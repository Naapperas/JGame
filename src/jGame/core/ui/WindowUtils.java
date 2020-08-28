package jGame.core.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import jGame.logging.ProgramLogger;

/**
 * This class provides a set of utility methods for UI/Window related logic, such as figuring the correct display size for visual components.
 * This is intended for single screen systems, multiple screen systems should be handled by the client.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 *
 */
public final class WindowUtils {
	 
	//make framework based around Full HD single screen systems and scale accordingly
	private static Dimension FULL_HD = null;
	public static double HORIZONTAL_SCALE = 0, VERTICAL_SCALE = 0;
	public static double FULLSCREEN_X = 0, FULLSCREEN_Y = 0;
	
	// make uninstatiatable
	private WindowUtils() {
	}

	static {
		FULL_HD = new Dimension(1920, 1080);
		setScreenDisplayScale();
	}
	
	/**
	 * Sets the horizontal and vertical scales with which each window will calculate
	 * it's dimensions relative to the screen resolutions, so higher resolution
	 * screens have a higher scale than lower resolution screens.
	 * 
	 * @since 1.0.0
	 */
	private static void setScreenDisplayScale() {

		ProgramLogger.writeLog("Setting screen display scale!");

		Dimension screenResolution = WindowParams.getScreenResolution();
		
		FULLSCREEN_X = screenResolution.getWidth();
		FULLSCREEN_Y = screenResolution.getHeight();

		// calculate scaling
		HORIZONTAL_SCALE = FULLSCREEN_X / FULL_HD.getWidth();
		VERTICAL_SCALE = FULLSCREEN_Y / FULL_HD.getHeight();
	}
	
	/**
	 * 
	 * Helper class to serve as a data container for screen/window related
	 * information.
	 * 
	 * @see Toolkit
	 * 
	 * @author Nuno Pereira
	 * @since 1.0.0
	 */
	static final class WindowParams {
	
		// dimensions of the screen the JVM gets started on
		private static double SCREEN_WIDTH = 0, SCREEN_HEIGHT = 0;
		private static Dimension SCREEN_RESOLUTION = new Dimension();
		
		static {	
			//Taken from https://stackoverflow.com/questions/3680221/how-can-i-get-screen-resolution-in-java
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			SCREEN_WIDTH = screenSize.getWidth();
			SCREEN_HEIGHT = screenSize.getHeight();
			SCREEN_RESOLUTION.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);	
		}
		
		/**
		 * Returns the screen resolutions values (width and height), encapsulated in a
		 * <code>Dimension</code> object
		 * 
		 * @return the screen resolution as a {@link Dimension} object.
		 * @see Dimension
		 * @since 1.0.0
		 */
		static Dimension getScreenResolution() {
			return (Dimension)SCREEN_RESOLUTION.clone();
		}
		
	}
}