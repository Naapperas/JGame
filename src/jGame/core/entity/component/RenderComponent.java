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
 * The component responsible for rendering entities to the game canvas.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class RenderComponent extends Component {
	 
	private boolean fill = false;
	
	/**
	 * Sets weather the shape drawn should be filled or not.
	 * 
	 * @param fill weather the shape drawn should be filled or not
	 * @since 2.0.0
	 */
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	
	/**
	 * Enum representing various "drawables" containing information about the appropriate method to render them.
	 * 
	 * @author Nuno Pereira
	 * @since 2.0.0
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
		 * Returns the name of the method used to render this shape.
		 * 
		 * @return the shapeMethodName
		 * @since 2.0.0
		 */
		public String getShapeMethodName() {
			return shapeMethodName;
		}
	}

	private EntityShape shape = EntityShape.RECTANGLE; // default to rectangle, since it is AWT's/Swing's basic shape

	/**
	 * Creates a RenderComponent attached to the given {@code entity}.
	 * 
	 * @param entity the entity to attach this component to 
	 * @return the newly instantiated RenderComponent
	 * @since 2.0.0
	 */
	public static RenderComponent create(Entity entity) {
		RenderComponent rc = new RenderComponent();
		rc.entity = entity;
		return rc;
	}

	/**
	 * Renders the entity containing this RenderComponent.
	 * 
	 * @param g the graphics context bused to render this component's entity
	 * @since 2.0.0
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
				ProgramLogger.writeErrorLog(e, e.getMessage());
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
		
		TransformComponent tc = (TransformComponent) this.entity.getComponent(TransformComponent.class);
		
		if(fill)
			g.fillRect(tc.getX(), tc.getY(), tc.getWidth(), tc.getHeight());
		else
			g.drawRect(tc.getX(), tc.getY(), tc.getWidth(), tc.getHeight());
		
	}

	@SuppressWarnings("unused")
	private void drawOval(Graphics2D g) {

		TransformComponent tc = (TransformComponent) this.entity.getComponent(TransformComponent.class);
		
		if(fill)
			g.fillOval(tc.getX(), tc.getY(), tc.getWidth(), tc.getHeight());
		else
			g.drawOval(tc.getX(), tc.getY(), tc.getWidth(), tc.getHeight());
		
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
