package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * Class representing a toggable checkbox.
 * 
 * @author Nuno Pereira
 * @since 1.1.0
 */
public class UIHudCheckBoxElement extends UIHudButtonElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4408367719217626720L;

	// the state of the checkbox
	private boolean checkBoxTriggered = false;

	private String textToDisplay = null;

	public UIHudCheckBoxElement() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square).
	 * 
	 * @param x          the horizontal position of this element
	 * @param y          the vertical position of this element
	 * @param sizeLength the size of the element's sides
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sizeLength) {
		super(x, y, sizeLength, sizeLength, null);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square). Also sets the text to be displayed on the side
	 * of this checkbox.
	 * 
	 * @param x          the horizontal position of this element
	 * @param y          the vertical position of this element
	 * @param sizeLength the size of the element's sides
	 * @param text       the text to be displayed
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sizeLength, String text) {
		super(x, y, sizeLength, sizeLength, null);
		this.textToDisplay = text;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square).
	 * 
	 * @param x          the horizontal position of this element
	 * @param y          the vertical position of this element
	 * @param sizeLength the size of the element's sides
	 * @param checked    weather this check box is already on or not
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sizeLength, boolean checked) {
		super(x, y, sizeLength, sizeLength, null);
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
	 * @param sizeLength the size of the element's sides
	 * @param text       the text to be displayed
	 * @param checked    weather this check box is on or not
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sizeLength, String text, boolean checked) {
		super(x, y, sizeLength, sizeLength, null);
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
	 * @param sizeLength       the size of the element's sides
	 * @param constraintType   the constraints to apply to this element
	 * @param constraintValues the specific values to apply when constraining the
	 *                         element
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sizeLength, int constraintType, int[] constraintValues) {
		super(x, y, sizeLength, sizeLength, null);
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square). Also sets the text to be displayed on the side
	 * of this checkbox.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param sizeLength       the size of the element's sides
	 * @param text             the text to be displayed
	 * @param constraintType   the constraints to apply to this element
	 * @param constraintValues the specific values to apply when constraining the
	 *                         element
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sizeLength, String text, int constraintType, int[] constraintValues) {
		super(x, y, sizeLength, sizeLength, null);
		this.textToDisplay = text;
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square).
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param sizeLength       the size of the element's sides
	 * @param checked          weather this check box is already on or not
	 * @param constraintType   the constraints to apply to this element
	 * @param constraintValues the specific values to apply when constraining the
	 *                         element
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sizeLength, boolean checked, int constraintType,
			int[] constraintValues) {
		super(x, y, sizeLength, sizeLength, null);
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
	 * @param sizeLength       the size of the element's sides
	 * @param text             the text to be displayed
	 * @param checked          weather this check box is on or not
	 * @param constraintType   the constraints to apply to this element
	 * @param constraintValues the specific values to apply when constraining the
	 *                         element
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sizeLength, String text, boolean checked, int constraintType,
			int[] constraintValues) {
		super(x, y, sizeLength, sizeLength, null);
		this.textToDisplay = text;
		checkBoxTriggered = checked;
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
	}

	@Override
	protected void processClick(MouseEvent e) {
		checkBoxTriggered = !checkBoxTriggered;
	}

	@Override
	protected void render(Graphics g) {

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		if (checkBoxTriggered) {
			Color startingColor = g.getColor();

			g.setColor(Color.WHITE);
			g.drawRect(this.x, this.y, this.width, this.height);
			g.fillRect(this.x + 3, this.y + 3, this.width - 6, this.height - 6);
			g.setColor(startingColor);

		} else {
			Color startingColor = g.getColor();

			g.setColor(Color.WHITE);
			g.drawRect(this.x, this.y, this.width, this.height);
			g.setColor(startingColor);
		}

		if (textToDisplay != null) {

			Color startingColor = g.getColor();
			Font startingFont = g.getFont();
			Rectangle2D textBounds = g.getFontMetrics().getStringBounds(textToDisplay, g);

			float scale = (float) (this.height / textBounds.getHeight());

			g.setFont(g.getFont().deriveFont(startingFont.getSize() * scale));
			g.setColor(Color.WHITE);
			g.drawString(textToDisplay, (int) (this.x + this.width * 1.35), (int) (this.y + this.height * 0.75));
			g.setColor(startingColor);
			g.setFont(startingFont);
		}
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
