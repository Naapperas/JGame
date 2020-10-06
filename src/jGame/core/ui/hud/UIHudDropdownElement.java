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
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import jGame.core.launcher.GameLauncher;
import jGame.core.utils.MathUtils;

public class UIHudDropdownElement extends UIHudElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7509427206795717555L;

	private List<UIHudButtonElement> dropdownButtons = new ArrayList<UIHudButtonElement>();

	private boolean showDropdown = false;

	private String optionDisplay;

	private static final int SCROLLBAR_WIDTH = 20;
	private int buttonDrawY = 0, scrollBarY = 0, scrollBarHeight = 0, scrollBarYOffset = 0;

	private Rectangle clipArea = null;

	private boolean buttonListenersAdded = false, mouseOnScrollbar = false;

	private MouseAdapter mouseInput = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isConsumed()) {
				return;
			}

			if (isMouseOver(true)) {

				if (showDropdown) {
					if (dropdownButtons != null && buttonListenersAdded) {
						for (UIHudButtonElement uiHudButtonElement : dropdownButtons) {
							uiHudButtonElement.removeInputListener();
						}

						buttonListenersAdded = false;
					}
				} else {
					if (dropdownButtons != null && !buttonListenersAdded) {
						for (UIHudButtonElement uiHudButtonElement : dropdownButtons) {
							uiHudButtonElement.registerInputListener();
						}

						buttonListenersAdded = true;
					}
				}

				showDropdown = !showDropdown;
				mouseOnScrollbar = true;
				e.consume();
			} else if (isMouseOver(false)) {
				showDropdown = true;

				if (new Rectangle(x + width - SCROLLBAR_WIDTH, scrollBarY, SCROLLBAR_WIDTH, scrollBarHeight)
						.contains(e.getPoint())) {
					mouseOnScrollbar = true;
					
					scrollBarYOffset = e.getY() - scrollBarY;

				}

				if (dropdownButtons != null && !buttonListenersAdded) {
					for (UIHudButtonElement uiHudButtonElement : dropdownButtons) {
						uiHudButtonElement.registerInputListener();
					}

					buttonListenersAdded = true;
				}
				e.consume();
			} else {
				showDropdown = false;

				if (dropdownButtons != null && buttonListenersAdded) {
					for (UIHudButtonElement uiHudButtonElement : dropdownButtons) {
						uiHudButtonElement.removeInputListener();
					}

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

	};

	{
		this.zIndex = 999;
	}

	public UIHudDropdownElement() {
	}

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
	protected void render(Graphics2D g) {

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
				.clamp((height * 3) / (this.dropdownButtons.size() - 2), 20, height * 3) : 0;

		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePos, GameLauncher.getMainWindow().getWindowCanvas());

		if (mouseOnScrollbar) {
			this.scrollBarY = MathUtils.clamp(mousePos.y - scrollBarYOffset, y + height + 5,
					y + height + 4 + height * 3 + 2 - scrollBarHeight - 1);
			//    | start position | height of |        height of
			//    |of dropdown menu| dropdown  |        scrollbar

		}

		try {
			if (this.dropdownButtons.size() > 3)
				this.buttonDrawY = (int) MathUtils.map(scrollBarY, y + height + 5f,
						y + height + 4 + height * 3 + 2 - scrollBarHeight - 1, y + height + 5f,
						(y + height + 5f) - ((this.dropdownButtons.size() - 3) * this.height));
			else
				this.buttonDrawY = y + height + 5;
		} catch (Exception e) {}

		if (this.clipArea == null) { this.clipArea = new Rectangle(x - 1, y + height + 4, width + 2, height * 3 + 2); }

		g.setColor(Color.WHITE);

		g.drawRect(x, y, width, height);

		g.drawString(optionDisplay, ((this.x + (this.width / 2)) - (textWidth / 2)),
				((this.y + (this.height / 2)) + (textHeight / 4)));

		if (this.showDropdown) {

			g.setClip(clipArea);

			g.clearRect(clipArea.x, clipArea.y, clipArea.width, clipArea.height);
			g.drawRect(x, y + height + 5, width, height * 3);

			if (dropdownButtons != null) {
				for (UIHudButtonElement uiHudButtonElement : dropdownButtons) {

					uiHudButtonElement.x = this.x;
					uiHudButtonElement.y = this.buttonDrawY;

					if (this.dropdownButtons.size() <= 3)
						uiHudButtonElement.width = width;

					uiHudButtonElement.bounds.setLocation(uiHudButtonElement.x, uiHudButtonElement.y);

					if (uiHudButtonElement.bounds.intersects(clipArea)) {

						if (clipArea.contains(mousePos))
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
	}

	@Override
	public void removeInputListener() {
		GameLauncher.getMainWindow().removeMouseInputListener(mouseInput, this);
	}

	private boolean isMouseOver(boolean button) {

		Point mousePos = MouseInfo.getPointerInfo().getLocation();

		SwingUtilities.convertPointFromScreen(mousePos, GameLauncher.getMainWindow().getWindowCanvas());

		return button ? new Rectangle(x, y, width, height)
				.contains(mousePos) : new Rectangle(x, y + height + 5, width, height * 3).contains(mousePos);
	}
}
