package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import jGame.core.launcher.GameLauncher;
import jGame.core.utils.MathUtils;

/**
 * Class responsible for creating a slider, in order to get a value in a certain
 * range.
 * 
 * @author Nuno Pereira
 * @since 1.2.0
 */
public class UIHudSliderElement extends UIHudElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1544055730175360067L;

	private float value = 0, minValue = 0, maxValue = 0;
	private boolean isMouseClicked = false;

	private static final int SLIDER_HANDLE_WIDTH = 30, SLIDER_HANDLE_HEIGHT = 50, SLIDER_WIDTH = 300,
			SLIDER_HEIGHT = 20;

	private MouseAdapter inputListener = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			isMouseClicked = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isMouseClicked = false;
		}
		
	};

	public UIHudSliderElement() {
	}

	public UIHudSliderElement(int x, int y, float minValue, float maxValue) {
		super(x, y);
		this.width = 300;
		this.height = 20;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = 0;
	}

	public UIHudSliderElement(int x, int y, float minValue, float maxValue, int constraintType, int[] constraintSpecs) {
		super(x, y);
		this.width = 300;
		this.height = 20;
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = 0;
	}

	public UIHudSliderElement(int x, int y, float minValue, float maxValue, float startValue) {
		super(x, y);
		this.width = 300;
		this.height = 20;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.value = startValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public UIHudSliderElement(int x, int y, float minValue, float maxValue, float startValue, int constraintType,
			int[] constraintSpecs) {
		super(x, y);
		this.width = 300;
		this.height = 20;
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
		this.value = startValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	protected void render(Graphics g) {

		Color startingColor = g.getColor();
		Font startingFont = g.getFont();

		g.setFont(startingFont.deriveFont(startingFont.getSize2D() * 2));

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();
		
		int handleY = (y + SLIDER_HEIGHT / 2 - SLIDER_HANDLE_HEIGHT / 2);
		
		Point p = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(p, GameLauncher.getMainWindow().getWindowCanvas());
		 
		int newX = MathUtils.clamp((int) p.getX(), x, x + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1);
		
		value = (int) MathUtils.map(newX, x, x + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1, minValue, maxValue);

		g.setColor(Color.WHITE);
		g.drawRoundRect(x, y, SLIDER_WIDTH, SLIDER_HEIGHT, 25, 25);
		
		g.fillRoundRect(newX, handleY, SLIDER_HANDLE_WIDTH,
				SLIDER_HANDLE_HEIGHT, 15, 15);
		
		FontMetrics fontMetrics = g.getFontMetrics();

		int minValueTextWidth = (int) fontMetrics.getStringBounds(minValue + "", g).getWidth();

		g.drawString(minValue + "", x - minValueTextWidth - 10,
				y + fontMetrics.getAscent() / 5 * 4);

		g.drawString(maxValue + "", x + SLIDER_WIDTH + 10, y + fontMetrics.getAscent() / 5 * 4);

		int valueTextWidth = (int) fontMetrics.getStringBounds(value + "", g).getWidth();

		g.drawString(value + "", newX + SLIDER_HANDLE_WIDTH / 2 - valueTextWidth / 2, handleY - 10);

		g.setColor(startingColor);
		g.setFont(startingFont);
	}

	@Override
	public void registerInputListener() {
		GameLauncher.getMainWindow().addMouseInputListener(inputListener, this);
	}

	@Override
	public void removeInputListener() {
		GameLauncher.getMainWindow().removeMouseInputListener(inputListener, this);
	}

	/**
	 * Returns the value of this slider.
	 * 
	 * @return the value of this slider
	 * @since 1.2.0
	 */
	public float getValue() {
		return value;
	}

}
