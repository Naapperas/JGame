package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * This class represents a textual visual element whose only purpose is to
 * display a piece of text to the screen.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class UIHudTextElement extends UIHudElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5009618223466781942L;

	// the text to be rendered, self-explanatory
	private String textToDisplay = "";
	private Font textFont = null;
	private Color textColor = Color.WHITE;

	protected UIHudTextElement() {
	}

	/**
	 * Initializes this text element in the specified location.
	 * 
	 * @param x the horizontal position of this element
	 * @param y the vertical position of this element
	 * @since 1.0.0
	 */
	public UIHudTextElement(int x, int y) {
		super(x, y);
		textFont = null;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Initializes this text element in the specified location and with the given
	 * starting text.
	 * 
	 * @param x             the horizontal position of this element
	 * @param y             the vertical position of this element
	 * @param displayString the starting text to display
	 * @since 1.0.0
	 */
	public UIHudTextElement(int x, int y, String displayString) {
		super(x, y);
		this.textToDisplay = displayString;
		textFont = null;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}
	
	/**
	 * Initializes this text element in the specified location and with the color to
	 * render the text with.
	 * 
	 * @param x            the horizontal position of this element
	 * @param y            the vertical position of this element
	 * @param displayColor the color of the text to display
	 * @since 1.0.0
	 */
	public UIHudTextElement(int x, int y, Color displayColor) {
		super(x, y);
		this.textColor = displayColor;
		textFont = null;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Initializes this text element in the specified location and with the given
	 * starting text. Also specify the color used to render the text on the screen.
	 * 
	 * @param x             the horizontal position of this element
	 * @param y             the vertical position of this element
	 * @param displayString the starting text to display
	 * @param displayColor  the color used to display the text
	 * @since 1.0.0
	 */
	public UIHudTextElement(int x, int y, String displayString, Color displayColor) {
		this(x, y, displayString);
		textColor = displayColor;
		textFont = null;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}


	/**
	 * Initializes this text element in the specified location. Uses the given font
	 * to render the text.
	 * 
	 * @param x           the horizontal position of this element
	 * @param y           the vertical position of this element
	 * @param displayFont the font of the text to be displayed
	 * @since 1.0.0
	 */
	public UIHudTextElement(int x, int y, Font displayFont) {
		this(x, y);
		this.textFont = displayFont;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Initializes this text element in the specified location and with the given
	 * starting text using the given font.
	 * 
	 * @param x             the horizontal position of this element
	 * @param y             the vertical position of this element
	 * @param displayString the starting text to display
	 * @param displayFont   the font of the text to be displayed
	 * @since 1.0.0
	 */
	public UIHudTextElement(int x, int y, String displayString, Font displayFont) {
		this(x, y, displayString);
		this.textFont = displayFont;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}
	
	/**
	 * Initializes this text element in the specified location and with the color of
	 * the text to be rendered. Also specify the text's font.
	 * 
	 * @param x            the horizontal position of this element
	 * @param y            the vertical position of this element
	 * @param displayColor the color of the text to display
	 * @param displayFont  the font of the text to be displayed
	 * @since 1.0.0
	 */
	public UIHudTextElement(int x, int y, Color displayColor, Font displayFont) {
		this(x, y, displayColor);
		this.textFont = displayFont;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Initializes this text element in the specified location and with the given
	 * starting text. Also specify the color used to render the text on the screen
	 * and the font of the text.
	 * 
	 * @param x             the horizontal position of this element
	 * @param y             the vertical position of this element
	 * @param displayString the starting text to display
	 * @param displayColor  the color used to display the text
	 * @param displayFont   the font of the text to be displayed
	 * @since 1.0.0
	 */
	public UIHudTextElement(int x, int y, String displayString, Color displayColor, Font displayFont) {
		this(x, y, displayString, displayColor);
		this.textFont = displayFont;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}
	
	/**
	 * Initializes this text element in the specified location using the given
	 * constraints.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param constraintSpecs  the constraints to apply
	 * @param constraintValues the values of the applied constraints
	 * @since 1.1.0
	 */
	public UIHudTextElement(int x, int y, int constraintSpecs, int[] constraintValues) {
		super(x, y);
		textFont = null;
		this.drawConstraints = new Constraints(this, constraintSpecs, constraintValues);
	}

	/**
	 * Initializes this text element in the specified location and with the given
	 * starting text, using the given constraints.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param displayString    the starting text to display
	 * @param constraintSpecs  the constraints to apply
	 * @param constraintValues the values of the applied constraints
	 * @since 1.1.0
	 */
	public UIHudTextElement(int x, int y, String displayString, int constraintSpecs, int[] constraintValues) {
		super(x, y);
		this.textToDisplay = displayString;
		textFont = null;
		this.drawConstraints = new Constraints(this, constraintSpecs, constraintValues);
	}

	/**
	 * Initializes this text element in the specified location and with the color to
	 * render the text with.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param displayColor     the color of the text to display
	 * @param constraintSpecs  the constraints to apply
	 * @param constraintValues the values of the applied constraints
	 * @since 1.1.0
	 */
	public UIHudTextElement(int x, int y, Color displayColor, int constraintSpecs, int[] constraintValues) {
		super(x, y);
		this.textColor = displayColor;
		textFont = null;
		this.drawConstraints = new Constraints(this, constraintSpecs, constraintValues);
	}

	/**
	 * Initializes this text element in the specified location and with the given
	 * starting text. Also specify the color used to render the text on the screen.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param displayString    the starting text to display
	 * @param displayColor     the color used to display the text
	 * @param constraintSpecs  the constraints to apply
	 * @param constraintValues the values of the applied constraints
	 * @since 1.1.0
	 */
	public UIHudTextElement(int x, int y, String displayString, Color displayColor, int constraintSpecs,
			int[] constraintValues) {
		this(x, y, displayString);
		textColor = displayColor;
		textFont = null;
		this.drawConstraints = new Constraints(this, constraintSpecs, constraintValues);
	}

	/**
	 * Initializes this text element in the specified location. Uses the given font
	 * to render the text.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param displayFont      the font of the text to be displayed
	 * @param constraintSpecs  the constraints to apply
	 * @param constraintValues the values of the applied constraints
	 * @since 1.1.0
	 */
	public UIHudTextElement(int x, int y, Font displayFont, int constraintSpecs, int[] constraintValues) {
		this(x, y);
		this.textFont = displayFont;
		this.drawConstraints = new Constraints(this, constraintSpecs, constraintValues);
	}

	/**
	 * Initializes this text element in the specified location and with the given
	 * starting text using the given font.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param displayString    the starting text to display
	 * @param displayFont      the font of the text to be displayed
	 * @param constraintSpecs  the constraints to apply
	 * @param constraintValues the values of the applied constraints
	 * @since 1.1.0
	 */
	public UIHudTextElement(int x, int y, String displayString, Font displayFont, int constraintSpecs,
			int[] constraintValues) {
		this(x, y, displayString);
		this.textFont = displayFont;
		this.drawConstraints = new Constraints(this, constraintSpecs, constraintValues);
	}

	/**
	 * Initializes this text element in the specified location and with the color of
	 * the text to be rendered. Also specify the text's font.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param displayColor     the color of the text to display
	 * @param displayFont      the font of the text to be displayed
	 * @param constraintSpecs  the constraints to apply
	 * @param constraintValues the values of the applied constraints
	 * @since 1.1.0
	 */
	public UIHudTextElement(int x, int y, Color displayColor, Font displayFont, int constraintSpecs,
			int[] constraintValues) {
		this(x, y, displayColor);
		this.textFont = displayFont;
		this.drawConstraints = new Constraints(this, constraintSpecs, constraintValues);
	}

	/**
	 * Initializes this text element in the specified location and with the given
	 * starting text. Also specify the color used to render the text on the screen
	 * and the font of the text.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param displayString    the starting text to display
	 * @param displayColor     the color used to display the text
	 * @param displayFont      the font of the text to be displayed
	 * @param constraintSpecs  the constraints to apply
	 * @param constraintValues the values of the applied constraints
	 * @since 1.1.0
	 */
	public UIHudTextElement(int x, int y, String displayString, Color displayColor, Font displayFont,
			int constraintSpecs, int[] constraintValues) {
		this(x, y, displayString, displayColor);
		this.textFont = displayFont;
		this.drawConstraints = new Constraints(this, constraintSpecs, constraintValues);
	}

	@Override
	protected void render(Graphics2D g) {

		Font startingFont = g.getFont();

		if (textFont != null)
			g.setFont(textFont);


		if (textColor != null)
			g.setColor(textColor);

		FontMetrics fontDimensions = g.getFontMetrics();
		this.height = fontDimensions.getAscent() - fontDimensions.getLeading() - fontDimensions.getDescent();
		this.width = (int) fontDimensions.getStringBounds(textToDisplay, g).getWidth();

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		g.drawString(textToDisplay, this.x, this.y);

		g.setColor(Color.BLACK);
		g.setFont(startingFont);

	}

	/**
	 * Changes the text to render.
	 * 
	 * @param textToDisplay the new text to render
	 * @since 1.0.0
	 */
	public void setTextToDisplay(String textToDisplay) {
		this.textToDisplay = textToDisplay;
	}

	/**
	 * Returns the text rendered by this element.
	 * 
	 * @return the text to be displayed
	 * @since 1.0.0
	 */
	public String getTextToDisplay() {
		return textToDisplay;
	}

	@Override
	public void registerInputListener() {
		// do nothing
	}

	@Override
	public void removeInputListener() {
		// do nothing
	}
}
