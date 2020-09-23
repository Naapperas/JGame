package jGame.core.ui.hud;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jGame.core.launcher.GameLauncher;

public class UIHudInputBoxElement extends UIHudElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2899739622778351704L;

	private String input = "";

	private KeyAdapter keyInputListenern = new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
		}

	};

	private MouseAdapter mouseListener = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
		}
		
	};

	public UIHudInputBoxElement() {
	}

	public UIHudInputBoxElement(int x, int y) {
		super(x, y);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	public UIHudInputBoxElement(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	public UIHudInputBoxElement(int x, int y, int constraintType, int[] constraintSpecs) {
		super(x, y);
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
	}

	public UIHudInputBoxElement(int x, int y, int width, int height, int constraintType, int[] constraintSpecs) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
	}

	@Override
	protected void render(Graphics2D g) {

		Color startingColor = g.getColor();
		Font startingFont = g.getFont();
		Stroke startingStroke = g.getStroke();

		g.setColor(Color.WHITE);
		
		g.setStroke(new BasicStroke(3));
		
		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		g.drawRect(x, y, width, height);

		g.setColor(startingColor);
		g.setFont(startingFont);
		g.setStroke(startingStroke);

	}

	@Override
	public void registerInputListener() {
		GameLauncher.getMainWindow().addInputListener(keyInputListenern, this);
	}

	@Override
	public void removeInputListener() {
		GameLauncher.getMainWindow().removeInputListener(keyInputListenern, this);
	}

	public String getInput() {
		return input;
	}
}
