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

	public static int NONE = 0;

	/**
	 * 
	 */
	public static int CENTER_HORIZONTAL_CONSTRAINT = 0b01000000;

	/**
	 * 
	 */
	public static int CENTER_VERTICAL_CONSTRAINT = 0b10000000;

	/**
	 * 
	 * @param constrainedElement
	 * @param constraintType
	 */
	public Constraints(UIHudElement constrainedElement, int constraintType) {
		this.constrainedElement = Objects.requireNonNull(constrainedElement);
		this.constraintType = constraintType;
	}

	/**
	 * 
	 * @return the x location of the constrained element
	 */
	public int getXLocation() {
		if ((this.constraintType & Constraints.CENTER_HORIZONTAL_CONSTRAINT) != 0) {
			return (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getWidth() / 2)
					- (constrainedElement.width / 2);
		} else
			return constrainedElement.x;
	}

	/**
	 * 
	 * @return the y location of the constrained element
	 */
	public int getYLocation() {
		if ((this.constraintType & Constraints.CENTER_VERTICAL_CONSTRAINT) != 0) {
			return (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight() / 2)
					- (constrainedElement.height / 2);
		} else
			return constrainedElement.y;
	}
}
