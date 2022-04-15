package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class UIHudTooltipElement extends UIHudElement {
	
	private String tooltipText;
	
	public UIHudTooltipElement() {
		super.setZIndex(99999);
		this.drawConstraints = Constraints.defaultFor(this);
		this.width = 200;
		this.height = 75;
	}
	
	@Override
	public void render(Graphics2D g) {
	
		Color startingColor = g.getColor();
		
		Color backgroundColor = new Color(235, 203, 107, 255 / 2);
		
		Color borderColor = backgroundColor.darker();
		
		g.setColor(backgroundColor);
		g.fillRect(x, y, this.width, this.height);
		
		g.setColor(borderColor);
		g.drawRect(x, y, this.width, this.height);
		
		g.setColor(startingColor);
		
	}

	@Override
	public void registerInputListener() { return; }

	@Override
	public void removeInputListener() { return; }

	public void setTooltipText(String text) {
		this.tooltipText = text;
	}
	
	public void setDrawPoint(Point p) {
		this.x = (int) p.getX();
		this.y = (int) p.getY();
	}
	
}
