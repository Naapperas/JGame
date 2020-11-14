package jGame.core.sound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.javatuples.Pair;

import jGame.logging.ProgramLogger;

public class SoundStore {

	public static void init() {

		String resourcePath = "audio/";
		
		InputStream audioInPath = getDataStream(resourcePath);
		
		if (audioInPath == null) { ProgramLogger.writeLog("No audio files detected"); }
		else {
			try {

				BufferedReader br = new BufferedReader(new InputStreamReader(audioInPath));
				String resource;

				while ((resource = br.readLine()) != null) {
					if (resource.endsWith(".wav")) {
						// is .wav file, proceed
						String soundName = resource.split(".")[0];
						cacheSound(soundName, getSoundData(resourcePath + resource));
					}
				}

				audioInPath.close();
			} catch (IOException e) {
				ProgramLogger.writeErrorLog(e);
			}
		}
	}

	private SoundStore() {
		// make non-instantiable
	}

	private static HashMap<String, Pair<AudioFormat, byte[]>> soundMap = new HashMap<String, Pair<AudioFormat, byte[]>>();

	/**
	 * 
	 * @param soundIdentifier
	 * @param soundData
	 * @param format
	 */
	public static void cacheSound(String soundIdentifier, byte[] soundData, AudioFormat format) {
		cacheSound(soundIdentifier, Pair.with(format, soundData));
	}

	/**
	 * 
	 * @param soundIdentifier
	 * @param audio
	 */
	private static void cacheSound(String soundIdentifier, Pair<AudioFormat, byte[]> audio) {
		soundMap.put(soundIdentifier, audio);
	}

	/**
	 * 
	 * @param soundIdentifier
	 * @return the byte data of the sound to play
	 * @since 1.3.0
	 */
	public static byte[] getSound(String soundIdentifier) {
		return (byte[]) soundMap.get(soundIdentifier).getValue(1);
	}

	/**
	 * 
	 * @param resource
	 * @return
	 */
	private static InputStream getDataStream(String resource) {

		InputStream audioInPath = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);

		return (audioInPath == null ? SoundStore.class.getResourceAsStream(resource) : audioInPath);

	}

	/**
	 * 
	 * @param resource
	 * @return the sound data in the given resource
	 */
	public static Pair<AudioFormat, byte[]> getSoundData(String resource) {
		byte[] sound = null;
		AudioFormat format = null;

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getDataStream(resource));

			sound = ais.readAllBytes();

			format = ais.getFormat();

			ais.close();
		} catch (UnsupportedAudioFileException | IOException e) {
			ProgramLogger.writeErrorLog(e);
		}
		return Pair.with(format, sound);
	}
}
