package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * Creates a colored square.
 * 
 * @author Nuno Pereira
 * @since 1.1.0
 */
public class UIHudBoxElement extends UIHudElement {

	private int width, height;
	private Color drawColor = Color.WHITE;

	{
		this.zIndex = 50;
	}

	// TODO: add constraints
	public UIHudBoxElement() {
	}

	/**
	 * Constructs a box element in the given location with the given dimensions,
	 * colored white.
	 * 
	 * @param x      the horizontal position of this element
	 * @param y      the vertical position of this element
	 * @param width  the width of this element
	 * @param height the height of this element
	 * @since 1.1.0
	 */
	public UIHudBoxElement(int x, int y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Constructs a box element in the given location with the given dimensions and
	 * with the desired color.
	 * 
	 * @param x            the horizontal position of this element
	 * @param y            the vertical position of this element
	 * @param width        the width of this element
	 * @param height       the height of this element
	 * @param drawingColor the color of this square
	 * @since 1.1.0
	 */
	public UIHudBoxElement(int x, int y, int width, int height, Color drawingColor) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.drawColor = drawingColor;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	@Override
	protected void render(Graphics2D g) {

		Color startingColor = g.getColor();

		if (drawColor != null)
			g.setColor(drawColor);

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		g.fillRect(this.x, this.y, width, height);

		g.setColor(startingColor);
	}

	@Override
	public void registerInputListener() {
		return;
	}

	@Override
	public void removeInputListener() {
		return;
	}

}
