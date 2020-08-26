package jGame.core.launcher;

import java.util.HashMap;

public class GameStateManager {

	private GameStateManager() {
	}

	private static HashMap<String,GameState> gameStates = new HashMap<String, GameState>();
	private static GameState currentState;

	public static synchronized void initState() {
		currentState.initState();
	}

	public static synchronized void terminateState() {
		currentState.terminateState();
	}

	public static synchronized void addGameState(String gameStateDescr, GameState gameState) {
		if (gameStates.isEmpty())
			currentState = gameState;
		gameStates.put(gameStateDescr, gameState);
	}

	public static synchronized void removeGameState(String gameStateDescr) {
		gameStates.remove(gameStateDescr);
	}

}
