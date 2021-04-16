package jGame.core.serializable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jGame.core.launcher.GameLauncher;
import jGame.logging.ProgramLogger;

/**
 * Class responsible for the serialization of the game in its current state.
 * Delegates serialization to other serializers.
 * 
 * @author Nuno Pereira
 * @since 1.1.0
 */
public class GameSerializer {

	private static String pathToGameSaveFolder;

	/**
	 * Serializes the game and its relevant aspects, like game states, entities and
	 * elements.
	 * 
	 * @since 1.1.0
	 */
	public static void serializeGame() {
		
		int indent = 0;
		
		File gameSaveFile = new File(pathToGameSaveFolder + "\\game.core.dat.txt");

		try (BufferedWriter gameSerializer = new BufferedWriter(new FileWriter(gameSaveFile))) {

			ProgramLogger.writeLog("Serializing game!");
			
			gameSerializer.write("#GAME STATS");
			gameSerializer.write(System.lineSeparator());
			gameSerializer.write("Game.name=" + GameLauncher.gameName);
			gameSerializer.write(System.lineSeparator());
			GameLauncher.serialize(gameSerializer, indent);
			gameSerializer.write(System.lineSeparator());
			
			gameSerializer.write(System.lineSeparator());
			
			gameSerializer.write("#WINDOW STATS");
			gameSerializer.write(System.lineSeparator());
			GameLauncher.getMainWindow().serialize(gameSerializer, indent);
			gameSerializer.write(System.lineSeparator());
			
			gameSerializer.write(System.lineSeparator());

			GameStateSerializer.serialize(gameSerializer, indent + 1);
			
			ProgramLogger.writeLog("Done serializing!");

		} catch (IOException e) {
			ProgramLogger.writeErrorLog(e, "Error while trying to serialize game!");
		}
	}

	/**
	 * Sets the path of the save game folder.
	 * 
	 * @param pathToGameSaveFile the path of the game save folder
	 * @since 1.1.0
	 */
	public static void setPathToGameSaveFile(String pathToGameSaveFolder) {
		GameSerializer.pathToGameSaveFolder = pathToGameSaveFolder;
	}
	
	/**
	 * Checks whether the file path for the game save file has been set.
	 * 
	 * @return true if the file path for the game save file has been set
	 */
	public static boolean isGameSaveFilePathSet() {
		return GameSerializer.pathToGameSaveFolder != null;
	}
}
