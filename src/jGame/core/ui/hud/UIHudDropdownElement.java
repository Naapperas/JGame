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

public class UIHudDropdownElement extends UIHudElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7509427206795717555L;

	private List<UIHudButtonElement> dropdownButtons = new ArrayList<UIHudButtonElement>();

	private boolean showDropdown = false;

	private String optionDisplay;

	private static final int SCROLLBAR_WIDTH = 20;

	private MouseAdapter mouseInput = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (isMouseOver()) {
				showDropdown = !showDropdown;
			} else {
				showDropdown = false;
			}
		}
	};

	{
		this.zIndex = 99;
	}

	public UIHudDropdownElement() {
	}

	public UIHudDropdownElement(int x, int y, List<UIHudButtonElement> elements) {
		super(x, y);
		this.dropdownButtons = elements;

		int maxWidth = Integer.MIN_VALUE;
		int maxHeight = Integer.MIN_VALUE;
		if (elements != null)
			for (UIHudButtonElement element : elements) {
				if (element.width > maxWidth)
					maxWidth = element.width;

				if (element.height > maxHeight)
					maxHeight = element.height;
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
				element.width = width - UIHudDropdownElement.SCROLLBAR_WIDTH;
				element.height = height;
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
		if (elements != null)
			for (UIHudButtonElement element : elements) {
				if (element.width > maxWidth)
					maxWidth = element.width;

				if (element.height > maxHeight)
					maxHeight = element.height;
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
				element.width = width - UIHudDropdownElement.SCROLLBAR_WIDTH;
				element.height = height;
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
		if (elements != null)
			for (UIHudButtonElement element : elements) {
				if (element.width > maxWidth)
					maxWidth = element.width;

				if (element.height > maxHeight)
					maxHeight = element.height;
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
				element.width = width - UIHudDropdownElement.SCROLLBAR_WIDTH;
				element.height = height;
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
		if (elements != null)
			for (UIHudButtonElement element : elements) {
				if (element.width > maxWidth)
					maxWidth = element.width;

				if (element.height > maxHeight)
					maxHeight = element.height;
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
				element.width = width - UIHudDropdownElement.SCROLLBAR_WIDTH;
				element.height = height;
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

		g.setColor(Color.WHITE);

		g.drawRect(x, y, width, height);

		g.drawString(optionDisplay, ((this.x + (this.width / 2)) - (textWidth / 2)),
				((this.y + (this.height / 2)) + (textHeight / 4)));

		if (this.showDropdown) {

			g.clearRect(x, y + height + 5, width, height * 3);
			g.drawRect(x, y + height + 5, width, height * 3);

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

	private boolean isMouseOver() {

		Point mousePos = MouseInfo.getPointerInfo().getLocation();

		SwingUtilities.convertPointFromScreen(mousePos, GameLauncher.getMainWindow().getWindowCanvas());

		return new Rectangle(x, y, width, height).contains(mousePos)
				|| (new Rectangle(x, y + height + 5, width, height * 3).contains(mousePos) && showDropdown);
	}
}
