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

public class UIHudInputBoxElement extends UIHudElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2899739622778351704L;

	private StringBuilder input = new StringBuilder();

	private int cursorPosition = 0, textPosition = 0, textStartPosition = 0, cursorBlinkTimer = 0,
			inputCursorPosition = 0;
	
	private boolean hasFocus = false;

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
			
			if (keyCode == KeyEvent.VK_BACK_SPACE) {
				if(input.length() > 0) {
					input.deleteCharAt(inputCursorPosition-- - 1);
				}
			} else if (keyCode == KeyEvent.VK_RIGHT) {
				
			
			} else if (keyCode == KeyEvent.VK_LEFT) {
				
			}else {

				if (keyCode == KeyEvent.VK_SHIFT || keyCode == KeyEvent.VK_CONTROL
						|| keyCode == KeyEvent.VK_CAPS_LOCK) {
					return;
				}

				input.append(e.getKeyChar());
				inputCursorPosition++;
			}

			System.out.println(getInput());
			System.out.println(keyCode);

			e.consume();
		}
	};

	private MouseAdapter mouseListener = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			if (isMouseOver())
				hasFocus = true;
			else {
				hasFocus = false;
				cursorBlinkTimer = 0;
			}
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
		Shape startingClip = g.getClip();

		g.setColor(Color.WHITE);
		
		g.setStroke(new BasicStroke(3));
		
		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		g.setClip(new Rectangle(x - 1, y - 1, width + 2, height + 2));

		this.cursorPosition = this.textStartPosition = x + 5;

		g.drawRect(x, y, width, height);

		g.setStroke(startingStroke);
		
		FontMetrics fontMetrics = g.getFontMetrics();
		
		float scale = height / fontMetrics.getAscent();

		g.setFont(g.getFont().deriveFont(g.getFont().getSize2D() * scale * 0.9f));

		fontMetrics = g.getFontMetrics(g.getFont());

		char[] inputChars = getInput().toCharArray();

		this.textPosition = textStartPosition;

		for (char c : inputChars) {

			String character = new String(new char[] { c });
			
			int characterWidth = (int) fontMetrics.getStringBounds(character, g).getWidth();

			if ((textPosition + 1) > (x + width)) {
				textStartPosition -= characterWidth;
			}

			textPosition += characterWidth + 1;

			cursorPosition += characterWidth + 1;
		}

		g.drawString(getInput(), textStartPosition, y + height - 12);

		if (hasFocus) {
			if (cursorBlinkTimer <= GameLauncher.getFPS() / 2)
				g.fillRect(MathUtils.clamp(cursorPosition, x + 5, x + width - 10), y + 6, 3, height - 12);

			cursorBlinkTimer++;
		}

		g.setColor(startingColor);
		g.setFont(startingFont);
		g.setStroke(startingStroke);
		g.setClip(startingClip);

		if(cursorBlinkTimer >= GameLauncher.getFPS())
			cursorBlinkTimer = 0;
		
	}

	@Override
	public void registerInputListener() {
		GameLauncher.getMainWindow().addInputListener(keyInputListener, this);
		GameLauncher.getMainWindow().addMouseInputListener(mouseListener, this);
	}

	@Override
	public void removeInputListener() {
		GameLauncher.getMainWindow().removeInputListener(keyInputListener, this);
		GameLauncher.getMainWindow().removeMouseInputListener(mouseListener, this);
	}

	public String getInput() {
		return input.toString();
	}

	private boolean isMouseOver() {

		Point mousePos = MouseInfo.getPointerInfo().getLocation();

		SwingUtilities.convertPointFromScreen(mousePos, GameLauncher.getMainWindow().getWindowCanvas());

		return new Rectangle(x, y, width, height).contains(mousePos);
	}
}
