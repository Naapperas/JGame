package jGame.core.ui.hud;

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
	public static int CENTER_HORIZONTAL_CONSTRAINT = 1;

	/**
	 * 
	 */
	public static int CENTER_VERTICAL_CONSTRAINT = 2;

	public Constraints(UIHudElement constrainedElement, int constraintType) {
		this.constrainedElement = constrainedElement;
		this.constraintType = constraintType;
	}

	public int getXLocation() {

		if (this.constraintType == Constraints.CENTER_HORIZONTAL_CONSTRAINT) {
			return (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getWidth() / 2)
					- (constrainedElement.width / 2);
		} else
			return constrainedElement.x;
		
	}
}
