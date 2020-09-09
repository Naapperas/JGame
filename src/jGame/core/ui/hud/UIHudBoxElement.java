package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author Nuno Pereira
 *
 */
public class UIHudBoxElement extends UIHudElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9098431952860544631L;

	private int width, height;
	private Color drawColor = null;

	public UIHudBoxElement() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public UIHudBoxElement(int x, int y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param drawingColor
	 */
	public UIHudBoxElement(int x, int y, int width, int height, Color drawingColor) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.drawColor = drawingColor;
	}

	@Override
	protected void render(Graphics g) {

		Color startingColor = g.getColor();

		if (drawColor != null)
			g.setColor(drawColor);

		g.fillRect(x, y, width, height);

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
