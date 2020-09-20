package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;

import jGame.core.launcher.GameLauncher;

/**
 * This class represents a "clickable" element rendered on the screen, that
 * could respond to user input.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 *
 */
public class UIHudButtonElement extends UIHudElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2004026804332689505L;

	// element coords and displayable text
	protected Rectangle bounds;
	private String buttonText;

	// the actual listener responsible for handling user input
	private MouseAdapter mouseListener = new MouseAdapter() {

		UIHudButtonElement theElement = UIHudButtonElement.this;

		@Override
		public void mouseClicked(MouseEvent e) {

			if (bounds.contains(e.getPoint()))
				theElement.processClick(e);
		}
	};

	protected UIHudButtonElement() {
	}

	/**
	 * Initializes this button element in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions. Also sets the string to be displayed in the
	 * center of the button.
	 * 
	 * @param x             the horizontal position of this element
	 * @param y             the vertical position of this element
	 * @param width         the width of this element
	 * @param height        the height of this element
	 * @param textToDisplay the text to be displayed by this element
	 * @since 1.0.0
	 */
	public UIHudButtonElement(int x, int y, int width, int height, String textToDisplay) {
		super(x, y, width, height);
		this.buttonText = textToDisplay;
		bounds = new Rectangle(width, height);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Initializes this button element in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions. Also sets the string to be displayed in the
	 * center of the button.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param width            the width of this element
	 * @param height           the height of this element
	 * @param textToDisplay    the text to be displayed by this element
	 * @param constraintSpecs  the constraints to apply
	 * @param constraintValues the values of the applied constraints
	 * @since 1.0.0
	 */
	public UIHudButtonElement(int x, int y, int width, int height, String textToDisplay, int constraintSpecs,
			int[] constraintValues) {
		super(x, y, width, height);
		this.buttonText = textToDisplay;
		bounds = new Rectangle(width, height);
		this.drawConstraints = new Constraints(this, constraintSpecs, constraintValues);
	}

	@Override
	protected void render(Graphics g) {

		Color startingColor = g.getColor();
		Font startingFont = g.getFont();

		// TODO: MAKE FONT DEPENDANT ON SIZE
		// g.setFont(startingFont.deriveFont((float) startingFont.getSize() * (width *
		// height) / 2500));

		Rectangle2D textBounds = g.getFontMetrics(startingFont).getStringBounds(buttonText, g);

		int textWidth = (int) textBounds.getWidth();
		int textHeight = (int) textBounds.getHeight();

		float scaleX = (float) (this.width / textBounds.getWidth());

		g.setFont(startingFont.deriveFont(startingFont.getSize() * scaleX * 0.62f));

		textBounds = g.getFontMetrics(g.getFont()).getStringBounds(buttonText, g);

		textWidth = (int) textBounds.getWidth();
		textHeight = (int) textBounds.getHeight();

		g.setColor(Color.WHITE);

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		if (isMouseOver()) { // draw alternate look for hovered buttons
			// fill the frame of the button
			g.fillRect(this.x, this.y, width, height);
			
			// set text color
			g.setColor(Color.BLACK);

			// set text font
			g.setFont(g.getFont().deriveFont(Font.BOLD));

			textBounds = g.getFontMetrics(g.getFont()).getStringBounds(buttonText, g);

			textWidth = (int) textBounds.getWidth();
			textHeight = (int) textBounds.getHeight();
		} else {
			// draw the frame of the button
			g.drawRect(this.x, this.y, width, height);
		}

		// this code is used to center the text on the button in order to give it a
		// nicer look

		g.drawString(buttonText, ((this.x + (this.width / 2)) - (textWidth / 2)),
				((this.y + (this.height / 2)) + (textHeight / 4)));

		g.setColor(startingColor);
		g.setFont(startingFont);

		bounds.setLocation(this.x, this.y);
	}

	@Override
	public void registerInputListener() {
		GameLauncher.getMainWindow().addMouseInputListener(this.mouseListener, this);
	}

	/**
	 * Processes a mouse click. The default implementation does nothing, so this
	 * method should be overridden.
	 * 
	 * @param e the mouse event to be processed
	 * @since 1.0.0
	 */
	protected void processClick(MouseEvent e) {

		// default implementation does nothing

		/*
		 * CODE ADDED FOR TEST PURPOSES
		 * Rectangle bounds = new Rectangle(width, height);
		 * bounds.setLocation(x, y);
		 * 
		 * if (bounds.contains(e.getPoint())) { System.out.println("click"); EntityImpl
		 * square = new EntityImpl(100, 100, 50, 50); GameLauncher.queueTask(() -> {
		 * square.setUpInputListener(); square.registerInputListener();
		 * 
		 * }); EntityManager.addEntity(square); }
		 */
	}

	@Override
	public void removeInputListener() {
		GameLauncher.getMainWindow().removeMouseInputListener(this.mouseListener, this);
	}

	private boolean isMouseOver() {

		Point mousePos = MouseInfo.getPointerInfo().getLocation();


		SwingUtilities.convertPointFromScreen(mousePos, GameLauncher.getMainWindow().getWindowCanvas());

		return bounds.contains(mousePos);
	}

	@Override
	public String toString() {
		return "UIHudButtonElement [buttonText=" + buttonText + ", width=" + width + ", height=" + height + "]";
	}

}
