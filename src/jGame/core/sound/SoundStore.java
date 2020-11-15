package jGame.core.sound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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
		
		InputStream audioInPath;
		try {

			URL audioURL = getDataStream(resourcePath);

			if (audioURL == null) {
				ProgramLogger.writeLog("No audio files detected");
			} else {
			
				audioInPath = audioURL.openStream();

				if (audioInPath == null) {
					ProgramLogger.writeLog("No audio files detected");
				} else {
					try {

						BufferedReader br = new BufferedReader(new InputStreamReader(audioInPath));
						String resource;

						while ((resource = br.readLine()) != null) {
							if (resource.endsWith(".wav")) {
								// is .wav file, proceed
								String soundName = resource.split("\\.")[0];
								cacheSound(soundName, getSoundData(audioURL, resource));
								ProgramLogger.writeLog("Caching sound: " + soundName);
							}
						}

						audioInPath.close();
					} catch (IOException e) {
						ProgramLogger.writeErrorLog(e);
					}
				}
			}
		} catch (IOException e) {
			ProgramLogger.writeErrorLog(e);
		}
	}

	public SoundStore() {
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
	 * @param soundIdentifier
	 * @return
	 */
	public static AudioFormat getFormat(String soundIdentifier) {
		return (AudioFormat) soundMap.get(soundIdentifier).getValue(0);
	}

	/**
	 * 
	 * @param resource
	 * @return
	 */
	private static URL getDataStream(String resource) {

		URL audioInPath = Thread.currentThread().getContextClassLoader().getResource(resource);

		return (audioInPath == null ? SoundStore.class.getResource(resource) : audioInPath);
	}

	/**
	 * 
	 * @param resource
	 * @return the sound data in the given resource
	 */
	private static Pair<AudioFormat, byte[]> getSoundData(URL parentDirectoryURL, String resource) {
		byte[] sound = null;
		AudioFormat format = null;

		try {

			URL url = new URL(parentDirectoryURL.toString() + "/" + resource);

			AudioInputStream ais = AudioSystem.getAudioInputStream(url);

			sound = ais.readAllBytes();

			format = ais.getFormat();

			ais.close();
		} catch (UnsupportedAudioFileException | IOException e) {
			ProgramLogger.writeErrorLog(e);
		}
		return Pair.with(format, sound);
	}
}
