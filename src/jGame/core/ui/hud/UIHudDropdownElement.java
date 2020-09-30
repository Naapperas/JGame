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

	public UIHudDropdownElement() {
		// TODO Auto-generated constructor stub
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
	}

	public UIHudDropdownElement(int x, int y, int width, int height, List<UIHudButtonElement> elements) {
		super(x, y, width, height);
		this.dropdownButtons = elements;
		
		if(elements != null)
			for (UIHudButtonElement element : elements) {
				element.width = width;
				element.height = height;
			}
		this.width = width;
		this.height = height;
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
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
	}

	public UIHudDropdownElement(int x, int y, int width, int height, List<UIHudButtonElement> elements,
			int constraintType, int[] constraintSpecs) {
		super(x, y, width, height);
		this.dropdownButtons = elements;

		if(elements != null)
			for (UIHudButtonElement element : elements) {
				element.width = width;
				element.height = height;
			}
		this.width = width;
		this.height = height;
		this.drawConstraints = new Constraints(this, constraintType, constraintSpecs);
	}

	@Override
	protected void render(Graphics2D g) {

		Color startingColor = g.getColor();
		Font startingFont = g.getFont();
		Stroke startingStroke = g.getStroke();
		Shape startingClip = g.getClip();

		this.x = this.drawConstraints.getXLocation();
		this.y = this.drawConstraints.getYLocation();

		g.setColor(Color.WHITE);

		g.drawRect(x, y, width, height);

		if (this.showDropdown) { System.out.println("AHGhhh"); }

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

		return new Rectangle(x, y, width, height).contains(mousePos);
	}
}
