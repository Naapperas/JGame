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
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import jGame.core.launcher.GameLauncher;
import jGame.core.utils.MathUtils;
import jGame.logging.ProgramLogger;

/**
 * Class responsible for creating a horizontal slider, in order to get a value
 * in a certain range. Minimum and maximum values can be anything (inside the
 * restrictions of the {@code int} data type (see {@link Integer#MAX_VALUE} and
 * {@link Integer#MIN_VALUE}), as long as the minimum value is below the
 * maximum.
 * 
 * @author Nuno Pereira
 * @since 1.2.0
 */
public class UIHudSliderElement extends UIHudElement {


	// current, mimum and maximum values for the slider
	private float value = 0, minValue = 0, maxValue = 0;

	/**
	 * Returns the minimum value that can be obtained from the slider.
	 * 
	 * @return the minimum value that can be obtained from the slider
	 * @since 1.2.0
	 */
	public float getMinValue() {
		return minValue;
	}

	/**
	 * Returns the maximum value that can be obtained from the slider.
	 * 
	 * @return the maximum value that can be obtained from the slider
	 * @since 1.2.0
	 */
	public float getMaxValue() {
		return maxValue;
	}

	// check weather the mouse is clicked or not. This variable is only set to true
	// if the mouse is over the "handle", so effectively this variable checks to see
	// if the mouse is being pressed on the handle.
	private boolean isMouseClicked = false;

	private float handleX = 0;

	// the bounds of the "handle", to check against the mouse position.
	private Rectangle bounds = null;


	private static final int SLIDER_HANDLE_WIDTH = 30, SLIDER_HANDLE_HEIGHT = 50, SLIDER_WIDTH = 300,
			SLIDER_HEIGHT = 20;

	// float variable to make the movement of the "handle" non-snappy: if not
	// implemented, the user would click on the "handle" and its x position would
	// snap to the mouse's y position in game coordinate space.
	private float handleXOffset = 0;

