package jGame.core.entity.component;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import jGame.core.entity.Entity;
import jGame.core.launcher.GameLauncher;
import jGame.core.utils.MathUtils;
import jGame.core.utils.properties.PropertiesManager;
import jGame.logging.ProgramLogger;

/**
 * The default implementation of a movement component.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class MovementComponent extends Component {

	/**
	 * An action to be performed when a specified keybinding is pressed. Acts as the "executor" to keys other than the default ones.
	 * 
	 * @author Nuno Pereira
	 * @since 2.0.0
	 */
	public interface MovementAction{
		
		/**
		 * Executes the given action.
		 * 
		 * @author Nuno Pereira
		 * @since 2.0.0
		 */
		public void execute();
	}
	
	private boolean userControlled = Boolean.parseBoolean(PropertiesManager.getPropertyOrDefault("movement.defaultUserControlled", "true")); // set default for true;

	/**
	 * Sets if the entity should be user controlled.
	 * 
	 * @param userControled the userControled to set
	 * @since 2.0.0
	 */
	public void setUserControlled(boolean userControlled) {
		this.userControlled = userControlled;
	}

	// move these fields from the entity itself into here because they are movement related
	private int speed, velX, velY;
	private boolean moveLeft, moveRight, moveUp, moveDown;
	private int moveHorizontal, moveVertical;

	// the movement parts of the movement component
	private int[] movementKeys = {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D}; //default for every entity, to be changed later
	public static final int MOVE_UP = 0, MOVE_DOWN = 1, MOVE_LEFT = 2, MOVE_RIGHT = 3;
	
	private Map<String, Boolean> bindingMap = new HashMap<String, Boolean>();
	private Map<String, MovementAction> actionBindingMap = new HashMap<String, MovementAction>();
	private Map<String, MovementAction> offActionBindingMap = new HashMap<String, MovementAction>();
	
	private MovementComponent() {
		// make uninstantiable
	}
	
	/**
	 * Creates a movement component attached to this entity.
	 * 
	 * @param entity the entity to attach this component too
	 * @return the component created.
	 * @since 2.0.0
	 */
	public static MovementComponent create(Entity entity) {
		MovementComponent mc = new MovementComponent();
		mc.entity = entity;
		return mc;
	}
	
	public void setInputListener() {
		
		this.entity.inputListener = new KeyAdapter() {

			/*
			 * Set boolean variable instead of directly changing velX and velY so movement
			 * is smoother, as it happens in the tick method.
			 */
		
			MovementComponent mc = MovementComponent.this;

			@Override
			public void keyPressed(KeyEvent e) {

				int eventKeyCode = e.getKeyCode();

				if(eventKeyCode == mc.movementKeys[MOVE_UP]) {
					mc.moveUp = true;
				} else if (eventKeyCode == mc.movementKeys[MOVE_DOWN]) {
					mc.moveDown = true;
				} else if (eventKeyCode ==  mc.movementKeys[MOVE_LEFT]) {
					mc.moveLeft = true;
				} else if (eventKeyCode == mc.movementKeys[MOVE_RIGHT]) { 
					mc.moveRight = true;
				} else if (mc.bindingMap.containsKey("" + eventKeyCode)) {
					mc.bindingMap.put("" + eventKeyCode, true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

				int eventKeyCode = e.getKeyCode();

				if (eventKeyCode == mc.movementKeys[MOVE_UP]) {
					mc.moveUp = false;
				} else if (eventKeyCode == mc.movementKeys[MOVE_DOWN]) {
					mc.moveDown = false; 
				} else if (eventKeyCode == mc.movementKeys[MOVE_LEFT]) {
					mc.moveLeft = false;
				} else if (eventKeyCode == mc.movementKeys[MOVE_RIGHT]) { 
					mc.moveRight = false; 
				} else if (mc.bindingMap.containsKey("" + eventKeyCode)) {
					mc.bindingMap.put("" + eventKeyCode, false);
					mc.offActionBindingMap.get("" + eventKeyCode).execute();
				}
			}
		};
	}
	
	@Override
	public void execute() {
		
		if (userControlled) {
			for (String key : this.actionBindingMap.keySet())
				if (this.bindingMap.containsKey(key) && this.bindingMap.get(key))
					this.actionBindingMap.get(key).execute();
			
			// movement direction code
			if (this.moveUp && !this.moveDown)
				this.moveVertical = -1;
			else if (this.moveDown && !this.moveUp)
				this.moveVertical = 1;
			else if (!this.moveUp && !this.moveDown)
				this.moveVertical = 0;
	
			if (this.moveRight && !this.moveLeft)
				this.moveHorizontal = 1;
			else if (this.moveLeft && !this.moveRight)
				this.moveHorizontal = -1;
			else if (!this.moveRight && !this.moveLeft)
				this.moveHorizontal = 0;
		}
		
		this.velX = this.moveHorizontal * this.speed;
		this.velY = this.moveVertical * this.speed;

		// movement code
		this.entity.x += this.velX;
		this.entity.y += this.velY;

		// movement constraints code

		// make so the x and y coordinates don't make the entity go out of the window
		this.entity.x = MathUtils.clamp(this.entity.x, 0, (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getWidth()
				- this.entity.getColisionBounds().getWidth()));
		this.entity.y = MathUtils.clamp(this.entity.y, 0, (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight()
				- this.entity.getColisionBounds().getHeight()));

		// collision bounds relocation code
		((CollisionComponent)this.entity.getComponent(CollisionComponent.class)).moveBounds(this.entity.x, this.entity.y);
	}

	/**
	 * Assigns a new key binding to the specified control.
	 * 
	 * @param newKey the new key binded to the control
	 * @param keyControl the control of the specified key
	 * @throws IllegalArgumentException if the specified keyControl isn't in range
	 * @since 2.0.0
	 * @see KeyEvent
	 */
	public void setMovementKeyControl(int newKey, int keyControl) throws IllegalArgumentException {
		if (keyControl >= this.movementKeys.length) throw new IllegalArgumentException("keyControl must be one of the pre-defined controls");
		
		this.movementKeys[keyControl] = newKey;
	}
	
	/**
	 * Sets a key binding for a specific action. An 'offAction' parameter must be also passed in to be activated when the key stops being pressed.
	 * 
	 * @param keyBinding the keyCode of the key that triggers this action
	 * @param onAction the action to perform when the key is pressed
	 * @param offAction the action to perform when the key is no longer pressed
	 * @throws IllegalArgumentException if we try to bind an action to a movement key
	 * @since 2.0.0
	 */
	public void setKeyBinding(int keyBinding, MovementAction onAction, MovementAction offAction) throws IllegalArgumentException {
		
		for (int i : movementKeys)
			if(i == keyBinding)
				throw new IllegalArgumentException("Cannot bind an action to a movement binding.");
		
		this.bindingMap.put("" + keyBinding, false);
		this.actionBindingMap.put("" + keyBinding, onAction);
		this.offActionBindingMap.put("" + keyBinding, offAction);
	}

	/**
	 * 
	 * @param i
	 */
	public void setHorizontalMovement(int i) throws IllegalArgumentException {
		if(i != 0 && i != 1 && i != -1) throw new IllegalArgumentException("Argument must be -1, 0 or 1, got " + i);
		this.moveHorizontal = i;
	}
	
	/**
	 * 
	 */
	public void bounceHorizontal () { this.moveHorizontal *= -1; }
	
	/**
	 * 
	 * @param i
	 */
	public void setVerticalMovement(int i) {
		if(i != 0 && i != 1 && i != -1) throw new IllegalArgumentException("Argument must be -1, 0 or 1, got " + i);
		this.moveVertical = i;
	}
	
	/**
	 * 
	 */
	public void bounceVertical () { this.moveVertical *= -1; }
	
	@Override
	public void init() {
		try {
			// assume 5 as the "global" default speed
			this.speed = Integer.parseInt(PropertiesManager.getPropertyOrDefault("entity.defaultSpeed", "5"));
		} catch (Exception e) {
			ProgramLogger.writeErrorLog(e);
		}
	}
}
