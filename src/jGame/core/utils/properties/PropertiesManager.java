package jGame.core.utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import jGame.core.sound.SoundStore;
import jGame.logging.ProgramLogger;

/**
 * Class responsible for collecting important properties from a file in the file
 * system to be used.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class PropertiesManager {

	// the properties file containing serializable data about the game's internal state
	
	private static final Properties defaultProperties = new Properties();
	private static Properties properties = null;
	
	static {
		
		// load default properties, logging should be enabled by default
		
		defaultProperties.put("logging", "on");
		defaultProperties.put("movement.defaultUserControlled", "true");
		defaultProperties.put("entity.defaultSpeed", "5");
		
		properties = new Properties(defaultProperties);
	}
	
	private static boolean propertiesFetched = false, firstRound = true;
	
	/**
	 * Loads all the properties from the input stream and makes them available for
	 * use. It is the responsibility of the client to close the {@code InputStream} object.
	 * 
	 * @param propertiesFileIS the input stream pointing to the properties file.
	 * @since 1.0.0
	 */
	public static void fetchProperties(InputStream propertiesFileIS) {
		if (propertiesFetched)
			return;
		
		firstRound = false;
		
		ProgramLogger.writeLog("Fetching main properties!"); // this line always gets printed

		try {
			properties.load(propertiesFileIS);
		} catch (IOException e) {
			ProgramLogger.writeErrorLog(e, "Error fetching properties from the given location!!");
		}
		
		ProgramLogger.writeLog("Properties fetched!");

		propertiesFetched = true;
	}
	
	/**
	 * Loads all the properties from the file and makes them available for use.
	 * 
	 * @param propertiesFile the properties file to read properties from.
	 * @since 1.0.0
	 */
	public static void fetchProperties(File propertiesFile) {
		if (propertiesFetched)
			return;

		firstRound = false;
		
		if (propertiesFile == null || !propertiesFile.exists())
			throw new IllegalArgumentException("Invalid file argument!");
		
		FileInputStream propertiesFIS = null;
		try {
			propertiesFIS = new FileInputStream(propertiesFile);
			fetchProperties(propertiesFIS);
		} catch (FileNotFoundException e) {
			ProgramLogger.writeErrorLog(e, "Couldnt find " + propertiesFile.toString());
		} finally {
			try {
				if (propertiesFIS != null)
					propertiesFIS.close();
			} catch (IOException e) {
				ProgramLogger.writeErrorLog(e, "Error closing FileInputStream");
			}
		}

	}

	/**
	 * Loads all the properties from the file pointed by {@code propertiesFilePath}
	 * and makes them available for use.
	 * 
	 * @param propertiesFilePath the path of the properties file to read properties
	 *                           from.
	 * 
	 * @since 1.0.0
	 */
	public static void fetchProperties(String propertiesFilePath) {
		if (propertiesFetched)
			return;
		
		firstRound = false;

		if (propertiesFilePath.isBlank())
			throw new IllegalArgumentException("Invalid file path argument!");

		File propertiesFile = new File(propertiesFilePath);
		fetchProperties(propertiesFile);
	}

	/**
	 * Fetches the properties from the classpath.
	 * 
	 * @since 1.3.0
	 */
	public static void fetchProperties() {
		
		if (propertiesFetched)
			return;
		
		firstRound = false;
		
		String resourcePath = "properties/";

		URL propertiesPathURL = getDataStream(resourcePath);

		if (propertiesPathURL == null) {
			ProgramLogger.writeLog("No property files detected");
		} else {

			try {

				URL propertiesURL = new URL(propertiesPathURL.toString() + "app.properties");
				fetchProperties(propertiesURL.openStream());
			} catch (IOException e) {

				ProgramLogger.writeErrorLog(e);
			}
		}
	}

	/**
	 * Returns the value of the property identified by <code>propertyName</code>.
	 * 
	 * @param propertyName the name/key of the desired property
	 * @return the value for the property with key <code>propertyName</code>
	 * @since 1.0.0
	 */
	public static String getProperty(String propertyName) {
		
		if(!propertiesFetched && firstRound) fetchProperties();
		
		return properties.getProperty(propertyName);
	}
	
	/**
	 * Returns the value of the property identified by <code>propertyName</code>. If
	 * no such property exists, {@code defaultValue} is returned.
	 * 
	 * @param propertyName the name/key of the desired property
	 * @param defaultValue a default value to be returned if there is no property
	 *                     with key {@code propertyName}
	 * @return the value for the property with key <code>propertyName</code>, or
	 *         {@code defaultValue}, if no property with key {@code propertyName}
	 *         exists.
	 * @since 1.0.0
	 */
	public static String getPropertyOrDefault(String propertyName, String defaultValue) {
		if(!propertiesFetched && firstRound) fetchProperties();
		
		return properties.getProperty(propertyName, defaultValue);
	}

	/*
	 * this method was made in the sound package and copied over into this class as it fits the same purpose, hence the names used don´t fit in.
	 */
	// not necessary but gives more certainty as to getting a URL object
	private static URL getDataStream(String resource) {

		URL audioInPath = Thread.currentThread().getContextClassLoader().getResource(resource);

		return (audioInPath == null ? SoundStore.class.getResource(resource) : audioInPath);
	}

}
