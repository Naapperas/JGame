package jGame.core.entity;

import java.util.ArrayList;

import jGame.logging.ProgramLogger;

/**
 * This class serves as a register for all Entities present in the game,
 * renderable or not. In addition, a list for all entities queued for removal is
 * maintained.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class EntityManager {

	// the lists of entities/entities to remove
	private static final ArrayList<Entity> ENTITIES_LIST = new ArrayList<Entity>();
	private static final ArrayList<Entity> ENTITIES_TO_REMOVE_LIST = new ArrayList<Entity>();
	
	/**
	 * Adds an entity to the entities list, if it isn't already present, otherwise
	 * return.
	 * 
	 * @param entityToAdd the entity to add to the game
	 * @since 1.0.0
	 */
	public synchronized static void addEntity(Entity entityToAdd) {
		if(ENTITIES_LIST.contains(entityToAdd))
			return;
		
		ProgramLogger.writeLog("Adding entity: " + entityToAdd);
		ENTITIES_LIST.add(entityToAdd);
	}
	
	/**
	 * Registers the key input listeners for every {@link Entity} present in the
	 * entities list.
	 * 
	 * @since 1.0.0
	 */
	public synchronized static void registerInputListeners() {

		ProgramLogger.writeLog("Registering input listeners for added entities!");

		ENTITIES_LIST.forEach((entity) -> {
			entity.setUpInputListener();
			entity.registerInputListener();
		});
	}

	/**
	 * Queues the entity at index <code>entityIndex</code> of the entities List for
	 * removal (adds it to the entity removal list).
	 * 
	 * @param entityIndex the index of the entity to remove
	 * @since 1.0.0
	 */
	public synchronized static void removeEntity(int entityIndex) {
		ProgramLogger.writeLog("Removing entity at index " + entityIndex);
		ENTITIES_TO_REMOVE_LIST.add(ENTITIES_LIST.get(entityIndex));
	}
	
	/**
	 * Queues the given <code>entity</code> for removal (adds it to the entity
	 * removal list).
	 * 
	 * @param entityToRemove the entity to remove
	 * @since 1.0.0
	 */
	public synchronized static void removeEntity(Entity entityToRemove) {
		if (ENTITIES_LIST.contains(entityToRemove)) {
			ProgramLogger.writeLog("Removing entity: " + entityToRemove);
			ENTITIES_TO_REMOVE_LIST.add(entityToRemove);
		}
	}
	
	/**
	 * Updates all entities on screen.
	 * 
	 * This method behaves exactly as:
	 * 
	 * <pre>
	 *  foreach entity:
	 *  	entity.tick()
	 *  	entity.render(g)
	 * </pre>
	 * 
	 * @param g the Graphics object responsible for rendering the objects on the
	 *          screen.
	 * @see #updateEntities(java.awt.Graphics)
	 * @see #tickEntities()
	 * @since 1.0.0
	 */
	public static synchronized void updateEntities(java.awt.Graphics g) {		
		tickEntities();
		renderEntities(g);
	}
	
	/**
	 * Renders all entities that are marked as renderable.
	 * 
	 * @param g the Graphics object responsible for rendering the objects on the
	 *          screen.
	 * @since 1.0.0
	 */
	public static synchronized void renderEntities(java.awt.Graphics g) {	
		ENTITIES_LIST.forEach((entity)->{
			if(entity.isRenderable())
				entity.render(g);
		});
	}
	
	/**
	 * Updates all the entities before rendering them on the screen. After all
	 * entities are updated, every entity which was queued for removal is removed
	 * from the entities list. Finally, the entity removal list is cleared so it
	 * doesn't infinitely increase in size with unused entities.
	 * 
	 * @see Entity#tick()
	 * @since 1.0.0
	 */
	public static synchronized void tickEntities() {

		// remove entities before updating in order to prevent updating removed entities
		
		if (!ENTITIES_TO_REMOVE_LIST.isEmpty()) {
			ENTITIES_TO_REMOVE_LIST.forEach((entity) -> { ENTITIES_LIST.remove(entity); });
			ENTITIES_TO_REMOVE_LIST.clear();
		}

		if (!ENTITIES_LIST.isEmpty())
			ENTITIES_LIST.forEach((entity) -> { entity.tick(); });
	}

	/**
	 * Returns a copy of the entities list.
	 * 
	 * @return a copy of the entities list.
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Entity> getEntitiesList() {
		return (ArrayList<Entity>) ENTITIES_LIST.clone();
	}
	
}
