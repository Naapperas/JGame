package jGame.core.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.util.LinkedList;

import jGame.core.entity.event.CollisionListener;
import jGame.core.launcher.GameLauncher;
import jGame.core.serializable.SerializableObject;

/**
 * 
 * This is the base class for all entities involved in the gameplay, weather it
 * be players or AI. The normal implementation of an entity and its updates are
 * based on the collision bounds of that entity.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 *
 */
public abstract class Entity implements SerializableObject {

	// name of this entity
	protected String name;

	// display properties
	protected double x, y, startingX, startingY, velX, velY;
	protected int width, height;
	protected Sprite texture;
	protected Rectangle colisionBounds;
	protected boolean renderSprite;

	// these fields are responsible for the movement of each entity, should there be
	// any movement
	protected KeyAdapter inputListener;
	protected boolean moveLeft, moveRight, moveUp, moveDown;
	protected int moveHorizontal, moveVertical;

	// this is the default speed of an entity, in case there is no speed
	// specification
	protected int speed = 5;
	
	// list of collision listeners
	protected LinkedList<CollisionListener> collisionListeners = new LinkedList<CollisionListener>();

	/**
	 * Creates an entity with the given <code>width</code> and <code>height</code>.
	 * Automatically sets this entity's collision bounds, which can be retrieved by
	 * {@link #getColisionBounds}
	 * 
	 * @param startingX the starting x position of the entity
	 * @param startingY the starting y position of the entity
	 * @param width     the width of the entity
	 * @param height    the height of the entity
	 * @since 1.0.0
	 */
	public Entity(double startingX, double startingY, int width, int height) {
		this.x = this.startingX = startingX;
		this.y = this.startingY = startingY;
		this.width = width;
		this.height = height;
		velX = velY = 1;
		colisionBounds = new Rectangle(width, height);
		colisionBounds.setLocation((int) x, (int) y);
	}

	/**
	 * Creates an entity with the given {@link Sprite}. The collision bounds are set
	 * based on the sprite's dimensions.
	 * 
	 * @param startingX the starting x position of the entity
	 * @param startingY the starting y position of the entity
	 * @param texture   the sprite to be rendered on top of the entity
	 * @since 1.0.0
	 */
	public Entity(double startingX, double startingY, Sprite texture) {
		this.x = this.startingX = startingX;
		this.y = this.startingY = startingY;
		this.texture = texture;
		velX = velY = 1;
		this.colisionBounds = texture.getBounds();
		colisionBounds.setLocation((int) x, (int) y);
	}

	/**
	 * Creates an entity with the given <code>width</code> and <code>height</code>.
	 * Automatically sets this entity's collision bounds, which can be retrieved by
	 * {@link #getColisionBounds}. The default speed is overridden.
	 * 
	 * @param startingX the starting x position of the entity
	 * @param startingY the starting y position of the entity
	 * @param width     the width of the entity
	 * @param height    the height of the entity
	 * @param speed     the new speed of the entity
	 * @since 1.0.0
	 */
	public Entity(double startingX, double startingY, int width, int height, int speed) {
		this(startingX, startingY, width, height);
		this.speed = speed;
	}

	/**
	 * Creates an entity with the given {@link Sprite}. The collision bounds are set
	 * based on the sprite's dimensions.
	 * 
	 * @param startingX the starting x position of the entity
	 * @param startingY the starting y position of the entity
	 * @param texture   the sprite to be rendered on top of the entity
	 * @param speed     the new speed of the entity
	 * @since 1.0.0
	 */
	public Entity(double startingX, double startingY, Sprite texture, int speed) {
		this(startingX, startingY, texture);
		this.speed = speed;
	}

