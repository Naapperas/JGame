package jGame.core.ui.hud;

import java.awt.Graphics;
import java.io.Serializable;

/**
 * This class represents a base element to be rendered in the HUD.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 *
 */
public abstract class UIHudElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6496514301669618772L;

	// the coordinates of the element
	protected int x, y;

	protected UIHudElement() {
	}

	/**
	 * Initializes this element at the specified <code>x</code> and <code>y</code>
	 * coordinates.
	 * 
	 * @param x the horizontal position of this element
	 * @param y the vertical position of this element
	 * @since 1.0.0
	 */
	public UIHudElement(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Renders this element on the screen.
	 * 
	 * @param g the {@link Graphics} object responsible for rendering this element
	 * @since 1.0.0
	 */
	protected abstract void render(Graphics g);

	/**
	 * Registers this element's input listener.
	 * 
	 * @since 1.0.0
	 */
	public abstract void registerInputListener();

	/**
	 * 
	 * Removes this element's input listener, if there is one.
	 * 
	 * @since 1.0.0
	 */
	public abstract void removeInputListener();
}