package jGame.core.ui.hud;

import java.util.Objects;

import jGame.core.launcher.GameLauncher;

/**
 * Display constraints to a {@link UIHudElement}.
 * 
 * @author Nuno Pereira
 * @since 1.1.0
 */
public class Constraints {

	// the element to be constrained
	private UIHudElement constrainedElement;
	private int constraintType;
	private int[] contraintValues = new int[4];

	public static int TOP = 0, RIGHT = 1, BOTTOM = 2, LEFT = 3;

	// generate bit masks for given constraint codes
	private static int BIT_MASK(int i) {
		return 1 << i;
	}

	/**
	 * Constructs a given constraint type out of the supplied constraints.
	 * 
	 * @param constraintTypes a variable number of constraints to constrain the
	 *                        element
	 * @return the code of the given constrain type
	 * @since 1.1.0
	 */
	public static int concatConstraints(int... constraintTypes) {
		int constraints = 0;
		for (int i : constraintTypes) { constraints |= i; }
		return constraints;
	}

	/**
	 * 
	 */
	public static int NONE = 0;

	/**
	 * 
	 */
	public static int CENTER_HORIZONTAL_CONSTRAINT = BIT_MASK(1);

	/**
	 * 
	 */
	public static int CENTER_VERTICAL_CONSTRAINT = BIT_MASK(2);

	/**
	 * 
	 */
	public static int FROM_TOP_CONSTRAINT = BIT_MASK(3);

	/**
	 * 
	 */
	public static int FROM_RIGHT_CONSTRAINT = BIT_MASK(4);

	/**
	 * 
	 */
	public static int FROM_BOTTOM_CONSTRAINT = BIT_MASK(5);

	/**
	 * 
	 */
	public static int FROM_LEFT_CONSTRAINT = BIT_MASK(6);

	/**
	 * 
	 * @param constrainedElement
	 * @param constraintType
	 * @param contraintValues
	 */
	public Constraints(UIHudElement constrainedElement, int constraintType, int[] contraintValues) {
		this.constrainedElement = Objects.requireNonNull(constrainedElement);
		this.constraintType = constraintType;
		this.contraintValues = contraintValues;
	}

	/**
	 * Returns the {@code x} position of the constrained element, according to the
	 * applied constraints.
	 * 
	 * @return the x location of the constrained element
	 * @since 1.1.0
	 */
	public int getXLocation() {
		if ((this.constraintType & Constraints.CENTER_HORIZONTAL_CONSTRAINT) != 0) {
			return (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getWidth() / 2)
					- (constrainedElement.width / 2);
		} else if ((this.constraintType & Constraints.FROM_LEFT_CONSTRAINT) != 0) {
			return this.contraintValues[Constraints.LEFT];
		} else if ((this.constraintType & Constraints.FROM_RIGHT_CONSTRAINT) != 0) {
			return (int) GameLauncher.getMainWindow().getWindowCanvas().getBounds().getWidth()
					- this.contraintValues[Constraints.RIGHT];
		} else
			return constrainedElement.x;
	}

	/**
	 * Returns the {@code y} position of the constrained element, according to the
	 * applied constraints.
	 * 
	 * 
	 * @return the y location of the constrained element
	 * @since 1.1.0
	 */
	public int getYLocation() {
		if ((this.constraintType & Constraints.CENTER_VERTICAL_CONSTRAINT) != 0) {
			return (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight() / 2)
					- (constrainedElement.height / 2);
		} else if ((this.constraintType & Constraints.FROM_TOP_CONSTRAINT) != 0) {
			return this.contraintValues[Constraints.TOP];
		} else if ((this.constraintType & Constraints.FROM_BOTTOM_CONSTRAINT) != 0) {
			return (int) GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight()
					- this.contraintValues[Constraints.BOTTOM];
		} else
			return constrainedElement.y;
	}
}
