package jGame.core.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.util.LinkedList;

import jGame.core.entity.component.CollisionComponent;
import jGame.core.entity.component.Component;
import jGame.core.entity.component.MovementComponent;
import jGame.core.entity.component.RenderComponent;
import jGame.core.entity.component.TransformComponent;
import jGame.core.entity.event.CollisionListener;
import jGame.core.entity.render.Sprite;
import jGame.core.launcher.GameLauncher;
import jGame.logging.ProgramLogger;

/**
 * 
 * This is the base class for all entities involved in the gameplay, weather it
 * be players or AI. The normal implementation of an entity and its updates are
 * based on the collision bounds of that entity.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public abstract class Entity {

	private static int ENTIY_NUMBER = 1;
	
	// name of this entity
	protected String name = "Entity_" + (ENTIY_NUMBER++);

	// display properties
	//protected int width, height;
	protected Sprite texture;
	protected Rectangle colisionBounds;
	protected boolean renderSprite;
	public boolean hasSprite(Component caller) { if (caller instanceof RenderComponent) return this.renderSprite; return false;}

	// these fields are responsible for the movement of each entity, should there be
	// any movement
	public KeyAdapter inputListener;

	//these are the default components each entity must have, and a list of components that can be registered after
	protected MovementComponent mc = MovementComponent.create(this);
	protected CollisionComponent cc = CollisionComponent.create(this);
	protected TransformComponent tc = TransformComponent.create(this);
	protected RenderComponent rc = RenderComponent.create(this);
	
	protected LinkedList<Component> components = new LinkedList<Component>();
	
	// list of collision listeners
	protected LinkedList<CollisionListener> collisionListeners = new LinkedList<CollisionListener>();

	public boolean started = false;
	
	protected Entity() { this.initComponents();	}
	
	private void initComponents() {
		this.tc.init();
		this.mc.init();
		this.cc.init();
		this.rc.init();
		
		components.forEach(Component::init);
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
	 * @since 1.0.0
	 */
	public Entity(int startingX, int startingY, int width, int height) {
		this.tc.setX(startingX);
		this.tc.setStartingX(startingX);
		this.tc.setY(startingY);
		this.tc.setStartingY(startingY);
		this.tc.setWidth(width);
		this.tc.setHeight(height);
		colisionBounds = new Rectangle(width, height);
		colisionBounds.setLocation(this.tc.getX(), this.tc.getY());
		this.initComponents();
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
	public Entity(int startingX, int startingY, Sprite texture) {
		this.tc.setX(startingX);
		this.tc.setStartingX(startingX);
		this.tc.setY(startingY);
		this.tc.setStartingY(startingY);
		this.texture = texture;
		this.colisionBounds = texture.getBounds();
		colisionBounds.setLocation(this.tc.getX(), this.tc.getY());
		this.initComponents();
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
	public Entity(int startingX, int startingY, int width, int height, int speed) {
		this(startingX, startingY, width, height);
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
	public Entity(int startingX, int startingY, Sprite texture, int speed) {
		this(startingX, startingY, texture);
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
	public Entity(int startingX, int startingY, int width, int height, String name) {
		this(startingX, startingY, width, height);
		this.name = name;
		colisionBounds = new Rectangle(width, height);
		colisionBounds.setLocation(this.tc.getX(), this.tc.getY());
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
	public Entity(int startingX, int startingY, Sprite texture, String name) {
		this(startingX, startingY, texture);
		this.name = name;
		this.colisionBounds = texture.getBounds();
		colisionBounds.setLocation(this.tc.getX(), this.tc.getY());
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
	public Entity(int startingX, int startingY, int width, int height, int speed, String name) {
		this(startingX, startingY, width, height, name);
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
	public Entity(int startingX, int startingY, Sprite texture, int speed, String name) {
		this(startingX, startingY, texture, name);
	}

	/**
	 * Renders the entity on the screen.
	 * 
	 * @param g the <code>Graphics</code> object responsible for rendering
	 * @see Graphics
	 * @since 1.0.0
	 */
	public void render(Graphics2D g) {
		this.rc.render(g);
	};
	
	/**
	 * Updates the entity and readies it for rendering. Also, all
	 * {@link CollisionListener}s registered are notified if a collision happens.
	 * 
	 * @see CollisionListener
	 * @since 1.0.0
	 */
	public void tick() {
		// check collisions and notify listeners
		if (this.cc != null)
			this.cc.execute();

		// move according to user input and collisions
		if (this.mc != null)
			this.mc.execute();
		
		// execute all other components
		if(this.components != null)
			this.components.forEach(Component::execute);
		
	};

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
	protected void setUpInputListener() {
		this.mc.setInputListener(); // delegate input listener registration to movement component
	};

	/**
	 * Restarts the player inside the game. Implementation is dependent on the
	 * client.
	 * 
	 * @since 1.0.0
	 */
	public abstract void restart();
	
	/**
	 * Returns the component of class {@code componentClass} registered to this entity.
	 * 
	 * @param <T> the type of component to return
	 * @param componentClass the class of the component to be returned
	 * @return the component of the given class
	 * @since 2.0.0
	 */
	public <T extends Component> Component getComponent(Class<T> componentClass) {
		if(componentClass == MovementComponent.class)
			return this.mc;
		else if (componentClass == CollisionComponent.class)
			return this.cc;
		else if (componentClass == TransformComponent.class)
			return this.tc;
		else if (componentClass == RenderComponent.class)
			return this.rc;
		else
			for(Component c : components)
				if(componentClass == c.getClass())
					return c;
		return null;
	}
	
	/**
	 * Registers a new component to this entity object. The general contract of the ECS is that only one component of each type can be registered on a given entity at all times. This is subject to change.
	 * 
	 * @param c the new {@code Component} to be registered
	 * @since 2.0.0
	 */
	public void registerComponent(Component c) {
		Class<? extends Component> clazz = c.getClass();
		
		if (clazz == TransformComponent.class || clazz == MovementComponent.class || clazz == CollisionComponent.class) return;
		
		for (Component component : components) 
			if(clazz == component.getClass())
				return;	
		ProgramLogger.writeLog("Registering component " + c.toString() + "!");
		c.init();
		components.add(c);
	}

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

	/**
	 * Removes this Enytity's key input listener in the GameLauncher's main window.
	 * 
	 * @since 1.0.0
	 */
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
	
	/**
	 * Returns the collision listeners registered for this entity.
	 * 
	 * @return the collision listeners registered for this entity
	 * @since 2.0.0
	 */
	public LinkedList<CollisionListener> getCollisionListeners() {
		return this.collisionListeners;
	}
	
	/**
	 * Initial startup process for this entity.
	 * 
	 * @since 2.0.0
	 */
	public void startup() { return; }
	
	/**
	 * Serializes this entity.
	 * 
	 * @param indent the level of indentation to apply when serializing this entity
	 * @throws IOException if an IOException occurs
	 * @since 2.0.0
	 */
	public void serialize(int indent) throws IOException {
		return;
	}

	@Override
	public String toString() {
		return "Entity [name=" + name + ", x=" + this.tc.getX() + ", y=" +this.tc.getY() + ", width=" + this.tc.getWidth() + ", height=" + this.tc.getHeight() + "]";
	}	
}
