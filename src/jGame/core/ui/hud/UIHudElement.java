package jGame.core.ui.hud;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import jGame.core.launcher.GameLauncher;

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

	// add tooltip on hover
	// since 2.0.0
	protected UIHudTooltipElement tooltip = null;
	protected boolean showTooltip = true;
	private MouseAdapter mouseHoverListener = new MouseAdapter() {

		@Override
		public void mouseEntered(MouseEvent e) {
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
		}

	};

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
	public abstract void render(Graphics2D g);

	/**
	 * Registers this element's input listener(s).
	 * 
	 * @since 1.0.0
	 */
	public void registerInputListener() {
		GameLauncher.getMainWindow().addMouseInputListener(this.mouseHoverListener, this);
	}

	/**
	 * 
	 * Removes this element's input listener(s), if there is one.
	 * 
	 * @since 1.0.0
	 */
	public void removeInputListener() {
		GameLauncher.getMainWindow().removeMouseInputListener(this.mouseHoverListener, this);
	};

	/**
	 * Sets the parent element of this element.
	 * 
	 * @param newParent the new parent element of this element
	 * @since 1.2.0
	 */
	public void setParentElement(UIHudElement newParent) {
		this.parentElement = newParent;
	}

	/**
	 * Sets the zIndex of this element
	 * 
	 * @param newIndex the new draw index of this element
	 * @since 1.2.0
	 */
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

	/**
	 * 
	 * @param indent
	 * @throws IOException
	 * @since 2.0.0
	 */
	public void serialize(int indent) throws IOException {
		return;
	}

	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height
				+ ", drawConstraints=" + drawConstraints.toString() + ", zIndex=" + zIndex + "]";
	}

}