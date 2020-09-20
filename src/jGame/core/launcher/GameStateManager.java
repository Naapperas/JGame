package jGame.core.launcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jGame.core.entity.Entity;
import jGame.core.ui.hud.UIHudElement;
import jGame.logging.ProgramLogger;

/**
 * Class responsible for managing game states and performing initialization and
 * termination of different game states.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class GameStateManager {

	/**
	 * The board {@link GameState} every game must have.
	 * 
	 * @since 1.1.0
	 */
	public static GameState board;
	private static ArrayList<Entity> boardEntities = new ArrayList<Entity>();
	private static ArrayList<UIHudElement> boardElements = new ArrayList<UIHudElement>();

	// static class, can't instantiate
	private GameStateManager() {
	}

	private static HashMap<String,GameState> gameStates = new HashMap<String, GameState>();
	private static GameState currentState;

	/**
	 * Initializes the {@code currentState}.
	 * 
	 * @since 1.0.0
	 */
	public static synchronized void initState() {
		ProgramLogger.writeLog("Initializing " + currentState.toString());
		currentState.initState();
	}

	/**
	 * Terminates the {@code currentState} object.
	 * 
	 * @since 1.0.0
	 */
	public static synchronized void terminateState() {
		ProgramLogger.writeLog("Terminating " + currentState.toString());
		currentState.terminateState();
	}

	/**
	 * Adds a new game state to the internal map. If there is no game state
	 * registered, makes {@code gameState} the current game state.
	 * 
	 * @param gameStateDescr the key in the map referring to the gameState
	 * @param gameState      the gameState to add
	 * @since 1.0.0
	 */
	public static synchronized void addGameState(String gameStateDescr, GameState gameState) {
		if (gameStates.isEmpty()) {
			ProgramLogger.writeLog("Setting current game state to " + gameState.toString());
			currentState = gameState;
		}
		gameStates.put(gameStateDescr, gameState);

		ProgramLogger.writeLog("Added game state: " + gameState.toString());
	}

	/**
	 * Removes the given game state.
	 * 
	 * @param gameStateDescr the game state object to be removed
	 * @since 1.0.0
	 */
	public static synchronized void removeGameState(String gameStateDescr) {
		ProgramLogger.writeLog("Removed game state: " + gameStates.remove(gameStateDescr).toString());
	}

	/**
	 * Changes the current game state.
	 * 
	 * @param gameStateDescr the {@link GameState} to make the current one
	 * @since 1.0.0
	 */
	public static synchronized void changeGameState(String gameStateDescr) {
		if (gameStates.containsKey(gameStateDescr)) {
			ProgramLogger.writeLog("Changing game state.");
			terminateState();
			currentState = gameStates.get(gameStateDescr);
			initState();
		}
		else
			ProgramLogger.writeLog("Can't change state; Maintaining same game state");
	}

	/**
	 * 
	 * @param entityToAdd
	 */
	public static void addEntityToBoard(Entity entityToAdd) {
		boardEntities.add(entityToAdd);
	}

	/**
	 * 
	 * @param elementToAdd
	 */
	public static void addUIHudElementToBoard(UIHudElement elementToAdd) {
		boardElements.add(elementToAdd);
	}

	/**
	 * 
	 * @param entitiesToAdd
	 */
	public static void addEntitiesToBoard(List<Entity> entitiesToAdd) {
		boardEntities.addAll(entitiesToAdd);
	}

	/**
	 * 
	 * @param elementsToAdd
	 */
	public static void addUIHudElementsToBoard(List<UIHudElement> elementsToAdd) {
		boardElements.addAll(elementsToAdd);
	}

	/**
	 * 
	 */
	public static void initializeBoard() {
		board = new GameState(boardEntities, boardElements, "Board");
		ProgramLogger.writeLog("Initializing Board");
	}

	/**
	 * 
	 */
	public static void addBoard() {
		ProgramLogger.writeLog("Adding Board");
		addGameState("Board", board);
	}

}
