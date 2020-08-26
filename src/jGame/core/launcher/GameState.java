package jGame.core.launcher;

import java.util.LinkedList;
import java.util.List;

import jGame.core.entity.Entity;
import jGame.core.entity.EntityManager;
import jGame.core.ui.hud.UIHud;
import jGame.core.ui.hud.UIHudElement;

public class GameState {

	private LinkedList<Entity> stateEntities = new LinkedList<Entity>();
	private LinkedList<UIHudElement> stateHUDElements = new LinkedList<UIHudElement>();

	public GameState(List<Entity> entities, List<UIHudElement> hudElements) {
		stateEntities.addAll(entities);
		stateHUDElements.addAll(hudElements);

	}

	public void initState() {

		stateEntities.forEach((entity) -> {
			EntityManager.addEntity(entity);
			entity.registerInputListener();
		});

		stateHUDElements.forEach((element) -> {
			UIHud.addHUDUIElement(element);
			element.registerInputListener();
		});
	}

	public void terminateState() {

		stateEntities.forEach((entity) -> {
			EntityManager.removeEntity(entity);
			entity.removeInputListener();
		});

		stateHUDElements.forEach((element) -> {
			UIHud.removeHUDUIElement(element);
			element.removeInputListener();
		});

	}
}
