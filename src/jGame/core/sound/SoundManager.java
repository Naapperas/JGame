package jGame.core.sound;

import java.io.File;
import java.net.URL;

import jGame.logging.ProgramLogger;

/**
 * This is the class responsible for the loading, storing/caching and playback
 * of audio data from files in the file system or in the classpath.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class SoundManager {

	/**
	 * Plays the specified audio file.
	 * 
	 * @param soundFileToPlay the audio file to be played
	 * @return a {@link SoundWorker} to play the specified audio
	 * @since 1.0.0
	 */
	public static SoundWorker playSound(File soundFileToPlay) {

		ProgramLogger.writeLog("Playing sound!");
		return new SoundWorker(soundFileToPlay);

	}

	/**
	 * Plays the audio file referenced by {@code soundFileToPlay}.
	 * 
	 * @param soundFileToPlay the {@link URL} to the audio file
	 * @return a {@link SoundWorker} to play the specified audio
	 * @since 1.0.0
	 */
	public static SoundWorker playSound(URL soundFileToPlay) {

		ProgramLogger.writeLog("Playing sound!");
		return new SoundWorker(new File(soundFileToPlay.toString()));

	}

	/**
	 * Plays the audio file referenced by {@code soundFileToPlay}.
	 * 
	 * @param soundFileToPlay the path of the file to be played
	 * @return a {@link SoundWorker} to play the specified audio
	 * @since 1.0.0
	 */
	public static SoundWorker playSound(String soundFileToPlay) {

		ProgramLogger.writeLog("Playing sound!");
		return new SoundWorker(new File(soundFileToPlay));

	}
}
