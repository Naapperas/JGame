package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

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

	// element coords and displayable text
	private int width = 0, height = 0;
	private String buttonText;

	// the actual listener responsible for handling user input
	private MouseAdapter mouseListener = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			UIHudButtonElement.this.processClick(e);
		}
	};

	/**
	 * Initializes this button element in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions. Also sets the string to be displayed in the
	 * center of the button.
	 * 
	 * @param x             the horizontal position of this element
	 * @param y             the vertical position of this element
	 * @param width         the with of this element
	 * @param height        the height of this element
	 * @param textToDisplay the text to be displayed by this element
	 * @since 1.0.0
	 */
	public UIHudButtonElement(int x, int y, int width, int height, String textToDisplay) {
		super(x, y);
		this.width = width;
		this.height = height;
		this.buttonText = textToDisplay;
	}

	@Override
	protected void render(Graphics g) {

		// draw the frame of the button
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);

		// this code is used to center the text on the button in order to give it a
		// nicer look
		Rectangle2D textBounds = g.getFontMetrics().getStringBounds(buttonText, g);

		int textWidth = (int) textBounds.getWidth();
		int textHeight = (int) textBounds.getHeight();

		g.drawString(buttonText, ((this.x + (this.width / 2)) - (textWidth / 2)),
				((this.y + (this.height / 2)) + (textHeight / 4)));

		g.setColor(Color.BLACK);

		Rectangle bounds = new Rectangle(width, height);
		bounds.setLocation(x, y);

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
}
