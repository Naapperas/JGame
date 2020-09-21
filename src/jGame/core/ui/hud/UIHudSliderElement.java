package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

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

	private float handleX = 0;

	private Rectangle bounds = null;

	private static final int SLIDER_HANDLE_WIDTH = 30, SLIDER_HANDLE_HEIGHT = 50, SLIDER_WIDTH = 300,
			SLIDER_HEIGHT = 20;

	private float handleXOffset = 0;

	private MouseAdapter inputListener = new MouseAdapter() {

		UIHudSliderElement theElement = UIHudSliderElement.this;

		@Override
		public void mousePressed(MouseEvent e) {
			if (theElement.bounds.contains(e.getPoint())) {
				isMouseClicked = true;
				theElement.handleXOffset = e.getX() - theElement.handleX;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isMouseClicked = false;
		}
		
	};

	private List<SliderCallback> callbackList = new LinkedList<SliderCallback>();

	public UIHudSliderElement() {
	}

	public UIHudSliderElement(int x, int y, int minValue, int maxValue) {
		super(x, y);
		this.width = SLIDER_WIDTH;
		this.height = SLIDER_HEIGHT;
		this.bounds = new Rectangle(SLIDER_HANDLE_WIDTH, SLIDER_HANDLE_HEIGHT);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = 0;
		try {
			this.handleX = MathUtils.map(minValue, minValue, maxValue, this.drawConstraints.getXLocation(),
					this.drawConstraints.getXLocation() + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1);
		} catch (IllegalArgumentException ex) {
			
		}
	}

	public UIHudSliderElement(int x, int y, int minValue, int maxValue, int constraintType,
			int[] constraintSpecs) {
		super(x, y);
		this.width = SLIDER_WIDTH;
		this.height = SLIDER_HEIGHT;
		this.bounds = new Rectangle(SLIDER_HANDLE_WIDTH, SLIDER_HANDLE_HEIGHT);
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = 0;
		try {
			this.handleX = MathUtils.map(minValue, minValue, maxValue, this.drawConstraints.getXLocation(),
					this.drawConstraints.getXLocation() + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1);
		} catch (IllegalArgumentException ex) {
			
		}
	}

	public UIHudSliderElement(int x, int y, int minValue, int maxValue, int startValue) {
		super(x, y);
		this.width = SLIDER_WIDTH;
		this.height = SLIDER_HEIGHT;
		this.bounds = new Rectangle(SLIDER_HANDLE_WIDTH, SLIDER_HANDLE_HEIGHT);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.value = startValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		try {
			this.handleX = MathUtils.map(startValue, minValue, maxValue, this.drawConstraints.getXLocation(),
					this.drawConstraints.getXLocation() + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1);
		} catch (IllegalArgumentException ex) {
			
		}
	}

	public UIHudSliderElement(int x, int y, int minValue, int maxValue, int startValue, int constraintType,
			int[] constraintSpecs) {
		super(x, y);
		this.width = SLIDER_WIDTH;
		this.height = SLIDER_HEIGHT;
		this.bounds = new Rectangle(SLIDER_HANDLE_WIDTH, SLIDER_HANDLE_HEIGHT);
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
		this.value = startValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		try {
			this.handleX = MathUtils.map(startValue, minValue, maxValue, this.drawConstraints.getXLocation(),
					this.drawConstraints.getXLocation() + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1);
		} catch (IllegalArgumentException ex) {

		}
	}

	@Override
	protected void render(Graphics2D g) {

		boolean valueChanged = false;

		Color startingColor = g.getColor();
		Font startingFont = g.getFont();

		g.setFont(startingFont.deriveFont(startingFont.getSize2D() * 2));

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();
		
		int handleY = (y + SLIDER_HEIGHT / 2 - SLIDER_HANDLE_HEIGHT / 2);

		if (this.isMouseClicked) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, GameLauncher.getMainWindow().getWindowCanvas());

			float temp = MathUtils.clamp((int) p.getX() - handleXOffset, x, x + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1);

			if (temp != handleX)
				valueChanged = true;

			handleX = temp;
		}
		
		// TODO: make drawing math in floats instead of integers in order to get more
		// precise numbers
		try {
			value = MathUtils.map(handleX, x, x + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1, minValue, maxValue);
		} catch (IllegalArgumentException ex) {/* ignore */}

		if (valueChanged)
			callbackList.forEach((r) -> { r.run(value); });

		g.setColor(Color.WHITE);
		g.drawRoundRect(x, y, SLIDER_WIDTH, SLIDER_HEIGHT, 25, 25);

		g.fillRoundRect((int) handleX, handleY, SLIDER_HANDLE_WIDTH, SLIDER_HANDLE_HEIGHT, 15, 15);
		
		FontMetrics fontMetrics = g.getFontMetrics();

		int minValueTextWidth = (int) fontMetrics.getStringBounds(minValue + "", g).getWidth();

		g.drawString(minValue + "", x - minValueTextWidth - 10,
				y + fontMetrics.getAscent() / 5 * 4);

		g.drawString(maxValue + "", x + SLIDER_WIDTH + 10, y + fontMetrics.getAscent() / 5 * 4);

		int valueTextWidth = (int) fontMetrics.getStringBounds(value + "", g).getWidth();

		g.drawString(value + "", (int) handleX + SLIDER_HANDLE_WIDTH / 2 - valueTextWidth / 2, handleY - 10);

		g.setColor(startingColor);
		g.setFont(startingFont);

		this.bounds.setLocation((int) handleX, handleY);
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

	/**
	 * 
	 * @param r
	 */
	public void addCallback(SliderCallback r) {
		callbackList.add(r);
	}

	/**
	 * 
	 * @author Nuno Pereira
	 *
	 */
	public interface SliderCallback {

		public void run(final float newValue);

	}
}
