package jGame.core.ui.hud;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * This class represents a base element to be rendered in the HUD.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public abstract class UIHudElement {

	// the coordinates of the element
	protected int x, y, width, height;
	protected int zIndex;
	protected Constraints drawConstraints;
	
	// support for nested elements
	protected UIHudElement parentElement = null;

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
	 * Initializes this element at the specified <code>x</code> and <code>y</code>
	 * coordinates.
	 * 
	 * @param x      the horizontal position of this element
	 * @param y      the vertical position of this element
	 * @param width  the width of this element
	 * @param height the height of this element
	 * @since 1.0.0
	 */
	public UIHudElement(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Renders this element on the screen.
	 * 
	 * @param g the {@link Graphics} object responsible for rendering this element
	 * @since 1.0.0
	 */
	protected abstract void render(Graphics2D g);

	/**
	 * Registers this element's input listener(s).
	 * 
	 * @since 1.0.0
	 */
	public abstract void registerInputListener();

	/**
	 * 
	 * Removes this element's input listener(s), if there is one.
	 * 
	 * @since 1.0.0
	 */
	public abstract void removeInputListener();

	public void setZIndex(int newIndex) {
		this.zIndex = newIndex;
	}

	/**
	 * Resets the new draw constraints for this elements.
	 * 
	 * @param drawConstraints the new draw constraints for this element
	 * @since 1.2.0
	 */
	public void setDrawConstraints(Constraints drawConstraints) {
		this.drawConstraints = drawConstraints;
	}

	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height
				+ ", drawConstraints="
				+ drawConstraints + ", zIndex=" + zIndex + "]";
	}

}