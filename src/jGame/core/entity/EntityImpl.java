package jGame.core.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import jGame.core.entity.event.CollisionEvent;
import jGame.core.launcher.GameLauncher;
import jGame.core.utils.MathUtils;

/**
 * Basic implementation of a square entity that is controlled by the player
 * using the WASD keys.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 *
 */
public final class EntityImpl extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7533119592298769774L;

	protected EntityImpl() {
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param width     the width of this entity
	 * @param height    the height of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, int width, int height) {
		super(startingX, startingY, width, height);
		renderSprite = false;
	}
	
	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>sprite</code> object to
	 * be rendered.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param texture   the sprite to render as the entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, Sprite texture) {
		super(startingX, startingY, texture);
		renderSprite = true;
	}
	
	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions. Also sets the speed of this entity.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param width     the width of this entity
	 * @param height    the height of this entity
	 * @param speed     the given speed of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, int width, int height, int speed) {
		super(startingX, startingY, width, height, speed);
		renderSprite = false;
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>sprite</code> object to
	 * be rendered. Also sets the speed of this entity.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param texture   the sprite to render as the entity
	 * @param speed     the given speed of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, Sprite texture, int speed) {
		super(startingX, startingY, texture, speed);
		renderSprite = true;
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param width     the width of this entity
	 * @param height    the height of this entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, int width, int height, String name) {
		super(startingX, startingY, width, height, name);
		renderSprite = false;
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>sprite</code> object to
	 * be rendered.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param texture   the sprite to render as the entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, Sprite texture, String name) {
		super(startingX, startingY, texture, name);
		renderSprite = true;
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions. Also sets the speed of this entity.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param width     the width of this entity
	 * @param height    the height of this entity
	 * @param speed     the given speed of this entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, int width, int height, int speed, String name) {
		super(startingX, startingY, width, height, speed, name);
		renderSprite = false;
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>sprite</code> object to
	 * be rendered. Also sets the speed of this entity.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param texture   the sprite to render as the entity
	 * @param speed     the given speed of this entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, Sprite texture, int speed, String name) {
		super(startingX, startingY, texture, speed, name);
		renderSprite = true;
	}

	@Override
	public void setUpInputListener() {

		this.inputListener = new KeyAdapter() {

			/*
			 * Set boolean variable instead of directly changing velX and velY so movement
			 * is smoother, as it happens in the tick method.
			 */

			@Override
			public void keyPressed(KeyEvent e) {

				int eventId = e.getKeyCode();

				if(eventId == KeyEvent.VK_W) {
					moveUp = true;
				} else if (eventId == KeyEvent.VK_S) {
					moveDown = true;
				} else if (eventId == KeyEvent.VK_A) {
					moveLeft = true;
				} else if (eventId == KeyEvent.VK_D) { 
					moveRight = true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

				int eventId = e.getKeyCode();

				if (eventId == KeyEvent.VK_W) {
					moveUp = false;
				} else if (eventId == KeyEvent.VK_S) {
					moveDown = false; 
				} else if (eventId == KeyEvent.VK_A) {
					moveLeft = false;
				} else if (eventId == KeyEvent.VK_D) { 
					moveRight = false; 
				}
			}
		};
	}

	@Override
	public synchronized void tick() {

		// wall collision code
		if (this.colisionBounds.getX() <= 0
				|| this.colisionBounds.getX() + this.colisionBounds.getWidth() >= GameLauncher
				.getMainWindow().getWindowCanvas().getBounds().getWidth()) {
			CollisionEvent theCollision = new CollisionEvent(this, null, "left/right wall hit",
					this.getColisionBounds().x,
					this.getColisionBounds().y, CollisionEvent.CollisionType.ENTITY_WALL);

			collisionListeners.forEach((listener) -> { listener.onCollision(theCollision);
			});
		}
		
		if (this.colisionBounds.getY() <= 0
				|| this.colisionBounds.getY() + this.colisionBounds.getHeight() >= GameLauncher.getMainWindow()
						.getWindowCanvas().getBounds().getHeight()) {
			CollisionEvent theCollision = new CollisionEvent(this, null, "top/botton wall hit",
					this.getColisionBounds().x, this.getColisionBounds().y, CollisionEvent.CollisionType.ENTITY_WALL);

			collisionListeners.forEach((listener) -> { listener.onCollision(theCollision); });
		}

		// entity collision code
		for (Entity entity : EntityManager.getEntitiesList()) {

			if (this.getColisionBounds().intersects(entity.getColisionBounds())) {

				CollisionEvent theCollision = new CollisionEvent(this, entity, entity.toString() + " hit",
						this.getColisionBounds().x, this.getColisionBounds().y,
						CollisionEvent.CollisionType.ENTITY_ENTITY);

				collisionListeners.forEach((listener) -> { listener.onCollision(theCollision); });

			}
		}

		// movement direction code
		if (moveUp && !moveDown)
			moveVertical = -1;
		else if (moveDown && !moveUp)
			moveVertical = 1;
		else if (!moveUp && !moveDown)
			moveVertical = 0;

		if (moveRight && !moveLeft)
			moveHorizontal = 1;
		else if (moveLeft && !moveRight)
			moveHorizontal = -1;
		else if (!moveRight && !moveLeft)
			moveHorizontal = 0;
		
		velX = moveHorizontal * speed;
		velY = moveVertical * speed;

		// movement code
		x += velX;
		y += velY;

		// movement constraints code

		// make so the x and y coordinates don't make the entity go out of the window
		x = MathUtils.clamp(x, 0, (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getWidth()
				- this.getColisionBounds().getWidth()));
		y = MathUtils.clamp(y, 0, (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight()
				- this.getColisionBounds().getHeight()));

		// collision bounds relocation code
		colisionBounds.setLocation(x, y);
	}

	@Override
	public synchronized void render(Graphics2D g) {
		
		// change rendering code based on render type, aka, if this entity has a sprite
		// or not
		if (renderSprite) {
			while (!g.drawImage(texture.render(), (int) this.getColisionBounds().getX(),
					(int) this.getColisionBounds().getY(), null));
		} else {
			Color startingColor = g.getColor();

			g.setColor(Color.WHITE);
			g.fillRect((int) this.getColisionBounds().getX(), (int) this.getColisionBounds().getY(),
					(int) this.getColisionBounds().getWidth(), (int) this.getColisionBounds().getHeight());
			g.setColor(startingColor);
		}
	}

	@Override
	public boolean isRenderable() {
		return true;
	}

	@Override
	public void restart() {
		x = startingX;
		y = startingY;
	}
}
