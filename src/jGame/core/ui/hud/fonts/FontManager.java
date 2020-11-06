package jGame.core.ui.hud.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Represents a collection of fonts to be used throughout the application.
 * 
 * @author Nuno Pereira
 * @since 1.2.0
 */
public class FontManager {

	/**
	 * Enum representing the types of fonts.
	 * 
	 * @author Nuno Pereira
	 * @since 1.2.0
	 */
	public static enum FontType {
		/**
		 * TrueType font
		 */
		TTF(Font.TRUETYPE_FONT),
		/**
		 * Type1 font
		 */
		T1(Font.TYPE1_FONT);

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
	 * Adds the specified font to this collection of fonts.
	 * 
	 * @param fontName the name of the font to add
	 * @param font     the font to add
	 * @since 1.2.0
	 */
	public static void addFont(String fontName, Font font) {
		fonts.put(fontName, font);
	}

	/**
	 * Loads a font from the specified path, using the specified font type.
	 * 
	 * @param fontFilePath the path of the font file
	 * @param fontType     the type of font, either {@code FontType#T1} or
	 *                     {@code FontType#TTF}
	 * @return the font specified by the file path
	 * @throws IOException         if the file referred to by {@code fontFilePath}
	 *                             cannot be read.
	 * @throws FontFormatException if the file referred to by {@code fontFilePath}
	 *                             does not contain the required font tables for the
	 *                             specified format.
	 * @throws SecurityException   if the executing code does not have permission to
	 *                             read from the file referred to by
	 *                             {@code fontFilePath}.
	 * @since 1.2.0
	 */
	public static Font loadFont(String fontFilePath, FontType fontType)
			throws FontFormatException, IOException, SecurityException {
		return Font.createFont(fontType.getType(), new File(fontFilePath));
	}
	
	/**
	 * Returns the font mapped to the given {@code fontName}.
	 * 
	 * @param fontName the name of the font to return
	 * @return the font specified by the given name
	 * @since 1.2.0
	 */
	public static Font getFont(String fontName) {
		return fonts.get(fontName);
	}

	/**
	 * Returns the default font used by every element.
	 * 
	 * @return the default font used by every element
	 * @since 1.2.0
	 * @see Font
	 */
	public static Font getDefaultFont() {
		return fonts.get("default");
	}
}
