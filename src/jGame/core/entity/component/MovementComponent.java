package jGame.core.entity.component;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import jGame.core.entity.Entity;
import jGame.core.launcher.GameLauncher;
import jGame.core.utils.MathUtils;
import jGame.core.utils.properties.PropertiesManager;

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
	
	private boolean userControlled = Boolean.parseBoolean(PropertiesManager.getProperty("movement.defaultUserControlled")); // set default for true;

	/**
	 * Sets if the entity should be user controlled.
	 * 
	 * @param userControled the userControled to set
	 * @since 2.0.0
	 */
	public void setUserControlled(boolean userControlled) {
		this.userControlled = userControlled;
	}

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
					entity.moveUp = true;
				} else if (eventKeyCode == mc.movementKeys[MOVE_DOWN]) {
					entity.moveDown = true;
				} else if (eventKeyCode ==  mc.movementKeys[MOVE_LEFT]) {
					entity.moveLeft = true;
				} else if (eventKeyCode == mc.movementKeys[MOVE_RIGHT]) { 
					entity.moveRight = true;
				} else if (mc.bindingMap.containsKey("" + eventKeyCode)) {
					mc.bindingMap.put("" + eventKeyCode, true);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

				int eventKeyCode = e.getKeyCode();

				if (eventKeyCode == mc.movementKeys[MOVE_UP]) {
					entity.moveUp = false;
				} else if (eventKeyCode == mc.movementKeys[MOVE_DOWN]) {
					entity.moveDown = false; 
				} else if (eventKeyCode == mc.movementKeys[MOVE_LEFT]) {
					entity.moveLeft = false;
				} else if (eventKeyCode == mc.movementKeys[MOVE_RIGHT]) { 
					entity.moveRight = false; 
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
				if(this.bindingMap.containsKey(key) && this.bindingMap.get(key))
					this.actionBindingMap.get(key).execute();
			
			// movement direction code
			if (this.entity.moveUp && !this.entity.moveDown)
				this.entity.moveVertical = -1;
			else if (this.entity.moveDown && !this.entity.moveUp)
				this.entity.moveVertical = 1;
			else if (!this.entity.moveUp && !this.entity.moveDown)
				this.entity.moveVertical = 0;
	
			if (this.entity.moveRight && !this.entity.moveLeft)
				this.entity.moveHorizontal = 1;
			else if (this.entity.moveLeft && !this.entity.moveRight)
				this.entity.moveHorizontal = -1;
			else if (!this.entity.moveRight && !this.entity.moveLeft)
				this.entity.moveHorizontal = 0;
		}
		
		this.entity.velX = this.entity.moveHorizontal * this.entity.speed;
		this.entity.velY = this.entity.moveVertical * this.entity.speed;

		// movement code
		this.entity.x += this.entity.velX;
		this.entity.y += this.entity.velY;

		// movement constraints code

		// make so the x and y coordinates don't make the entity go out of the window
		this.entity.x = MathUtils.clamp(this.entity.x, 0, (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getWidth()
				- this.entity.getColisionBounds().getWidth()));
		this.entity.y = MathUtils.clamp(this.entity.y, 0, (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight()
				- this.entity.getColisionBounds().getHeight()));

		// collision bounds relocation code
		this.entity.getColisionBounds().setLocation(this.entity.x, this.entity.y);
	}

	/**
	 * Assigns a new key binding to the specified control.
	 * 
	 * @param newKey the new key binded to the control
	 * @param keyControl the control of the specified key
	 * @since 2.0.0
	 * @see KeyEvent
	 */
	public void setMovementKeyControl(int newKey, int keyControl) {
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
}
