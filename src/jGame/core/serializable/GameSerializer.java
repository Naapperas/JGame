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

	private static String pathToGameSaveFile;

	public static void serializeGame() {
		
		try (BufferedWriter serializer = new BufferedWriter(new FileWriter(new File(pathToGameSaveFile)));) {

			serializer.write(GameLauncher.gameName);

		} catch (IOException e) {
			ProgramLogger.writeErrorLog(e, "Error while trying to serialize game!");
		}
	}
}
