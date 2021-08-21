package jGame.core.ui.hud;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import jGame.core.launcher.GameLauncher;
import jGame.core.utils.MathUtils;

/**
 * Class representing an input box, to which the user can write a string of
 * text. Word skipping (CTRL+left/right) is implemented, and use for the shift
 * key is still a work in progress.
 * 
 * @author Nuno Pereira
 * @since 1.2.0
 */
public class UIHudInputBoxElement extends UIHudElement {

	// use a string builder, since it is more efficient than String concatenation
	private StringBuilder input = new StringBuilder();

	// the relevant coordinates for both text and cursor
	private int cursorPosition = 0, textPosition = 0, textStartPosition = 0, cursorBlinkTimer = 0,
			inputCursorPosition = 0;
	
	private boolean hasFocus = false;

	// if true, can't write any more characters
	private boolean overflow = false;

	private KeyAdapter keyInputListener = new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent e) {
			if (!hasFocus || e.isConsumed())
				return; // we only want to process events if we have focus and they are not consumed

			// because we have no concept of "layer stack", events propagate to every
			// listener, we need to call this method to ensure that no action dependent on
			// keys is activated
			GameLauncher.setHudEvent();

			int keyCode = e.getKeyCode();
			
			if (keyCode == KeyEvent.VK_BACK_SPACE) {// character deletion-backspace
				if (input.length() > 0 && inputCursorPosition != 0) {
					input.deleteCharAt(inputCursorPosition-- - 1);
				}
			} else if (keyCode == KeyEvent.VK_DELETE) {// character deletion-delete
				if (input.length() > 0 && inputCursorPosition != input.length()) {
					input.deleteCharAt(inputCursorPosition);
				}
			} else if (keyCode == KeyEvent.VK_RIGHT) {
				if (inputCursorPosition != input.length()) // character skipping
					inputCursorPosition++;

				if (e.isControlDown() && !e.isShiftDown()) {
					// word skipping

					if (!Character.isWhitespace(e.getKeyChar())) {

						for (int i = inputCursorPosition; i < input.length(); i++) {
							if (Character.isWhitespace(input.charAt(i - 1))
									&& !Character.isWhitespace(input.charAt(i)))
								break;

							inputCursorPosition++;
						}
					}
				} else if (!e.isControlDown() && e.isShiftDown()) {
					// character selection -- TO BE IMPLEMENTED
					
				} else if (e.isControlDown() && e.isShiftDown()) {
					// word selection -- TO BE IMPLEMENTED
				}

			} else if (keyCode == KeyEvent.VK_LEFT) {
				if (inputCursorPosition != 0) // character skipping
					inputCursorPosition--;

				if (e.isControlDown() && !e.isShiftDown()) {
					// word skipping

					if (!Character.isWhitespace(e.getKeyChar())) {

						for (int i = inputCursorPosition; i > 0; i--) {
							if (Character.isWhitespace(input.charAt(i)) && !Character.isWhitespace(input.charAt(i - 1)))
								break;

							inputCursorPosition--;
						}
					}
				} else if (!e.isControlDown() && e.isShiftDown()) {
					// character selection -- TO BE IMPLEMENTED

				} else if (e.isControlDown() && e.isShiftDown()) {
					// word selection -- TO BE IMPLEMENTED
				}

			}else {

				if (keyCode == KeyEvent.VK_SHIFT || keyCode == KeyEvent.VK_CONTROL
						|| keyCode == KeyEvent.VK_CAPS_LOCK || keyCode == KeyEvent.VK_UP
						|| keyCode == KeyEvent.VK_DOWN) {// ignore special keys
					// do nothing. We can't return, because that would leave the event unconsumed.
				} else {
					char c = e.getKeyChar();

					if ((Character.isAlphabetic(c) || Character.isWhitespace(c) || Character.isDigit(c)
							|| Character.isDefined(c)) && !overflow) {
						input.insert(inputCursorPosition++, c);
					}
				}
			}

			cursorBlinkTimer = 0;

			e.consume();
		}
	};

	private MouseAdapter mouseListener = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isConsumed()) {
				hasFocus = false;
				return;
			}

			if (isMouseOver()) {
				hasFocus = true;
				e.consume(); // only consume event if any action has been done
			} else {
				hasFocus = false;
			}

			cursorBlinkTimer = 0;
		}
	};

	{
		this.zIndex = 50;
	}

	public UIHudInputBoxElement() {
	}

	/**
	 * Creates an input box in the given position, with the given dimensions.
	 * {@link Constraints#NONE} are the applied constraints, by default.
	 * 
	 * @param x      the horizontal coordinate of the top-left corner of this input
	 * @param y      the vertical coordinate of the top-left corner of this input
	 * @param width  the width of the element
	 * @param height the height of the element
	 * @since 1.2.0
	 */
	public UIHudInputBoxElement(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Creates an input box in the given position, with the given dimensions, using
	 * the given constraints.
	 * 
	 * @param x               the horizontal coordinate of the top-left corner of
	 *                        this input
	 * @param y               the vertical coordinate of the top-left corner of this
	 *                        input
	 * @param width           the width of the element
	 * @param height          the height of the element
	 * @param constraintType  the type of constraint to apply
	 * @param constraintSpecs the values to apply when constraining this element
	 * @since 1.2.0
	 */
	public UIHudInputBoxElement(int x, int y, int width, int height, int constraintType, int[] constraintSpecs) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
	}

	@Override
	public void render(Graphics2D g) {

		Color startingColor = g.getColor();
		Font startingFont = g.getFont();
		Stroke startingStroke = g.getStroke();
		Shape startingClip = g.getClip();

		g.setColor(Color.WHITE);
		
		g.setStroke(new BasicStroke(3));
		
		overflow = false;

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		g.setClip(new Rectangle(x - 1, y - 1, width + 2, height + 2)); // clip visible area to the input box on this
																		// iteration

		this.cursorPosition = this.textStartPosition = x + 5;

		g.drawRect(x, y, width, height);

		g.setStroke(startingStroke);
		
		FontMetrics fontMetrics = g.getFontMetrics();
		
		float scale = height / fontMetrics.getAscent(); // scale items vertically so they align with the input
														// boundaries

		g.setFont(g.getFont().deriveFont(g.getFont().getSize2D() * scale * 0.9f));

		fontMetrics = g.getFontMetrics(g.getFont());

		char[] inputChars = getInput().toCharArray();

		this.textPosition = textStartPosition;

		for (char c : inputChars) { // his loop determines weather there is input overflow or not; might be changed
									// later to allow horizontal scrolling

			String character = new String(new char[] { c });
			
			int characterWidth = (int) fontMetrics.getStringBounds(character, g).getWidth();

			if ((textPosition + characterWidth) > (x + width))
				overflow = true;

			textPosition += characterWidth + 1;

		}

		g.drawString(getInput(), textStartPosition, y + height - 12);

		if (hasFocus) {
			if (cursorBlinkTimer <= GameLauncher.getFPS() / 2) { // this should make it so that the cursor is always
																	// visible for .5 seconds.
				this.cursorPosition += (int) fontMetrics.getStringBounds(
						input.substring(0, MathUtils.clamp(inputCursorPosition, 0, Integer.MAX_VALUE)), g)
						.getWidth(); // clamp number to assure it is non-negative

				g.fillRect(MathUtils.clamp(cursorPosition, x + 5, x + width - 5), y + 6, 3, height - 12);
			}

			cursorBlinkTimer++;
		}

		g.setColor(startingColor);
		g.setFont(startingFont);
		g.setStroke(startingStroke);
		g.setClip(startingClip);

		if(cursorBlinkTimer >= GameLauncher.getFPS())
			cursorBlinkTimer = 0; // reset cursor blink timer every second
		
	}

	@Override
	public void registerInputListener() {
		super.registerInputListener();
		GameLauncher.getMainWindow().addInputListener(keyInputListener, this);
		GameLauncher.getMainWindow().addMouseInputListener(mouseListener, this);
	}

	@Override
	public void removeInputListener() {
		super.removeInputListener();
		GameLauncher.getMainWindow().removeInputListener(keyInputListener, this);
		GameLauncher.getMainWindow().removeMouseInputListener(mouseListener, this);
	}

	/**
	 * Returns the input of this element.
	 * 
	 * @return the input of this element
	 * @since 1.2.0
	 */
	public String getInput() {
		return input.toString();
	}

	/**
	 * Returns weather the mouse is over this component or not.
	 * 
	 * @return weather the mouse is over this component or not
	 * @since 1.2.0
	 */
	private boolean isMouseOver() {

		Point mousePos = MouseInfo.getPointerInfo().getLocation();

		// since mouse position is in screen coordinates,we need te convert them to the
		// application's coordinate system
		SwingUtilities.convertPointFromScreen(mousePos, GameLauncher.getMainWindow().getWindowCanvas());

		return new Rectangle(x, y, width, height).contains(mousePos);
	}
}
