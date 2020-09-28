package jGame.core.ui.hud;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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
			showDropdown = !showDropdown;
		}
	};

	public UIHudDropdownElement() {
	}

	public UIHudDropdownElement(int x, int y, List<UIHudButtonElement> elements) {
		super(x, y);
		this.dropdownButtons = elements;
		
		int maxWidth = Integer.MIN_VALUE;
		int maxHeight = Integer.MIN_VALUE;
		for (UIHudButtonElement element : elements) {
			if(element.width > maxWidth)
				maxWidth = element.width;
			
			if(element.height > maxHeight)
				maxHeight = element.height;
		}
		this.width = maxWidth;
		this.height = maxHeight;
	}

	public UIHudDropdownElement(int x, int y, int width, int height, List<UIHudButtonElement> elements) {
		super(x, y, width, height);
		this.dropdownButtons = elements;

		int maxWidth = Integer.MIN_VALUE;
		int maxHeight = Integer.MIN_VALUE;
		for (UIHudButtonElement element : elements) {
			if (element.width > maxWidth)
				maxWidth = element.width;

			if (element.height > maxHeight)
				maxHeight = element.height;
		}
		this.width = maxWidth;
		this.height = maxHeight;
	}

	@Override
	protected void render(Graphics2D g) {

	}

	@Override
	public void registerInputListener() {

	}

	@Override
	public void removeInputListener() {

	}
}
