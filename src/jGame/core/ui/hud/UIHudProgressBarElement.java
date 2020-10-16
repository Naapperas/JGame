package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Graphics2D;

import jGame.core.utils.MathUtils;

/**
 * Progress bar that tracks progression of anything that requires so.
 * 
 * @author Nuno Pereira
 * @since 1.2.0
 *
 */
public class UIHudProgressBarElement extends UIHudElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3065269234183461086L;

	/**
	 * The current progress tracked by this progress bar. Must be between 0 and 100.
	 * 
	 * @since 1.2.0
	 */
	private int progress = 0;

	{
		this.zIndex = 50;
	}

	public UIHudProgressBarElement() {
	}

	/**
	 * Creates a progress bar element in the given position and with the given
	 * dimensions whose initial value is equal to {@code startProgressValue}. This
	 * element's constraints are set to {@link Constraints#NONE}.
	 * 
	 * @param x                  the horizontal position of the top-left corner of
	 *                           this element
	 * @param y                  the vertical position of the top-most corner of
	 *                           this element
	 * @param width              the width of the element
	 * @param height             the height of the element
	 * @param startProgressValue the starting progress value, must be between 0 and
	 *                           100
	 * @since 1.2.0
	 */
	public UIHudProgressBarElement(int x, int y, int width, int height, int startProgressValue) {
		super(x, y, width, height);
		this.progress = MathUtils.clamp(startProgressValue, 0, 100);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Creates a progress bar element in the given position and with the given
	 * dimensions, using the applied constraints. This elements progress value is
	 * set to 0.
	 * 
	 * @param x               the horizontal position of the top-left corner of this
	 *                        element
	 * @param y               the vertical position of the top-most corner of this
	 *                        element
	 * @param width           the width of the element
	 * @param height          the height of the element
	 * @param constraintType  the type of constraints to apply
	 * @param constraintSpecs the values to apply when constraining the element
	 * @since 1.2.0
	 */
	public UIHudProgressBarElement(int x, int y, int width, int height, int constraintType, int[] constraintSpecs) {
		super(x, y, width, height);
		this.progress = 0;
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
	}

	/**
	 * Creates a progress bar in the given location and with the given dimensions,
	 * using the applied constraints and with starting value equal to
	 * {@code startProgressValue}.
	 * 
	 * @param x                  the horizontal position of the top-left corner of
	 *                           this element
	 * @param y                  the vertical position of the top-most corner of
	 *                           this element
	 * @param width              the width of the element
	 * @param height             the height of the element
	 * @param startProgressValue the starting progress value, must be between 0 and
	 *                           100
	 * @param constraintType     the type of constraints to apply
	 * @param constraintSpecs    the values to apply when constraining the element
	 * @since 1.2.0
	 */
	public UIHudProgressBarElement(int x, int y, int width, int height, int startProgressValue, int constraintType,
			int[] constraintSpecs) {
		super(x, y, width, height);
		this.progress = MathUtils.clamp(startProgressValue, 0, 100);
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
	}

	@Override
	protected void render(Graphics2D g) {
		Color startingColor = g.getColor();

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);

		// since this classes general contract states that progress is an integer
		// between 0 and 100 (inclusive), we just need to map the value of progress
		g.fillRect(x + 2, y + 2, (int) MathUtils.map(getProgress(), 0f, 100, 0f, width - 4), height - 4);

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

	/**
	 * Sets this element's progress.
	 * 
	 * @param newProgress the progress that this element should display
	 * @since 1.2.0
	 */
	public void setProgress(int newProgress) {
		this.progress = newProgress;
	}

	/**
	 * Returns this element's progress.
	 * 
	 * @return this element's progress
	 * @since 1.2.0
	 */
	public int getProgress() {
		return this.progress;
	}
}
