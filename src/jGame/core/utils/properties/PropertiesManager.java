package jGame.core.utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jGame.logging.ProgramLogger;

/**
 * 
 * Class responsible for collecting important properties from a file in the file
 * system to be used
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class PropertiesManager {

	// the properties file containing serializable data about the game
	private static Properties properties = new Properties();
	
	private static boolean propertiesFetched = false;
	
	/**
	 * Loads all the properties from the input stream and makes them available for
	 * use.
	 * 
	 * @param propertiesFileIS the input stream pointing to the properties file.
	 * 
	 * @since 1.0.0
	 * 
	 */
	public static void fetchProperties(InputStream propertiesFileIS) {
		if(propertiesFetched)return;
		
		ProgramLogger.writeLog("Fetching main properties!");

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
	 * 
	 * @since 1.0.0
	 * 
	 */
	public static void fetchProperties(File propertiesFile) {
		
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
	 * 
	 */
	public static void fetchProperties(String propertiesFilePath) {

		if (propertiesFilePath.isBlank())
			throw new IllegalArgumentException("Invalid file path argument!");

		File propertiesFile = new File(propertiesFilePath);
		fetchProperties(propertiesFile);
	}

	/**
	 * Returns the value of the property identified by <code>propertyName</code>.
	 * 
	 * @param propertyName the name/key of the desired property
	 * @return the value for the property with key <code>propertyName</code>
	 * @since 1.0.0
	 */
	public static String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
	
	/**
	 * Returns the value of the property identified by <code>propertyName</code>. If
	 * no such property exists, {@code defaultValue} is returned.
	 * 
	 * @param propertyName the name/key of the desired property
	 * @param defaultValue a default value to be returned if there is no property
	 *                     with key {@code propertyName}
	 * @return the value for the property with key <code>propertyName</code>,
	 *         or{@code defaultValue}, if no property with key {@code propertyName}
	 *         exists.
	 * @since 1.0.0
	 */
	public static String getPropertyOrDefault(String propertyName, String defaultValue) {
		return properties.getProperty(propertyName, defaultValue);
	}

}
