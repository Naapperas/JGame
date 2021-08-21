package jGame.core.ui.hud;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class UIHudTooltipElement extends UIHudElement {
	
	private Point drawPoint;
	
	@Override
	public void render(Graphics2D g) {
	
		Color startingColor = g.getColor();
		
		Color backgroundColor = Color.getHSBColor(45f, 0.76f, 0.67f);
		
		Color borderColor = backgroundColor.darker();
		
		g.setColor(backgroundColor);
		g.fillRect(drawPoint.x, drawPoint.y, 200, 75);
		
		g.setColor(borderColor);
		g.drawRect(drawPoint.x, drawPoint.y, 200, 75);
		
		g.setColor(startingColor);
		
	}

	@Override
	public void registerInputListener() { return; }

	@Override
	public void removeInputListener() { return; }

}
