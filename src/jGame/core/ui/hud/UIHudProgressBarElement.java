package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Graphics2D;

import jGame.core.utils.MathUtils;

public class UIHudProgressBarElement extends UIHudElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3065269234183461086L;

	/**
	 * The current progress tracked by this progress bar. Must be between 0 and 100.
	 */
	private int progress = 0;

	public UIHudProgressBarElement() {
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param startProgressValue
	 */
	public UIHudProgressBarElement(int x, int y, int width, int height, int startProgressValue) {
		super(x, y, width, height);
		this.progress = startProgressValue;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param constraintType
	 * @param constraintSpecs
	 */
	public UIHudProgressBarElement(int x, int y, int width, int height, int constraintType, int[] constraintSpecs) {
		super(x, y, width, height);
		this.progress = 0;
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param startProgressValue
	 * @param constraintType
	 * @param constraintSpecs
	 */
	public UIHudProgressBarElement(int x, int y, int width, int height, int startProgressValue, int constraintType,
			int[] constraintSpecs) {
		super(x, y, width, height);
		this.progress = startProgressValue;
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
	}

	@Override
	protected void render(Graphics2D g) {
		Color startingColor = g.getColor();

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);

		int progressWidth = (int) MathUtils.map(this.progress, 0, 100, 0f, width - 4);
		g.fillRect(x + 2, y + 2, progressWidth, height - 4);

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

	public void setProgress(int newProgress) {
		this.progress = newProgress;
	}

	public int getProgress() {
		return this.progress;
	}
}
