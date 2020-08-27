package jGame.core.launcher;

import java.util.LinkedList;
import java.util.List;

import jGame.core.entity.Entity;
import jGame.core.entity.EntityManager;
import jGame.core.ui.hud.UIHud;
import jGame.core.ui.hud.UIHudElement;
import jGame.logging.ProgramLogger;

/**
 * This class represents a game state to be played.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class GameState {

	private LinkedList<Entity> stateEntities = new LinkedList<Entity>();
	private LinkedList<UIHudElement> stateHUDElements = new LinkedList<UIHudElement>();

	private String stateName;

	/**
	 * Create a new {@link GameState} object with the given entities and HUD
	 * elements.
	 * 
	 * @param entities      the entities to be rendered in this game state
	 * @param hudElements   the HUD elements to appear on screen
	 * @param gameStateName the name of this game state
	 * @since 1.0.0
	 */
	public GameState(List<Entity> entities, List<UIHudElement> hudElements, String gameStateName) {
		stateEntities.addAll(entities);
		stateHUDElements.addAll(hudElements);
		stateName = gameStateName;
	}

	/**
	 * Initializes the current state, adding all entities and registering their
	 * respective listeners.
	 * 
	 * @since 1.0.0
	 */
	public void initState() {

		if (!stateEntities.isEmpty())
			stateEntities.forEach((entity) -> {
				EntityManager.addEntity(entity);
				ProgramLogger.writeLog("Added " + entity);
				entity.registerInputListener();
				ProgramLogger.writeLog("Registered input listeners for " + entity);
			});

		if (!stateHUDElements.isEmpty())
			stateHUDElements.forEach((element) -> {
				UIHud.addHUDUIElement(element);
				ProgramLogger.writeLog("Added " + element);
				element.registerInputListener();
			});
	}

	/**
	 * Terminates the current state, removing all entities and unregistering their
	 * listeners.
	 * 
	 * @since 1.0.0
	 */
	public void terminateState() {

		stateEntities.forEach((entity) -> {
			EntityManager.removeEntity(entity);
			ProgramLogger.writeLog("Removed " + entity);
			entity.removeInputListener();
			ProgramLogger.writeLog("Unregistered input listeners for " + entity);
		});

		stateHUDElements.forEach((element) -> {
			UIHud.removeHUDUIElement(element);
			ProgramLogger.writeLog("Removed " + element);
			element.removeInputListener();
		});

	}

	@Override
	public String toString() {
		return "GameState[stateName=" + stateName + "]";
	}
}
