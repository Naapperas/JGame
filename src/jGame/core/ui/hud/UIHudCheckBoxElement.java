package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * Class representing a toggable checkbox.
 * 
 * @author Nuno Pereira
 * @since 1.1.0
 */
public class UIHudCheckBoxElement extends UIHudButtonElement {

	// the state of the checkbox
	private boolean checkBoxTriggered = false;
	private int boxSideLenght;

	private String textToDisplay = null;

	{
		this.zIndex = 50;
	}

	public UIHudCheckBoxElement() {
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square).
	 * 
	 * @param x          the horizontal position of this element
	 * @param y          the vertical position of this element
	 * @param sideLength the size of the element's sides
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sideLength) {
		super(x, y, sideLength, sideLength, null);
		this.boxSideLenght = sideLength;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square). Also sets the text to be displayed on the side
	 * of this checkbox.
	 * 
	 * @param x          the horizontal position of this element
	 * @param y          the vertical position of this element
	 * @param sideLength the size of the element's sides
	 * @param text       the text to be displayed
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sideLength, String text) {
		super(x, y, sideLength, sideLength, null);
		this.boxSideLenght = sideLength;
		this.textToDisplay = text;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square).
	 * 
	 * @param x          the horizontal position of this element
	 * @param y          the vertical position of this element
	 * @param sideLength the size of the element's sides
	 * @param checked    weather this check box is already on or not
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sideLength, boolean checked) {
		super(x, y, sideLength, sideLength, null);
		this.boxSideLenght = sideLength;
		checkBoxTriggered = checked;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square). Also sets the text to be displayed on the side
	 * of this checkbox.
	 * 
	 * @param x          the horizontal position of this element
	 * @param y          the vertical position of this element
	 * @param sideLength the size of the element's sides
	 * @param text       the text to be displayed
	 * @param checked    weather this check box is on or not
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sideLength, String text, boolean checked) {
		super(x, y, sideLength, sideLength, null);
		this.boxSideLenght = sideLength;
		this.textToDisplay = text;
		checkBoxTriggered = checked;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square).
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param sideLength       the size of the element's sides
	 * @param constraintType   the constraints to apply to this element
	 * @param constraintValues the specific values to apply when constraining the
	 *                         element
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sideLength, int constraintType, int[] constraintValues) {
		super(x, y, sideLength, sideLength, null);
		this.boxSideLenght = sideLength;
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square). Also sets the text to be displayed on the side
	 * of this checkbox.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param sideLength       the size of the element's sides
	 * @param text             the text to be displayed
	 * @param constraintType   the constraints to apply to this element
	 * @param constraintValues the specific values to apply when constraining the
	 *                         element
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sideLength, String text, int constraintType, int[] constraintValues) {
		super(x, y, sideLength, sideLength, null);
		this.boxSideLenght = sideLength;
		this.textToDisplay = text;
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square).
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param sideLength       the size of the element's sides
	 * @param checked          weather this check box is already on or not
	 * @param constraintType   the constraints to apply to this element
	 * @param constraintValues the specific values to apply when constraining the
	 *                         element
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sideLength, boolean checked, int constraintType,
			int[] constraintValues) {
		super(x, y, sideLength, sideLength, null);
		this.boxSideLenght = sideLength;
		checkBoxTriggered = checked;
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);;
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square). Also sets the text to be displayed on the side
	 * of this checkbox.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param sideLength       the size of the element's sides
	 * @param text             the text to be displayed
	 * @param checked          weather this check box is on or not
	 * @param constraintType   the constraints to apply to this element
	 * @param constraintValues the specific values to apply when constraining the
	 *                         element
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sideLength, String text, boolean checked, int constraintType,
			int[] constraintValues) {
		super(x, y, sideLength, sideLength, null);
		this.boxSideLenght = sideLength;
		this.textToDisplay = text;
		checkBoxTriggered = checked;
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
	}

	@Override
	protected void processClick(MouseEvent e) {
		checkBoxTriggered = !checkBoxTriggered;
	}

	@Override
	public void render(Graphics2D g) {

		Font startingFont = g.getFont();
		Color startingColor = g.getColor();

		Rectangle2D textBounds = g.getFontMetrics().getStringBounds(textToDisplay, g);
		float scale = (float) (this.height / textBounds.getHeight());

		this.width = (int) ((this.boxSideLenght * 1.35)
				+ g.getFontMetrics(g.getFont().deriveFont(startingFont.getSize() * scale))
						.getStringBounds(textToDisplay, g).getWidth()); // set the width of the element to contain the
																		// width of the text

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		if (checkBoxTriggered) {
			g.setColor(Color.WHITE);
			g.drawRect(this.x, this.y, this.boxSideLenght, this.boxSideLenght);
			g.fillRect(this.x + 3, this.y + 3, this.boxSideLenght - 6, this.boxSideLenght - 6);
			g.setColor(startingColor);

		} else {
			g.setColor(Color.WHITE);
			g.drawRect(this.x, this.y, this.boxSideLenght, this.boxSideLenght);
			g.setColor(startingColor);
		}

		if (textToDisplay != null) { // if we have text to display, then do it
			g.setFont(g.getFont().deriveFont(startingFont.getSize() * scale));
			g.setColor(Color.WHITE);
			g.drawString(textToDisplay, (int) (this.x + this.boxSideLenght * 1.35),
					(int) (this.y + this.boxSideLenght * 0.75));
			g.setColor(startingColor);
			g.setFont(startingFont);
		}

		this.bounds.setLocation(this.x, this.y);
	}

	/**
	 * Returns if this check box is on or not.
	 * 
	 * @return the state of this check box
	 * @since 1.1.0
	 */
	public boolean getCheckBoxTriggered() {
		return checkBoxTriggered;
	}

}
