package jGame.core.serializable;

import java.io.BufferedWriter;
import java.io.IOException;

import jGame.core.launcher.GameState;
import jGame.core.launcher.GameStateManager;

public class GameStateSerializer {
	
	public static void serialize(BufferedWriter gameSerializer, int indent) throws IOException {
		
		gameSerializer.write("#GAME STATES");
		gameSerializer.write(System.lineSeparator());
		
		for (GameState state : GameStateManager.getGameStates()) {
			
			for (int i = 0; i < indent; i++)
				gameSerializer.write('\t');
			
			gameSerializer.write(state.getStateName());
			gameSerializer.write(System.lineSeparator());
			
		}		
	}	
}
