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
	private int[] constraintValues = new int[4];

	public static int TOP = 0, RIGHT = 1, BOTTOM = 2, LEFT = 3;

	// generate bit masks for given constraint codes
	private static int BIT_MASK(int i) {
		return 1 << i;
	}

	/**
	 * Constructs a given constraint type out of the supplied constraint codes.
	 * 
	 * @param constraintTypes a variable number of constraints to constrain the
	 *                        element
	 * 
	 * @return the code of the given constrain type
	 * @since 1.1.0
	 */
	public static int concat(int... constraintTypes) {
		int constraints = 0;
		for (int i : constraintTypes) { constraints |= i; }
		return constraints;
	}

	/**
	 * Returns the default constraints to apply to a {@link UIHudElement}.
	 * 
	 * @param theElement the element to which the default constraints should be
	 *                   applied
	 * @return the default constraints for the given element
	 * @since 2.0.0
	 */
	public static Constraints defaultFor(UIHudElement theElement) {
		return new Constraints(theElement, Constraints.NONE, null);
	}

	/**
	 * The constraint code for no constraints, position is set by the client.
	 * 
	 * @since 1.1.0
	 */
	public static int NONE = 0;

	/**
	 * The constraint code for centering elements horizontally.
	 * 
	 * @since 1.1.0
	 */
	public static int CENTER_HORIZONTAL_CONSTRAINT = BIT_MASK(1);

	/**
	 * The constraint code for centering elements vertically.
	 * 
	 * @since 1.1.0
	 */
	public static int CENTER_VERTICAL_CONSTRAINT = BIT_MASK(2);

	/**
	 * The constraint code for centering elements horizontally and vertically. It is
	 * the same as a bitwise OR of {@link Constraints#CENTER_HORIZONTAL_CONSTRAINT}
	 * and {@link Constraints#CENTER_VERTICAL_CONSTRAINT}.
	 * 
	 * @since 1.1.0
	 */
	public static int CENTER_POINT_CONSTRAINT = concat(CENTER_HORIZONTAL_CONSTRAINT, CENTER_VERTICAL_CONSTRAINT);

	/**
	 * The constraint code for placing elements relative to the top-most border of
	 * the element's container, weather it be the window or another element.
	 * 
	 * @since 1.1.0
	 */
	public static int FROM_TOP_CONSTRAINT = BIT_MASK(3);

	/**
	 * The constraint code for placing elements relative to the right-most border of
	 * the element's container, weather it be the window or another element.
	 * 
	 * @since 1.1.0
	 */
	public static int FROM_RIGHT_CONSTRAINT = BIT_MASK(4);

	/**
	 * The constraint code for placing elements relative to the bottom-most border
	 * of the element's container, weather it be the window or another element.
	 * 
	 * @since 1.1.0
	 */
	public static int FROM_BOTTOM_CONSTRAINT = BIT_MASK(5);

	/**
	 * The constraint code for placing elements relative to the left-most border of
	 * the element's container, weather it be the window or another element.
	 * 
	 * @since 1.1.0
	 */
	public static int FROM_LEFT_CONSTRAINT = BIT_MASK(6);

	/**
	 * Constructs a Constraints object for the given {@code constrainedElement}, of
	 * type {@code constraintType} and using the values in {@code contraintValues}
	 * 
	 * @param constrainedElement the element to constrain
	 * @param constraintType     the type of constraint to apply to the element
	 * @param constraintValues   the values to apply when constraining the object
	 * @throws NullPointerException if constraintType is different from
	 *                              {@link Constraints#NONE} and constraintValues is
	 *                              null
	 * @since 1.1.0
	 */
	public Constraints(UIHudElement constrainedElement, int constraintType, int[] constraintValues)
			throws NullPointerException {
		this.constrainedElement = Objects.requireNonNull(constrainedElement);
		this.constraintType = constraintType;
		if (constraintType != Constraints.NONE && (constraintType & Constraints.CENTER_POINT_CONSTRAINT) == 0)
			Objects.requireNonNull(constraintValues); // can't have select constraining without providing values
		this.constraintValues = constraintValues;
	}

	/**
	 * Returns the {@code x} position of the constrained element, according to the
	 * applied constraints.
	 * 
	 * @return the x location of the constrained element
	 * @since 1.1.0
	 */
	public int getXLocation() {

		// UIHudCheckBoxElements have a box and some text, we need to account for the
		// text

		if ((this.constraintType & Constraints.CENTER_HORIZONTAL_CONSTRAINT) != 0) {
			if (this.constrainedElement.parentElement != null) {
				return this.constrainedElement.parentElement.x + this.constrainedElement.parentElement.width / 2
						- (constrainedElement.width / 2);
			} else {
				return (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getWidth() / 2)
						- (constrainedElement.width / 2);
			}
		} else if ((this.constraintType & Constraints.FROM_LEFT_CONSTRAINT) != 0) {
			if (this.constrainedElement.parentElement != null) {
				return this.constrainedElement.parentElement.x + this.constraintValues[Constraints.LEFT];
			} else {
				return this.constraintValues[Constraints.LEFT];
			}
		} else if ((this.constraintType & Constraints.FROM_RIGHT_CONSTRAINT) != 0) {
			if (this.constrainedElement.parentElement != null) {
				return this.constrainedElement.parentElement.x + this.constrainedElement.parentElement.width
						- this.constrainedElement.width - this.constraintValues[Constraints.RIGHT];
			} else {
				return (int) GameLauncher.getMainWindow().getWindowCanvas().getBounds().getWidth()
						- this.constraintValues[Constraints.RIGHT] - this.constrainedElement.width;
			}
		} else
			return constrainedElement.x;
	}

	/**
	 * Returns the {@code y} position of the constrained element, according to the
	 * applied constraints.
	 * 
	 * @return the y location of the constrained element
	 * @since 1.1.0
	 */
	public int getYLocation() {

		/*
		 * Strings get drawn from the left bottom corner (baseline), as opposed to other
		 * elements, check special cases
		 */

		/*
		 * If parent element is not null, constraint relative to the parent element
		 */

		if ((this.constraintType & Constraints.CENTER_VERTICAL_CONSTRAINT) != 0) {
			if (this.constrainedElement instanceof UIHudTextElement) {
				if (this.constrainedElement.parentElement != null)
					return this.constrainedElement.parentElement.y + (this.constrainedElement.parentElement.height / 2)
							+ (constrainedElement.height / 4);
				else
					return (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight() / 2)
						+ (constrainedElement.height / 4);
			} else {
				if (this.constrainedElement.parentElement != null) {
					return this.constrainedElement.parentElement.y + (this.constrainedElement.parentElement.height / 2)
							- (constrainedElement.height / 2);
				}else {
					return (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight() / 2)
						- (constrainedElement.height / 2);
				}
			}
		} else if ((this.constraintType & Constraints.FROM_TOP_CONSTRAINT) != 0) {
			if (this.constrainedElement instanceof UIHudTextElement) {
				if (this.constrainedElement.parentElement != null) {
					return this.constrainedElement.parentElement.y + this.constraintValues[Constraints.TOP]
							+ this.constrainedElement.height;
				} else {
					return this.constraintValues[Constraints.TOP] + this.constrainedElement.height;
				}
			} else {
				if (this.constrainedElement.parentElement != null) {
					return this.constrainedElement.parentElement.y + this.constraintValues[Constraints.TOP];
				} else {
					return this.constraintValues[Constraints.TOP];
				}
			}
		} else if ((this.constraintType & Constraints.FROM_BOTTOM_CONSTRAINT) != 0) {
			if (this.constrainedElement instanceof UIHudTextElement) {
				if (this.constrainedElement.parentElement != null) {
					return this.constrainedElement.parentElement.y + this.constrainedElement.parentElement.height
							- this.constraintValues[Constraints.BOTTOM];
				} else {
					return (int) GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight()
							- this.constraintValues[Constraints.BOTTOM];
				}
			} else {
				if (this.constrainedElement.parentElement != null) {
					return this.constrainedElement.parentElement.y + this.constrainedElement.parentElement.height
							- this.constraintValues[Constraints.BOTTOM] - this.constrainedElement.height;
				} else {
					return (int) GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight()
							- this.constraintValues[Constraints.BOTTOM] - this.constrainedElement.height;
				}
			}
		} else {
			if (this.constrainedElement instanceof UIHudTextElement) {
				return ((UIHudTextElement) constrainedElement).startingY + constrainedElement.height;
			} else
				return constrainedElement.y;
		}
	}

	@Override
	public String toString() {
		return "Constraints [constraintType=" + constraintType + "]";
	}

}
