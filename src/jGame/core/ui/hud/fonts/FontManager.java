package jGame.core.ui.hud.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * 
 * @author Nuno Pereia
 * @since 1.2.0
 */
public class FontManager {

	/**
	 * 
	 * @author Nuno Pereira
	 * @since 1.2.0
	 */
	public static enum FontType {
		TTF(Font.TRUETYPE_FONT), T1(Font.TYPE1_FONT);

		private int type;

		FontType(int type) {
			this.type = type;
		}

		public int getType() {
			return this.type;
		}
	}

	// the object mapping fonts to their names
	private static HashMap<String, Font> fonts = new HashMap<String, Font>();

	private FontManager() {// make class static
	}

	/**
	 * 
	 * 
	 * @param fontName
	 * @param font
	 */
	public static void addFont(String fontName, Font font) {
		fonts.put(fontName, font);
	}

	/**
	 * 
	 * @param fontFilePath
	 * @param fontType
	 * @return the font specified by the file path
	 * @throws IOException
	 * @throws FontFormatException
	 * @throws SecurityException
	 * @since 1.2.0
	 */
	public static Font loadFont(String fontFilePath, FontType fontType)
			throws FontFormatException, IOException, SecurityException {
		return Font.createFont(fontType.getType(), new File(fontFilePath));
	}
}