	/**
	 * Creates an entity with the given <code>width</code> and <code>height</code>.
	 * Automatically sets this entity's collision bounds, which can be retrieved by
	 * {@link #getColisionBounds}
	 * 
	 * @param startingX the starting x position of the entity
	 * @param startingY the starting y position of the entity
	 * @param width     the width of the entity
	 * @param height    the height of the entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public Entity(double startingX, double startingY, int width, int height, String name) {
		this(startingX, startingY, width, height);
		this.name = name;
		colisionBounds = new Rectangle(width, height);
		colisionBounds.setLocation((int) x, (int) y);
	}

	/**
	 * Creates an entity with the given {@link Sprite}. The collision bounds are set
	 * based on the sprite's dimensions.
	 * 
	 * @param startingX the starting x position of the entity
	 * @param startingY the starting y position of the entity
	 * @param texture   the sprite to be rendered on top of the entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public Entity(double startingX, double startingY, Sprite texture, String name) {
		this(startingX, startingY, texture);
		this.name = name;
		this.colisionBounds = texture.getBounds();
		colisionBounds.setLocation((int) x, (int) y);
	}

	/**
	 * Creates an entity with the given <code>width</code> and <code>height</code>.
	 * Automatically sets this entity's collision bounds, which can be retrieved by
	 * {@link #getColisionBounds}. The default speed is overridden.
	 * 
	 * @param startingX the starting x position of the entity
	 * @param startingY the starting y position of the entity
	 * @param width     the width of the entity
	 * @param height    the height of the entity
	 * @param speed     the new speed of the entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public Entity(double startingX, double startingY, int width, int height, int speed, String name) {
		this(startingX, startingY, width, height, name);
		this.speed = speed;
	}

	/**
	 * Creates an entity with the given {@link Sprite}. The collision bounds are set
	 * based on the sprite's dimensions.
	 * 
	 * @param startingX the starting x position of the entity
	 * @param startingY the starting y position of the entity
	 * @param texture   the sprite to be rendered on top of the entity
	 * @param speed     the new speed of the entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public Entity(double startingX, double startingY, Sprite texture, int speed, String name) {
		this(startingX, startingY, texture, name);
		this.speed = speed;
	}

	/**
	 * Renders the entity on the screen.
	 * 
	 * @param g the <code>Graphics</code> object responsible for rendering
	 * @see Graphics
	 * @since 1.0.0
	 */
	public abstract void render(Graphics g);
	
	/**
	 * Updates the entity and readies it for rendering. Also, all
	 * {@link CollisionListener}s registered are notified if a collision happens.
	 * 
	 * @see CollisionListener
	 * @since 1.0.0
	 */
	public abstract void tick();

	/**
	 * Checks if the entity is currently renderable. This method is only intended to
	 * be used by the {@link EntityManager}.
	 * 
	 * @return weather this <code>Entity</code> is currently renderable or not
	 * @since 1.0.0
	 */
	public abstract boolean isRenderable();

	/**
	 * Returns the name of the entity.
	 * 
	 * @return this entity's name
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets up the key input listener.
	 * 
	 * @since 1.0.0
	 */
	protected abstract void setUpInputListener();

	/**
	 * Restarts the player inside the game. Implementation isdependant on the
	 * client.
	 * 
	 * @since 1.0.0
	 */
	protected abstract void restart();

	/**
	 * Returns the bounds of this entity in the game.
	 * 
	 * @return the bounds of this entity
	 * @since 1.0.0
	 */
	public Rectangle getColisionBounds() {
		return colisionBounds;
	}
	
	/**
	 * Registers this Entity's key input listener in the GameLauncher's main window.
	 * 
	 * @since 1.0.0
	 */
	public void registerInputListener() {
		if (this.inputListener == null)
			setUpInputListener();
		GameLauncher.getMainWindow().addInputListener(inputListener, this);
	}

	public void removeInputListener() {
		GameLauncher.getMainWindow().removeInputListener(inputListener, this);
	}

	/**
	 * Registers a {@link CollisionListener} object that will receive collision
	 * events and process them.
	 * 
	 * @param collListener the collision listener to add
	 * @see CollisionListener
	 * @since 1.0.0
	 */
	public void addColisionListener(CollisionListener collListener) {
		this.collisionListeners.add(collListener);
	}

	@Override
	public String toString() {
		return "Entity [name=" + name + "]";
	}

}
