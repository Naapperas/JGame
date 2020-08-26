package jGame.core.utils.properties;

import java.util.Properties;

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
	 * Loads all the properties from the file and makes them available for use.
	 * 
	 */
	public static void fetchProperties() {
		if(propertiesFetched)return;
		
		//INSERT CODE HERE
		
		propertiesFetched = true;
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
	
}
