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
	protected boolean checkBoxTriggered = false;

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
	}

	@Override
	protected void processClick(MouseEvent e) {
		checkBoxTriggered = !checkBoxTriggered;
	}

	@Override
	protected void render(Graphics g) {

		if (checkBoxTriggered) {
			Color startingColor = g.getColor();

			g.setColor(Color.WHITE);
			g.drawRect(x, y, width, height);
			g.fillRect(x + 3, y + 3, width - 6, height - 6);
			g.setColor(startingColor);

		} else {
			Color startingColor = g.getColor();

			g.setColor(Color.WHITE);
			g.drawRect(x, y, width, height);
			g.setColor(startingColor);
		}

		if (textToDisplay != null) {

			Color startingColor = g.getColor();
			Font startingFont = g.getFont();
			Rectangle2D textBounds = g.getFontMetrics().getStringBounds(textToDisplay, g);

			float scale = (float) (height / textBounds.getHeight());

			g.setFont(g.getFont().deriveFont(startingFont.getSize() * scale));
			g.setColor(Color.WHITE);
			g.drawString(textToDisplay, (int) (x + width * 1.5), (int) (y + height * 0.75));
			g.setColor(startingColor);
			g.setFont(startingFont);
		}
	}

}
