package jGame.core.serializable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jGame.core.launcher.GameLauncher;
import jGame.logging.ProgramLogger;

/**
 * Class responsible for the serialization of the game in its current state.
 * Delegates methods to other serializers.
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
		
		File gameSaveFile = new File(pathToGameSaveFolder + "\\game.core.dat.txt");

		try (BufferedWriter gameSerializer = new BufferedWriter(new FileWriter(gameSaveFile))) {

			ProgramLogger.writeLog("Serializing game!");
			
			gameSerializer.write("Game.name=" + GameLauncher.gameName);
			gameSerializer.write(System.lineSeparator());
			gameSerializer.write(System.lineSeparator());
			
			gameSerializer.write("WINDOW STATS");
			gameSerializer.write(System.lineSeparator());
			GameLauncher.getMainWindow().serialize(gameSerializer);

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
}
