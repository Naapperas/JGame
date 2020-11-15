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

/**
 * A sound bank to store all the audio data used by a game.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class SoundStore {

	private static boolean initiated = false;

	public static boolean initiated() {
		return initiated;
	}

	/**
	 * Initialize the sound bank by checking the classpath for an "audio" folder.
	 * 
	 * @since 2.0.0
	 */
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
								cacheSound(soundName, getSoundData(audioURL, resource)); // we need the function call to
																							// be like this because
																							// resources in a client
																							// game using this framework
																							// aren't accesible from
																							// this class, so use the
																							// url to get the absolute
																							// path to the resource
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

		initiated = false;
	}

	private SoundStore() {
		// make non-instantiable
	}

	// the actual mappings from identifier and sound info (format and actual data)
	private static HashMap<String, Pair<AudioFormat, byte[]>> soundMap = new HashMap<String, Pair<AudioFormat, byte[]>>();

	/**
	 * Stores the given audio data in this audio bank.
	 * 
	 * @param soundIdentifier the identifier to use when addressing the given sound
	 * @param soundData       the actual sound data of the sound to cache
	 * @param format          the format of the sound data
	 * @since 2.0.0
	 */
	public static void cacheSound(String soundIdentifier, byte[] soundData, AudioFormat format) {
		cacheSound(soundIdentifier, Pair.with(format, soundData));
	}

	// private method to serve as a bridge between the "pure" Java data types and
	// the "javatuples" Pair
	private static void cacheSound(String soundIdentifier, Pair<AudioFormat, byte[]> audio) {
		soundMap.put(soundIdentifier, audio);
	}

	/**
	 * Returns the sound data referred to by the given identifier.
	 * 
	 * @param soundIdentifier of the sound
	 * @return the byte data of the sound to play
	 * @since 2.0.0
	 */
	public static byte[] getSound(String soundIdentifier) {
		if (!soundMap.containsKey(soundIdentifier)) {
			ProgramLogger.writeLog("No sound named: " + soundIdentifier);
			return null;
		}
		return (byte[]) soundMap.get(soundIdentifier).getValue(1);
	}

	/**
	 * Returns the format of the sound data referred to by the given identifier.
	 * 
	 * @param soundIdentifier the identifier of the sound
	 * @return the format of the specified audio data
	 * @since 2.0.0
	 */
	public static AudioFormat getFormat(String soundIdentifier) {
		if (!soundMap.containsKey(soundIdentifier)) {
			ProgramLogger.writeLog("No sound named: " + soundIdentifier);
			return null;
		}
		return (AudioFormat) soundMap.get(soundIdentifier).getValue(0);
	}

	// not necessary, but useful to guarantee that we get something
	private static URL getDataStream(String resource) {

		URL audioInPath = Thread.currentThread().getContextClassLoader().getResource(resource);

		return (audioInPath == null ? SoundStore.class.getResource(resource) : audioInPath);
	}

	// parses the given path and constructs a Pair object with the data and format
	// of the data
	private static Pair<AudioFormat, byte[]> getSoundData(URL parentDirectoryURL, String resource) {
		byte[] sound = null;
		AudioFormat format = null;

		try {

			URL url = new URL(parentDirectoryURL.toString() + "/" + resource); // form the URL to the resource

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
