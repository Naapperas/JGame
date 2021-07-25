package jGame.core.entity.component;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.lang.reflect.InvocationTargetException;

import jGame.core.entity.Entity;
import jGame.logging.ProgramLogger;

/**
 * 
 * @author nunoa
 *
 */
public class RenderComponent extends Component {

	/**
	 * 
	 * @author nunoa
	 *
	 */
	private static enum EntityShape {

		RECTANGLE("Rect"), 
		SQUARE("Rect"), 
		CIRCLE("Oval"), 
		ELIPSE("Oval"), 
		TRIANGLE("Shape"),
		CUSTOM("Custom");

		private String shapeMethodName;

		EntityShape(String shapeType) {
			this.shapeMethodName = shapeType;
		}

		/**
		 * @return the shapeMethodName
		 */
		public String getShapeMethodName() {
			return shapeMethodName;
		}
	}

	private RenderComponent.EntityShape shape = EntityShape.RECTANGLE;

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public static RenderComponent create(Entity entity) {
		RenderComponent rc = new RenderComponent();
		rc.entity = entity;
		return rc;
	}

	/**
	 * 
	 * @param g
	 */
	public void render(Graphics2D g) {

		// store the initial values, since they might get changed inside the draw functions
		Color startingColor = g.getColor();
		Color startingBackgroundColor = g.getBackground();
		Shape startingClip = g.getClip();
		Font startingFont = g.getFont();
		Composite startingComposite = g.getComposite();
		AffineTransform startingTransform = g.getTransform();
		Stroke startingStroke = g.getStroke();
		Paint startingPaint = g.getPaint();
		RenderingHints startingRenderingHints = g.getRenderingHints();
				
		if (this.entity.hasSprite(this)) {
			// render the sprite
			drawSprite(g);
		} else {
			// render the shape assigned to this component
			try {
				this.getClass().getDeclaredMethod("draw" + this.shape.getShapeMethodName(), Graphics2D.class).invoke(this, g);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				ProgramLogger.writeErrorLog(e);
			}
		}
		
		// restore the initial values to reuse the graphics object
		g.setColor(startingColor);
		g.setBackground(startingBackgroundColor);
		g.setClip(startingClip);
		g.setFont(startingFont);
		g.setComposite(startingComposite);
		g.setTransform(startingTransform);
		g.setStroke(startingStroke);
		g.setPaint(startingPaint);
		g.setRenderingHints(startingRenderingHints);
	}

	private void drawSprite(Graphics2D g) {

	}

	@SuppressWarnings("unused")
	private void drawRect(Graphics2D g) {
	}

	@SuppressWarnings("unused")
	private void drawOval(Graphics2D g) {

	}

	@SuppressWarnings("unused")
	private void drawShape(Graphics2D g) {

	}
	
	@SuppressWarnings("unused")
	private void drawCustom(Graphics2D g) {
		
	}

	@Override
	public void execute() {
		return;
	}

	@Override
	public void init() {

	}
}
