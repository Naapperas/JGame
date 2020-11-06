package jGame.core.ui.hud;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.LinkedList;

import jGame.core.launcher.GameLauncher;

/**
 * A scroll view element to represent an extensive collection of
 * {@link UIHudElement}s in a reduced amount of space.
 * 
 * @author Nuno Pereira
 * @since 1.2.0
 */
class UIHudScrollViewElement extends UIHudElement {

	private LinkedList<UIHudElement> elements = null;

	private MouseAdapter listener = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseReleased(e);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			super.mouseWheelMoved(e);
		}
	};

	/**
	 * Creates an scrollview element in the given position and with the given
	 * dimensions. There are no constraints applied to this element.
	 * 
	 * @param x      the horizontal position of this element
	 * @param y      the vertical position of this element
	 * @param width  the width of this element
	 * @param height the height of this element
	 * @since 1.2.0
	 */
	public UIHudScrollViewElement(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
	}

	/**
	 * Creates an scrollview element in the given position and with the given
	 * dimensions, using the applied constraints.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param width            the width of this element
	 * @param height           the height of this element
	 * @param constraintType   the type of constraints to apply
	 * @param constraintValues the values to apply when constraining the element
	 * @since 1.2.0
	 */
	public UIHudScrollViewElement(int x, int y, int width, int height, int constraintType, int[] constraintValues) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
	}

	/**
	 * Creates an scrollview element in the given position and with the given
	 * dimensions. There are no constraints applied to this element. The elements to
	 * display are passed in as an argument.
	 * 
	 * @param x        the horizontal position of this element
	 * @param y        the vertical position of this element
	 * @param width    the width of this element
	 * @param height   the height of this element
	 * @param elements the list of elements to display
	 * @since 1.2.0
	 */
	public UIHudScrollViewElement(int x, int y, int width, int height, LinkedList<UIHudElement> elements) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.elements = elements;
		for (UIHudElement uiHudElement : this.elements) {
			uiHudElement.parentElement = this;
			if (uiHudElement.width > this.width)
				uiHudElement.width = this.width;
		}
	}

	/**
	 * Creates an scrollview element in the given position and with the given
	 * dimensions, using the applied constraints. The elements to display are passed
	 * in as an argument.
	 * 
	 * @param x                the horizontal position of this element
	 * @param y                the vertical position of this element
	 * @param width            the width of this element
	 * @param height           the height of this element
	 * @param constraintType   the type of constraints to apply
	 * @param constraintValues the values to apply when constraining the element
	 * @param elements         the list of elements to display
	 * @since 1.2.0
	 */
	public UIHudScrollViewElement(int x, int y, int width, int height, int constraintType, int[] constraintValues,
			LinkedList<UIHudElement> elements) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
		this.elements = elements;
		for (UIHudElement uiHudElement : this.elements) {
			uiHudElement.parentElement = this;
			if (uiHudElement.width > this.width)
				uiHudElement.width = this.width;
		}
	}

	@Override
	public void render(Graphics2D g) {
		
		Color startingColor = g.getColor();
		Stroke startingStroke = g.getStroke();
		Font startingFont = g.getFont();

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(2));

		g.drawRect(x, y, width, height);

		int elementsHeight = 0;
		int verticalElementDraw = this.y;

		if (this.elements != null) {

			for (UIHudElement uiHudElement : elements) {
				elementsHeight += uiHudElement.height; 

				uiHudElement.x = this.x;
				uiHudElement.y = verticalElementDraw;
				uiHudElement.render(g);
				verticalElementDraw += uiHudElement.height + 5;

				if (elementsHeight > this.height) {

				}
			}
		}

		g.setColor(startingColor);
		g.setStroke(startingStroke);
		g.setFont(startingFont);

	}

	@Override
	public void registerInputListener() {
		GameLauncher.getMainWindow().addMouseInputListener(listener, this);
	}

	@Override
	public void removeInputListener() {
		GameLauncher.getMainWindow().removeMouseInputListener(listener, this);
	}
}
