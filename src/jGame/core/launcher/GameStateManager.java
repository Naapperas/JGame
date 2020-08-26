package jGame.core.launcher;

import java.util.HashMap;

import jGame.logging.ProgramLogger;

public class GameStateManager {

	// static class, can't instantiate
	private GameStateManager() {
	}

	private static HashMap<String,GameState> gameStates = new HashMap<String, GameState>();
	private static GameState currentState;

	public static synchronized void initState() {
		ProgramLogger.writeLog("Initializing " + currentState.toString());
		currentState.initState();
	}

	public static synchronized void terminateState() {
		ProgramLogger.writeLog("Terminating " + currentState.toString());
		currentState.terminateState();
	}

	public static synchronized void addGameState(String gameStateDescr, GameState gameState) {
		if (gameStates.isEmpty())
			currentState = gameState;
		gameStates.put(gameStateDescr, gameState);

		ProgramLogger.writeLog("Added game state: " + gameState.toString());
	}

	public static synchronized void removeGameState(String gameStateDescr) {
		ProgramLogger.writeLog("Removed game state: " + gameStates.remove(gameStateDescr).toString());
	}

	public static synchronized void changeGameState(String gameStateDescr) {
		if (gameStates.containsKey(gameStateDescr)) {
			terminateState();
			currentState = gameStates.get(gameStateDescr);
			initState();
		}
		else
			ProgramLogger.writeLog("Can't change state; Maintaining same game state");
	}

}
