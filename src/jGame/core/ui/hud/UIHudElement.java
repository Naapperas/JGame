package jGame.core.ui.hud;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.Timer;

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
	
	protected boolean disabled;

	// support for nested elements
	protected UIHudElement parentElement = null;

	// add tooltip on hover
	// since 2.0.0
	protected UIHudTooltipElement tooltip = null;
	private Point tooltipDrawPoint = new Point();
	protected boolean showTooltip = true, entered;
	private Timer tooltipEnterTimer = new Timer(2500, null);	

	private MouseAdapter mouseHoverListener = new MouseAdapter() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			
			if (UIHudElement.this.isDisabled()) return;
			
			Rectangle bounds = new Rectangle();
			bounds.setBounds(x, y, width, height);
			
			Rectangle tooltipBounds = new Rectangle();
			
			if (tooltip != null)
				tooltipBounds.setBounds(tooltip.x, tooltip.y, tooltip.width, tooltip.height);
			
			if (!bounds.contains(e.getPoint()) && !tooltipBounds.contains(e.getPoint())) {
				tooltipEnterTimer.stop();
				entered = false;
				UIHud.removeHUDUIElement(tooltip);
				return;
			}
			
			Point p = new Point(e.getX(), e.getY());
			
			UIHudElement.this.tooltipDrawPoint.setLocation(p);
			
			if (tooltipEnterTimer.isRunning() || entered) return;
					
			entered = true;
			tooltipEnterTimer.start();
		}

	};
	
	{
		tooltipEnterTimer.addActionListener(action -> {
			
			if (UIHudElement.this.isDisabled()) return;
			
			this.tooltip.setDrawPoint((Point)tooltipDrawPoint.clone());
			UIHud.addHUDUIElement(tooltip);
			
			tooltipEnterTimer.stop();
		});
	}
	
	public void setTooltip(UIHudTooltipElement tooltip) {
		this.tooltip = tooltip;
	}

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
		GameLauncher.getMainWindow().getWindowCanvas().addMouseMotionListener(mouseHoverListener);
	}

	/**
	 * 
	 * Removes this element's input listener(s), if there is one.
	 * 
	 * @since 1.0.0
	 */
	public void removeInputListener() {
		GameLauncher.getMainWindow().getWindowCanvas().removeMouseMotionListener(mouseHoverListener);
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
	
	/**
	 * Sets the disabled state of this element.
	 * 
	 * @param disabled the new disability state for this element
	 * @since 2.0.0
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	/**
	 * Gets the disabled state of this element.
	 * 
	 * @return the disabled state of this element´
	 * @since 2.0.0
	 */
	public boolean isDisabled() {
		return this.disabled;
	}

	@Override
	public String toString() {

		return this.getClass().getSimpleName() + " [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height
				+ ", drawConstraints=" + drawConstraints.toString() + ", zIndex=" + zIndex + "]";
	}

}