package jGame.core.serializable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jGame.logging.ProgramLogger;

/**
 * Class responsible for the serialization of the game in its current state.
 * Delegates methods to other serializers.
 * 
 * @author Nuno Pereira
 * @since 1.1.0
 */
public class GameSerializer {

	private static String pathToGameSaveFile;

	/**
	 * Serializes the game and its relevant aspects, like game states, entities and
	 * elements.
	 * 
	 * @since 1.1.0
	 */
	public static void serializeGame() {
		
		File saveFile = new File(pathToGameSaveFile);

		try (BufferedWriter serializer = new BufferedWriter(new FileWriter(saveFile));) {

			ProgramLogger.writeLog("Serializing game!");
			/*
			 * serializer.write(GameLauncher.gameName);
			 * serializer.write(System.lineSeparator());
			 */

			EntitySerializer.serializeEntities(saveFile);

			ProgramLogger.writeLog("Done serializing!");


		} catch (IOException e) {
			ProgramLogger.writeErrorLog(e, "Error while trying to serialize game!");
		}
	}

	/**
	 * Sets the path of the save game file.
	 * 
	 * @param pathToGameSaveFile the path of the game save file
	 * @since 1.1.0
	 */
	public static void setPathToGameSaveFile(String pathToGameSaveFile) {
		GameSerializer.pathToGameSaveFile = pathToGameSaveFile;
	}
}
