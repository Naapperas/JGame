package jGame.core.entity.component;

import java.awt.Point;

import jGame.core.entity.Entity;
import jGame.core.entity.EntityManager;
import jGame.core.entity.event.CollisionEvent;
import jGame.core.launcher.GameLauncher;

/**
 * The default implementation of a collision component.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class CollisionComponent extends Component {

	private CollisionComponent() {
		// make uninstantiable
	}
	
	private Runnable collider = null;
	private boolean collisionsAllowed = true;
	
	/**
	 * 
	 * @param allow
	 */
	public void allowCollsions(boolean allow) {
		this.collisionsAllowed = allow;
	}
	
	/**
	 * Sets the custom collider for this collider component.
	 * 
	 * @param collider the collider to set
	 * @since 2.0.0
	 */
	public void setCollider(Runnable collider) {
		this.collider = collider;
	}

	private void collide() {if(collider != null) collider.run();}
	
	/**
	 * Creates a collision component attached to this entity.
	 * 
	 * @param entity the entity to attach this component too
	 * @return the component created.
	 * @since 2.0.0
	 */
	public static CollisionComponent create(Entity entity) {
		CollisionComponent cc = new CollisionComponent();
		cc.entity = entity;
		return cc;
	}
	
	@Override
	public void execute() {

		if (!this.collisionsAllowed) return;
		
		// wall collision code
		if (this.entity.getColisionBounds().getX() <= 0
				|| this.entity.getColisionBounds().getX() + this.entity.getColisionBounds().getWidth() >= GameLauncher
				.getMainWindow().getWindowCanvas().getBounds().getWidth()) {
			CollisionEvent theCollision = new CollisionEvent(this.entity, null, "left/right wall hit",
					this.entity.getColisionBounds().x,
					this.entity.getColisionBounds().y, CollisionEvent.CollisionType.ENTITY_WALL);

			this.entity.getCollisionListeners().forEach((listener) -> { listener.onCollision(theCollision); });
		}
		
		if (this.entity.getColisionBounds().getY() <= 0
				|| this.entity.getColisionBounds().getY() + this.entity.getColisionBounds().getHeight() >= GameLauncher.getMainWindow()
						.getWindowCanvas().getBounds().getHeight()) {
			CollisionEvent theCollision = new CollisionEvent(this.entity, null, "top/bottom wall hit",
					this.entity.getColisionBounds().x, this.entity.getColisionBounds().y, CollisionEvent.CollisionType.ENTITY_WALL);

			this.entity.getCollisionListeners().forEach((listener) -> { listener.onCollision(theCollision); });
		}

		// entity collision code
		for (Entity entity : EntityManager.getEntitiesList()) {

			if(entity == this.entity)
				continue; // can't collide with ourselves
			
			if (this.entity.getColisionBounds().intersects(entity.getColisionBounds())) {

				CollisionEvent theCollision = new CollisionEvent(this.entity, entity, entity.toString() + " hit",
						this.entity.getColisionBounds().x, this.entity.getColisionBounds().y,
						CollisionEvent.CollisionType.ENTITY_ENTITY);

				this.entity.getCollisionListeners().forEach((listener) -> { listener.onCollision(theCollision); });

			}
		}		
		this.collide();
	}
	
	/**
	 * Moves the collision bounds of this entity to the given {@code x} and {@code y} coordinates.
	 * 
	 * @param x the new x position of the collision bounds
	 * @param y the new y position of the collision bounds
	 * @since 2.0.0
	 */
	public void moveBounds(int x, int y) {
		this.entity.getColisionBounds().setLocation(x, y);
	}
	
	/**
	 * Moves the top left corner of the collision bounds to the point given.
	 * 
	 * @param p the new position of the top left corner of the collision bounds
	 * @since 2.0.0
	 */
	public void moveBounds(Point p) {
		moveBounds(p.x, p.y);
	}

	@Override
	public void init() {

		
		
		
	}
}
