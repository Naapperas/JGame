package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

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

	public UIHudCheckBoxElement() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a new check box element in the given position and with the given side
	 * length (meant to be a square),
	 * 
	 * @param x          the horizontal position of this element
	 * @param y          the vertical position of this element
	 * @param sizeLenght the size of the element's sides
	 * @since 1.1.0
	 */
	public UIHudCheckBoxElement(int x, int y, int sizeLenght) {
		super(x, y, sizeLenght, sizeLenght, null);
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
	}

}
