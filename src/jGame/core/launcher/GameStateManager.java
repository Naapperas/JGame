package jGame.core.launcher;

import java.util.HashMap;

import jGame.logging.ProgramLogger;

/**
 * Class responsible for managing game states and performing initialization and
 * termination of different game states.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class GameStateManager {

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
		if (gameStates.isEmpty())
			currentState = gameState;
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

}