	private MouseAdapter inputListener = new MouseAdapter() {

		UIHudSliderElement theElement = UIHudSliderElement.this;

		@Override
		public void mousePressed(MouseEvent e) {
			if (theElement.bounds.contains(e.getPoint())) {
				isMouseClicked = true;
				theElement.handleXOffset = e.getX() - theElement.handleX; // set the offset to account for when setting
																			// the x position of the handle
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isMouseClicked = false; // we only want to check if the mouse is pressed on the handle, so whenever the
									// user
									// releases the mouse button we want to stop moving the handle
		}
		
	};

	// a list of SliderCallback's to be run when the value of the slider changes
	private List<SliderCallback> callbackList = new LinkedList<SliderCallback>();

	{
		// set the ordering position in the "render queue"
		this.zIndex = 50;
	}

	// default constructor, required by the serialization system, to be worked on
	public UIHudSliderElement() {
	}

	/**
	 * Creates a {@code UIHudSliderElement} in the given {@code x} and {@code y}
	 * position and with the given minimum and maximum values. This elements value
	 * is set to the minimum value.
	 * 
	 * @param x        the horizontal position in which to render this element
	 * @param y        the vertical position in which to render this element
	 * @param minValue the minimum value this slider can give
	 * @param maxValue the maximum value this slider can give
	 * @since 1.2.0
	 */
	public UIHudSliderElement(int x, int y, int minValue, int maxValue) {
		super(x, y);
		this.width = SLIDER_WIDTH;
		this.height = SLIDER_HEIGHT;
		this.bounds = new Rectangle(SLIDER_HANDLE_WIDTH, SLIDER_HANDLE_HEIGHT);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = minValue;
		try {
			this.handleX = MathUtils.map(this.value, minValue, maxValue, this.drawConstraints.getXLocation(),
					this.drawConstraints.getXLocation() + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1f);
		} catch (IllegalArgumentException ex) {
			ProgramLogger.writeErrorLog(ex, "Attemtping to map value out of range!");
		}
	}

	/**
	 * Creates a {@code UIHudSliderElement} in the given {@code x} and {@code y}
	 * position and with the given minimum and maximum values, using the given
	 * constraints. This elements value is set to the minimum value.
	 * 
	 * @param x               the horizontal position in which to render this
	 *                        element
	 * @param y               the vertical position in which to render this element
	 * @param minValue        the minimum value this slider can give
	 * @param maxValue        the maximum value this slider can give
	 * @param constraintType  the type of constraints to apply to this element
	 * @param constraintSpecs the specific values to apply when constraining the
	 *                        element, or {@code null} if not needed
	 * @since 1.2.0
	 * @see Constraints
	 */
	public UIHudSliderElement(int x, int y, int minValue, int maxValue, int constraintType,
			int[] constraintSpecs) {
		super(x, y);
		this.width = SLIDER_WIDTH;
		this.height = SLIDER_HEIGHT;
		this.bounds = new Rectangle(SLIDER_HANDLE_WIDTH, SLIDER_HANDLE_HEIGHT);
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = minValue;
		try {
			this.handleX = MathUtils.map(minValue, minValue, maxValue, this.drawConstraints.getXLocation(),
					this.drawConstraints.getXLocation() + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1f);
		} catch (IllegalArgumentException ex) {
			ProgramLogger.writeErrorLog(ex, "Attemtping to map value out of range!");
		}
	}

	/**
	 * Creates a {@code UIHudSliderElement} in the given {@code x} and {@code y}
	 * position and with the given minimum, maximum and start values.
	 * 
	 * @param x          the horizontal position in which to render this element
	 * @param y          the vertical position in which to render this element
	 * @param minValue   the minimum value this slider can give
	 * @param maxValue   the maximum value this slider can give
	 * @param startValue the start value of this slider
	 * @since 1.2.0
	 */
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
					this.drawConstraints.getXLocation() + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1f);
		} catch (IllegalArgumentException ex) {
			ProgramLogger.writeErrorLog(ex, "Attemtping to map value out of range!");
		}
	}

	/**
	 * Creates a {@code UIHudSliderElement} in the given {@code x} and {@code y}
	 * position and with the given minimum, maximum and start values, using the
	 * given constraints.
	 * 
	 * @param x               the horizontal position in which to render this
	 *                        element
	 * @param y               the vertical position in which to render this element
	 * @param minValue        the minimum value this slider can give
	 * @param maxValue        the maximum value this slider can give
	 * @param startValue      the start value of this slider
	 * @param constraintType  the type of constraints to apply to this element
	 * @param constraintSpecs the specific values to apply when constraining the
	 *                        element, or {@code null} if not needed
	 * @since 1.2.0
	 * @see Constraints
	 */
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
			this.handleX = MathUtils.map(this.value, minValue, maxValue, this.drawConstraints.getXLocation(),
					this.drawConstraints.getXLocation() + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1); // handleX is
																											// displaced
																											// by 100,
																											// dunno why
		} catch (IllegalArgumentException ex) {
			ProgramLogger.writeErrorLog(ex, "Attemtping to map value out of range!");
		}
	}

	@Override
	public void render(Graphics2D g) {

		this.handleX = MathUtils.map(this.value, minValue, maxValue, this.drawConstraints.getXLocation(),
				this.drawConstraints.getXLocation() + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1); // this is a workaround
																								// to a problem where
																								// the handleX would get
																								// shifted because of
																								// constraints
																								// redefinition

		boolean valueChanged = false;

		Color startingColor = g.getColor();
		Font startingFont = g.getFont();

		// original font is too small, double its size
		g.setFont(startingFont.deriveFont(startingFont.getSize2D() * 2));

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();
		
		// verticaly center the handle in the middle of the slider
		int handleY = (y + SLIDER_HEIGHT / 2 - SLIDER_HANDLE_HEIGHT / 2);

		if (this.isMouseClicked) { // mouse pressed, update handleX
			Point p = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, GameLauncher.getMainWindow().getWindowCanvas());

			// create temporary variable too check for updates
			float temp = MathUtils.clamp((int) p.getX() - handleXOffset, x, x + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1);

			if (temp != handleX)
				valueChanged = true;

			handleX = temp;
		}
		
		// TODO: make drawing math in floats instead of integers in order to get more
		// precise numbers
		try {
			value = MathUtils.map(handleX, x, x + SLIDER_WIDTH - SLIDER_HANDLE_WIDTH + 1, minValue, maxValue);
		} catch (IllegalArgumentException ex) {/* ignore any errors, just catch them so the program doesn't crash */}

		if (valueChanged) // if the value has changed, invoked any callbacks registered to this element
			callbackList.forEach((r) -> { r.run(value); });

		g.setColor(Color.WHITE);
		g.drawRoundRect(x, y, SLIDER_WIDTH, SLIDER_HEIGHT, 25, 25);

		g.fillRoundRect((int) handleX, handleY, SLIDER_HANDLE_WIDTH, SLIDER_HANDLE_HEIGHT, 15, 15);
		
		/////////////////////// DRAWING OF SLIDER TEXT///////////////////////////

		FontMetrics fontMetrics = g.getFontMetrics();

		int minValueTextWidth = (int) fontMetrics.getStringBounds(minValue + "", g).getWidth();

		g.drawString(minValue + "", x - minValueTextWidth - 10,
				y + fontMetrics.getAscent() / 5 * 4);

		g.drawString(maxValue + "", x + SLIDER_WIDTH + 10, y + fontMetrics.getAscent() / 5 * 4);

		DecimalFormat valueFormatter = new DecimalFormat("#.#");

		String fomattedValue = valueFormatter.format(value);

		int valueTextWidth = (int) fontMetrics.getStringBounds(fomattedValue, g).getWidth();

		g.drawString(fomattedValue, (int) handleX + SLIDER_HANDLE_WIDTH / 2 - valueTextWidth / 2, handleY - 10);

		////////////////////////////////////////////////////////////////////////

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
	 * Sets the new value for this slider.
	 * 
	 * @param newValue the new value for this slider
	 * @since 2.0.0
	 */
	public void setValue(float newValue) {
		this.value = newValue;
	}

	/**
	 * Add a callback to be called when this slider value changes.
	 * 
	 * @param r a callback to run when the value of this slider changes
	 * @since 1.2.0
	 */
	public void addCallback(SliderCallback r) {
		callbackList.add(r);
		callbackList.forEach((callback) -> { callback.run(value); }); // run all callbacks because we might want to
																		// change some values even when the user doesn't
																		// change the value through the slider
	}

	/**
	 * Functional interface representing a callback to be executed when the value of
	 * the slider changes.
	 * 
	 * @author Nuno Pereira
	 * @since 1.2.0
	 */
	@FunctionalInterface
	public interface SliderCallback {

		/**
		 * Performs the given action when the value of this slider changes, passing the
		 * new value as an argument.
		 * 
		 * @param newValue the new value of this slider
		 * @since 1.2.0
		 */
		public void run(final float newValue);

	}
}
