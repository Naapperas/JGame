package jGame.core.sound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SoundStore {

	private SoundStore() {
		// make non-instantiable
	}

	private static HashMap<String, byte[]> soundMap = new HashMap<String, byte[]>();

	public static void cacheSound(String soundIdentifier, byte[] soundData) {
		soundMap.put(soundIdentifier, soundData);
	}

	// CODE TAKEN FROM
	// https://stackoverflow.com/questions/3923129/get-a-list-of-resources-from-classpath-directory

	/**
	 * List all of the entries (files or not) in the given <i>classpath</i> path.
	 * 
	 * See <a href="
	 * https://stackoverflow.com/questions/3923129/get-a-list-of-resources-from-classpath-directory">this
	 * post</a> for more information on the behavior of this method
	 * 
	 * @param path the path of the classpath resource
	 * @return a list of Strings denoting all the entries in the given path
	 * @throws IOException if n IOException occurs
	 * @author iirekm@StackOverflow.com
	 * @since 1.3.0
	 */
	public static List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();

		try (InputStream in = getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;

			while ((resource = br.readLine()) != null) { filenames.add(resource); }
		}

		return filenames;
	}

	private static InputStream getResourceAsStream(String resource) {
		final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);

		return in == null ? SoundStore.class.getResourceAsStream(resource) : in;
	}
}
