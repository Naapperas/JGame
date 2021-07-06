package jGame.core.sound;

import java.net.URL;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import JTuples.Tuple;
import jGame.logging.ProgramLogger;

/**
 * A sound bank to store all the audio data used by a game.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class SoundStore {

	private static boolean initiated = false;

	/**
	 * Returns weather the sound bank has been initialized.
	 * 
	 * @return weather the sound bank has been initialized
	 * @since 2.0.0
	 */
	public static boolean initiated() {
		return initiated;
	}

	/**
	 * Initialize the sound bank by checking the classpath for an "audio" folder.
	 * 
	 * Because of some limitations with reading the classpath for files, the user
	 * must provide a list with the names of the resources they wish to load.
	 * 
	 * @param audioResources a comma separated list of the audio resources to load
	 * 
	 * @since 2.0.0
	 */
	public static void init(String audioResources) {
		
		if (audioResources.isBlank()) {
			ProgramLogger.writeLog("No audio files detected");
			SoundPlayer.hasSound = false;
			return;
		}

		String resourcePath = "audio/";

		URL audioURL = getDataStream(resourcePath); // this from a previous version of this method, but still works to
													// check if there is an "audio" folder on the classpath

		if (audioURL == null) {
			ProgramLogger.writeLog("No audio files detected");
		} else {

			for (String resourceName : audioResources.split(",")) {
				if (resourceName.endsWith(".wav")) {
					// is .wav file, proceed
					String soundName = resourceName.split("\\.")[0];
					cacheSound(soundName, getSoundData(audioURL, resourceName)); // we need the function
																					// call to
																					// be like this because
																					// resources in a client
																					// game using this
																					// framework
																					// aren't accessible
																					// from
																					// this class, so use
																					// the
																					// url to get the
																					// absolute
																					// path to the resource
					ProgramLogger.writeLog("Caching sound: " + soundName);
				}
			}

			ProgramLogger.writeLog("Finnished caching all sounds!");
		}

		initiated = false;
	}

	private SoundStore() {
		// make non-instantiable
	}

	// the actual mappings from identifier and sound info (format and actual data)
	private static HashMap<String, Tuple> soundMap = new HashMap<String, Tuple>();

	/**
	 * Stores the given audio data in this audio bank.
	 * 
	 * @param soundIdentifier the identifier to use when addressing the given sound
	 * @param soundData       the actual sound data of the sound to cache
	 * @param format          the format of the sound data
	 * @since 2.0.0
	 */
	public static void cacheSound(String soundIdentifier, byte[] soundData, AudioFormat format) {
		cacheSound(soundIdentifier, Tuple.from(format, soundData));
	}

	// private method to serve as a bridge between the "pure" Java data types and
	// the Tuple defined in JTuples
	private static void cacheSound(String soundIdentifier, Tuple audio) {
		soundMap.put(soundIdentifier, audio);
	}

	/**
	 * Returns the sound data referred to by the given identifier.
	 * 
	 * @param soundIdentifier of the sound
	 * @return the byte data of the sound to play, or null if there is no sound with
	 *         the given identifier
	 * @since 2.0.0
	 */
	public static byte[] getSound(String soundIdentifier) {
		if (!soundMap.containsKey(soundIdentifier)) {
			ProgramLogger.writeLog("No sound named: " + soundIdentifier);
			return null;
		}
		return (byte[]) soundMap.get(soundIdentifier).get(1);
	}

	/**
	 * Returns the format of the sound data referred to by the given identifier.
	 * 
	 * @param soundIdentifier the identifier of the sound
	 * @return the format of the specified audio data, or null if there is no sound
	 *         with the given identifier
	 * @since 2.0.0
	 */
	public static AudioFormat getFormat(String soundIdentifier) {
		if (!soundMap.containsKey(soundIdentifier)) {
			ProgramLogger.writeLog("No sound named: " + soundIdentifier);
			return null;
		}
		return (AudioFormat) soundMap.get(soundIdentifier).get(0);
	}

	// not necessary, but useful to guarantee that we get something
	private static URL getDataStream(String resource) {

		URL audioInPath = Thread.currentThread().getContextClassLoader().getResource(resource);

		return (audioInPath == null ? SoundStore.class.getClassLoader().getResource(resource) : audioInPath);
	}

	// parses the given path and constructs a Pair object with the data and format
	// of the data
	private static Tuple getSoundData(URL parentDirectoryURL, String resource) {
		byte[] sound = null;
		AudioFormat format = null;

		try {

			URL url = new URL(parentDirectoryURL.toString() + resource); // form the URL to the resource

			ProgramLogger.writeLog(url.toString());

			AudioInputStream ais = AudioSystem.getAudioInputStream(url);

			sound = ais.readAllBytes();

			format = ais.getFormat();

			ais.close();
		} catch (Exception e) {
			ProgramLogger.writeErrorLog(e);
		}
		return Tuple.from(format, sound);
	}
}
