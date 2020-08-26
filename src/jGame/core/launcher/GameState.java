package jGame.core.launcher;

import java.util.LinkedList;
import java.util.List;

import jGame.core.entity.Entity;
import jGame.core.entity.EntityManager;
import jGame.core.ui.hud.UIHud;
import jGame.core.ui.hud.UIHudElement;
import jGame.logging.ProgramLogger;

public class GameState {

	private LinkedList<Entity> stateEntities = new LinkedList<Entity>();
	private LinkedList<UIHudElement> stateHUDElements = new LinkedList<UIHudElement>();

	private String stateName;

	public GameState(List<Entity> entities, List<UIHudElement> hudElements, String gameStateName) {
		stateEntities.addAll(entities);
		stateHUDElements.addAll(hudElements);
		stateName = gameStateName;
	}

	public void initState() {

		stateEntities.forEach((entity) -> {
			EntityManager.addEntity(entity);
			entity.registerInputListener();
			ProgramLogger.writeLog("Adding " + entity);
		});

		stateHUDElements.forEach((element) -> {
			UIHud.addHUDUIElement(element);
			element.registerInputListener();
		});
	}

	public void terminateState() {

		stateEntities.forEach((entity) -> {
			ProgramLogger.writeLog("Removing " + entity);
			EntityManager.removeEntity(entity);
			entity.removeInputListener();
		});

		stateHUDElements.forEach((element) -> {
			UIHud.removeHUDUIElement(element);
			element.removeInputListener();
		});

	}

	@Override
	public String toString() {
		return "GameState[stateName=" + stateName + "]";
	}
}
