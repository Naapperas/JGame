package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import jGame.core.launcher.GameLauncher;
import jGame.core.utils.MathUtils;

/**
 * Dropdown list of "clickable" buttons to fit inside a reduced space that
 * appears and disappears when the users clicks this element.
 * 
 * @author Nuno Pereira
 * @since 1.2.0
 */
public class UIHudDropdownElement extends UIHudElement {

	// the list of buttons to be rendered
	private List<UIHudButtonElement> dropdownButtons = new ArrayList<UIHudButtonElement>();

	private boolean showDropdown = false;

	// the string to display
	private String optionDisplay;

	private static final int SCROLLBAR_WIDTH = 20;
	private int buttonDrawY = 0, scrollBarY = 0, scrollBarHeight = 0, scrollBarYOffset = 0;

	// the clip area to restrict drawing to the "dropdown area"
	private Rectangle clipArea = null;

	private boolean buttonListenersAdded = false, mouseOnScrollbar = false, scrollWheel = false;

	private MouseAdapter mouseInput = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isConsumed()) {
				return;
			}

			// forgot that MouseEvent has a field for mouse pos, might change later
			if (isMouseOver(true)) { // true = dropdown button

				if (showDropdown) {
					if (dropdownButtons != null && buttonListenersAdded) {
						for (UIHudButtonElement uiHudButtonElement : dropdownButtons) {
							uiHudButtonElement.removeInputListener();
						} // add listeners so they listen for input when dropdown is visible

						buttonListenersAdded = false;
					}
				} else {
					if (dropdownButtons != null && !buttonListenersAdded) {
						for (UIHudButtonElement uiHudButtonElement : dropdownButtons) {
							uiHudButtonElement.registerInputListener();
						} // remove listeners so they don't listen for input when dropdown is not visible

						buttonListenersAdded = true;
					}
				}

				showDropdown = !showDropdown;
				mouseOnScrollbar = true;
				e.consume();
			} else if (isMouseOver(false)) {// false = dropdown area
				if (!showDropdown) {
					return;
				}

				showDropdown = true;

				if (new Rectangle(x + width - SCROLLBAR_WIDTH, scrollBarY, SCROLLBAR_WIDTH, scrollBarHeight)
						.contains(e.getPoint())) {
					mouseOnScrollbar = true;
					
					scrollBarYOffset = e.getY() - scrollBarY; // add offset so scrollbar follows the position in which
																// the mouse was clicked, instead of snapping its y
																// component to the mouse's y value in game coordinate
																// space

				}

				if (dropdownButtons != null && !buttonListenersAdded) {
					for (UIHudButtonElement uiHudButtonElement : dropdownButtons) {
						uiHudButtonElement.registerInputListener();
					} // add listeners so they listen for input when dropdown is visible

					buttonListenersAdded = true;
				}
				e.consume();
			} else {
				showDropdown = false;

				if (dropdownButtons != null && buttonListenersAdded) {
					for (UIHudButtonElement uiHudButtonElement : dropdownButtons) {
						uiHudButtonElement.removeInputListener();
					} // remove listeners so they don't listen for input when dropdown is not visible

					buttonListenersAdded = false;
					mouseOnScrollbar = true;
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isConsumed())
				return;
			mouseOnScrollbar = false;
			e.consume();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (e.isConsumed() || !showDropdown)
				return;

			scrollBarY += /* e.getScrollAmount() * */e.getPreciseWheelRotation();
			scrollWheel = true;
			e.consume();
		}
	};

	{
		// set this element's zIndex super high so dropdown area is rendered on top of
		// the other elements
		this.zIndex = 999;
	}

	public UIHudDropdownElement() {
	}

	/**
	 * Creates a dropdown element in the given position using the given elements.
	 * 
	 * @param x        the horizontal coordinate of this element
	 * @param y        the vertical coordinate of this element
	 * @param elements the list of elements to show
	 * @since 1.2.0
	 */
	public UIHudDropdownElement(int x, int y, List<UIHudButtonElement> elements) {
		super(x, y);
		this.dropdownButtons = elements;

		int maxWidth = Integer.MIN_VALUE;
		int maxHeight = Integer.MIN_VALUE;
		if (elements != null) {
			for (UIHudButtonElement element : elements) {
				element.setZIndex(this.zIndex + 1);
				element.setDrawConstraints(new Constraints(element, Constraints.NONE, null));
				if (element.width > maxWidth)
					maxWidth = element.width;

				if (element.height > maxHeight)
					maxHeight = element.height;
			}

			for (UIHudButtonElement element : elements) { element.bounds.setSize(maxWidth, maxHeight); }
		}

		this.width = maxWidth;
		this.height = maxHeight;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.optionDisplay = "Selecionar opção...";
	}

	/**
	 * Creates a dropdown element in the given position with the given dimensions
	 * using the given elements
	 * 
	 * @param x        the horizontal position of this element
	 * @param y        the vertical position of this element
	 * @param width    the width of this element
	 * @param height   the height of this element
	 * @param elements the elements to display
	 * @since 1.2.0
	 */
	public UIHudDropdownElement(int x, int y, int width, int height, List<UIHudButtonElement> elements) {
		super(x, y, width, height);
		this.dropdownButtons = elements;
		
		if(elements != null)
			for (UIHudButtonElement element : elements) {
				element.setZIndex(this.zIndex + 1);
				element.setDrawConstraints(new Constraints(element, Constraints.NONE, null));
				element.width = width - UIHudDropdownElement.SCROLLBAR_WIDTH;
				element.height = height;
				element.bounds.setSize(element.width, element.height);
			}
		this.width = width;
		this.height = height;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.optionDisplay = "Selecionar opção...";
	}

	/**
	 * Creates a dropdown element in the given position using the given elements and
	 * the given constraints.
	 * 
	 * @param x               the horizontal coordinate of this element
	 * @param y               the vertical coordinate of this element
	 * @param elements        the list of elements to show
	 * @param constraintType  the type of constraint to apply
	 * @param constraintSpecs the values to apply when constraining
	 * @since 1.2.0
	 */
	public UIHudDropdownElement(int x, int y, List<UIHudButtonElement> elements, int constraintType,
			int[] constraintSpecs) {
		super(x, y);
		this.dropdownButtons = elements;

		int maxWidth = Integer.MIN_VALUE;
		int maxHeight = Integer.MIN_VALUE;
		if (elements != null) {
			for (UIHudButtonElement element : elements) {
				element.setZIndex(this.zIndex + 1);
				element.setDrawConstraints(new Constraints(element, Constraints.NONE, null));
				if (element.width > maxWidth)
					maxWidth = element.width;

				if (element.height > maxHeight)
					maxHeight = element.height;
			}

			for (UIHudButtonElement element : elements) { element.bounds.setSize(maxWidth, maxHeight); }
		}
		this.width = maxWidth;
		this.height = maxHeight;
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
		this.optionDisplay = "Selecionar opção...";
	}

	/**
	 * 
	 * Creates a dropdown element in the given position with the given dimensions
	 * using the given elements and the given constraints.
	 * 
	 * @param x               the horizontal position of this element
	 * @param y               the vertical position of this element
	 * @param width           the width of this element
	 * @param height          the height of this element
	 * @param elements        the elements to display
	 * @param constraintType  the type of constraints to apply
	 * @param constraintSpecs the values to apply when constraining
	 * @since 1.2.0
	 */
	public UIHudDropdownElement(int x, int y, int width, int height, List<UIHudButtonElement> elements,
			int constraintType, int[] constraintSpecs) {
		super(x, y, width, height);
		this.dropdownButtons = elements;

		if(elements != null)
			for (UIHudButtonElement element : elements) {
				element.setZIndex(this.zIndex + 1);
				element.setDrawConstraints(new Constraints(element, Constraints.NONE, null));
				element.width = width - UIHudDropdownElement.SCROLLBAR_WIDTH;
				element.height = height;
				element.bounds.setSize(element.width, element.height);
			}
		this.width = width;
		this.height = height;
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
		this.optionDisplay = "Selecionar opção...";
	}

	/**
	 * Creates a dropdown element in the given position using the given elements and
	 * with the option display passed in.
	 * 
	 * @param x             the horizontal position of this element
	 * @param y             the vertical position of this element
	 * @param elements      the elements to display
	 * @param optionDisplay the text to display in the "default" button
	 * @since 1.2.0
	 */
	public UIHudDropdownElement(int x, int y, List<UIHudButtonElement> elements, String optionDisplay) {
		super(x, y);
		this.dropdownButtons = elements;

		int maxWidth = Integer.MIN_VALUE;
		int maxHeight = Integer.MIN_VALUE;
		if (elements != null) {
			for (UIHudButtonElement element : elements) {
				element.setZIndex(this.zIndex + 1);
				element.setDrawConstraints(new Constraints(element, Constraints.NONE, null));

				if (element.width > maxWidth)
					maxWidth = element.width;

				if (element.height > maxHeight)
					maxHeight = element.height;
			}

			for (UIHudButtonElement element : elements) { element.bounds.setSize(maxWidth, maxHeight); }
		}
		this.width = maxWidth;
		this.height = maxHeight;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.optionDisplay = optionDisplay;
	}

	/**
	 * Creates a dropdown element in the given position with the given width,
	 * showing the given elements and with the given initial text.
	 * 
	 * @param x             the horizontal position of this element
	 * @param y             the vertical position of this element
	 * @param width         the width of this element
	 * @param height        the height of this element
	 * @param elements      the elements to display
	 * @param optionDisplay the text to display in the "default" button
	 * @since 1.2.0
	 */
	public UIHudDropdownElement(int x, int y, int width, int height, List<UIHudButtonElement> elements,
			String optionDisplay) {
		super(x, y, width, height);
		this.dropdownButtons = elements;

		if (elements != null)
			for (UIHudButtonElement element : elements) {
				element.setZIndex(this.zIndex + 1);
				element.setDrawConstraints(new Constraints(element, Constraints.NONE, null));
				element.width = width - UIHudDropdownElement.SCROLLBAR_WIDTH;
				element.height = height;
				element.bounds.setSize(element.width, element.height);
			}
		this.width = width;
		this.height = height;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.optionDisplay = optionDisplay;
	}

	/**
	 * Creates a dropdown element in the given position, using the given constraints
	 * and showing the given elements and default text.
	 * 
	 * @param x               the horizontal position of this element
	 * @param y               the vertical position of this element
	 * @param elements        the elements to display
	 * @param constraintType  the types of constraints to apply
	 * @param constraintSpecs the values to apply when constraining
	 * @param optionDisplay   the text of the "default" button
	 * @since 1.2.0
	 */
	public UIHudDropdownElement(int x, int y, List<UIHudButtonElement> elements, int constraintType,
			int[] constraintSpecs, String optionDisplay) {
		super(x, y);
		this.dropdownButtons = elements;

		int maxWidth = Integer.MIN_VALUE;
		int maxHeight = Integer.MIN_VALUE;
		if (elements != null) {
			for (UIHudButtonElement element : elements) {
				element.setZIndex(this.zIndex + 1);
				element.setDrawConstraints(new Constraints(element, Constraints.NONE, null));
				if (element.width > maxWidth)
					maxWidth = element.width;

				if (element.height > maxHeight)
					maxHeight = element.height;
			}

			for (UIHudButtonElement element : elements) { element.bounds.setSize(maxWidth, maxHeight); }
		}
		this.width = maxWidth;
		this.height = maxHeight;
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
		this.optionDisplay = optionDisplay;
	}

	/**
	 * Creates a dropdown element in the given position with the given dimensions,
	 * using the given constraints and showing the given elements and default text.
	 * 
	 * @param x               the horizontal position of this element
	 * @param y               the vertical position of this element
	 * @param width           the width of this element
	 * @param height          the height of this element
	 * @param elements        the elements to display
	 * @param constraintType  the types of constraints to apply
	 * @param constraintSpecs the values to apply when constraining
	 * @param optionDisplay   the text of the "default" button
	 * @since 1.2.0
	 */
	public UIHudDropdownElement(int x, int y, int width, int height, List<UIHudButtonElement> elements,
			int constraintType, int[] constraintSpecs, String optionDisplay) {
		super(x, y, width, height);
		this.dropdownButtons = elements;

		if (elements != null)
			for (UIHudButtonElement element : elements) {
				element.setZIndex(this.zIndex + 1);
				element.setDrawConstraints(new Constraints(element, Constraints.NONE, null));
				element.width = width - UIHudDropdownElement.SCROLLBAR_WIDTH;
				element.height = height;
				element.bounds.setSize(element.width, element.height);
			}
		this.width = width;
		this.height = height;
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
		this.optionDisplay = optionDisplay;
	}

	@Override
	public void render(Graphics2D g) {

		Color startingColor = g.getColor();
		Font startingFont = g.getFont();
		Stroke startingStroke = g.getStroke();
		Shape startingClip = g.getClip();

		Rectangle2D textBounds = g.getFontMetrics(startingFont).getStringBounds(optionDisplay, g);

		int textWidth = (int) textBounds.getWidth();
		int textHeight = (int) textBounds.getHeight();

		float scaleX = (float) (this.width / textBounds.getWidth());

		g.setFont(startingFont.deriveFont(startingFont.getSize() * scaleX * 0.62f));

		textBounds = g.getFontMetrics(g.getFont()).getStringBounds(optionDisplay, g);

		textWidth = (int) textBounds.getWidth();
		textHeight = (int) textBounds.getHeight();

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();
		
		// adjust scrollbar height according to number of elements in the dropdown list
		// clamp value so we don't get tiny scrollbars
		scrollBarHeight = (this.dropdownButtons.size() > 3) ? MathUtils
				.clamp((height * 3) / (this.dropdownButtons.size() - 2), 20, height * 3) : 0; // display scrollbar only
																								// if elements overflow
																								// display area

		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePos, GameLauncher.getMainWindow().getWindowCanvas());

		if (mouseOnScrollbar && !scrollWheel) {
			this.scrollBarY = MathUtils.clamp(mousePos.y - scrollBarYOffset, y + height + 5,
					y + height + 4 + height * 3 + 2 - scrollBarHeight - 1); // follow mouse
			//    | start position | height of |        height of
			//    |of dropdown menu| dropdown  |        scrollbar

		} else if (!mouseOnScrollbar && scrollWheel) {
			this.scrollBarY = MathUtils.clamp(this.scrollBarY, y + height + 5,
					y + height + 4 + height * 3 + 2 - scrollBarHeight - 1); // offset relative to previous value
			this.scrollWheel = false;
		}

		try {
			if (this.dropdownButtons.size() > 3)
				this.buttonDrawY = (int) MathUtils.map(scrollBarY, y + height + 5f,
						y + height + 4 + height * 3 + 2 - scrollBarHeight - 1, y + height + 5f,
						(y + height + 5f) - ((this.dropdownButtons.size() - 3) * this.height));
			// set the y position to draw the buttons, this creates the illusion of
			// scrolling through the elements of the dropdown

			else
				this.buttonDrawY = y + height + 5;
		} catch (Exception e) {}

		if (this.clipArea == null) { this.clipArea = new Rectangle(x - 1, y + height + 4, width + 2, height * 3 + 2); }

		g.setColor(Color.WHITE);

		g.drawRect(x, y, width, height);

		g.drawString(optionDisplay, ((this.x + (this.width / 2)) - (textWidth / 2)),
				((this.y + (this.height / 2)) + (textHeight / 4)));

		if (this.showDropdown) {

			g.setClip(clipArea); // clip to draw only on the dropdown area

			g.clearRect(clipArea.x, clipArea.y, clipArea.width, clipArea.height);
			g.drawRect(x, y + height + 5, width, height * 3);

			if (dropdownButtons != null) {
				for (UIHudButtonElement uiHudButtonElement : dropdownButtons) {

					uiHudButtonElement.x = this.x;
					uiHudButtonElement.y = this.buttonDrawY;

					if (this.dropdownButtons.size() <= 3)
						uiHudButtonElement.width = width; // there is no scrollbar, change width of every element

					uiHudButtonElement.bounds.setLocation(uiHudButtonElement.x, uiHudButtonElement.y);

					if (uiHudButtonElement.bounds.intersects(clipArea)) { // we only want to render elements that are
																			// totally/partially visible

						if (clipArea.contains(mousePos)) // mouse interaction is only to be accounted for if the the
															// element is totally/partially visible
							uiHudButtonElement.checkForMouse(true);
						else
							uiHudButtonElement.checkForMouse(false);

						uiHudButtonElement.render(g);

					}

					this.buttonDrawY += uiHudButtonElement.height;
				}
			}

			if (this.dropdownButtons.size() > 3) {
				g.setClip(startingClip);

				g.fillRoundRect(x + width - SCROLLBAR_WIDTH, scrollBarY, SCROLLBAR_WIDTH, scrollBarHeight, 5, 5);
			}
		}

		g.setColor(startingColor);
		g.setFont(startingFont);
		g.setStroke(startingStroke);
		g.setClip(startingClip);
	}

	@Override
	public void registerInputListener() {
		GameLauncher.getMainWindow().addMouseInputListener(mouseInput, this);
		GameLauncher.getMainWindow().getWindowCanvas().addMouseWheelListener(mouseInput);
	}

	@Override
	public void removeInputListener() {
		GameLauncher.getMainWindow().removeMouseInputListener(mouseInput, this);
		GameLauncher.getMainWindow().getWindowCanvas().removeMouseWheelListener(mouseInput);
	}

	/**
	 * Returns weather the mouse is over this element or not.
	 * 
	 * @param button condition to check if mouse is over the dropdown button/area
	 * @return weather the mouse is over this element or not
	 * @since 1.2.0
	 */
	private boolean isMouseOver(boolean button) {

		Point mousePos = MouseInfo.getPointerInfo().getLocation();

		SwingUtilities.convertPointFromScreen(mousePos, GameLauncher.getMainWindow().getWindowCanvas());

		return button ? new Rectangle(x, y, width, height)
				.contains(mousePos) : new Rectangle(x, y + height + 5, width, height * 3).contains(mousePos);
	}
}
