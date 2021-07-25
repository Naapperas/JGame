package jGame.core.entity.component;

import jGame.core.entity.Entity;

/**
 * Data component holding various data about an entity's dimensions and positions, etc...
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 *
 */
public class TransformComponent extends Component {

	private int x, y, startingX, startingY;
	
	/**
	 * Creates a movement component attached to this entity.
	 * 
	 * @param entity the entity to attach this component too
	 * @return the component created.
	 * @since 2.0.0
	 */
	public static TransformComponent create(Entity entity) {
		TransformComponent tc = new TransformComponent();
		tc.entity = entity;
		return tc;
	}
	
	/**
	 * Returns this component's entity's x position.
	 * 
	 * @return the x position of this component's entity on the game board
	 * @since 2.0.0
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the new x position of this component's entity.
	 * 
	 * @param x the new x position of this component's entity
	 * @since 2.0.0
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns this component's entity's y position.
	 * 
	 * @return the y position of this component's entity on the game board
	 * @since 2.0.0
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the new y position of this component's entity.
	 * 
	 * @param x the new y position of this component's entity
	 * @since 2.0.0
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns this component's entity's startingX position.
	 * 
	 * @return the startingX position of this component's entity on the game board
	 * @since 2.0.0
	 */
	public int getStartingX() {
		return startingX;
	}

	/**
	 * Sets the new startingX position of this component's entity.
	 * 
	 * @param x the new startingX position of this component's entity
	 * @since 2.0.0
	 */
	public void setStartingX(int startingX) {
		this.startingX = startingX;
	}

	/**
	 * Returns this component's entity's startingY position.
	 * 
	 * @return the startingY position of this component's entity on the game board
	 * @since 2.0.0
	 */
	public int getStartingY() {
		return startingY;
	}

	/**
	 * Sets the new startingY position of this component's entity.
	 * 
	 * @param x the new startingY position of this component's entity
	 * @since 2.0.0
	 */
	public void setStartingY(int startingY) {
		this.startingY = startingY;
	}
	
	/**
	 * 
	 */
	public void resetX() {
		this.x = this.startingX;
	}
	
	/**
	 * 
	 */
	public void resetY() {
		this.y = this.startingY;
	}
	
	/**
	 * 
	 */
	public void resetPosition() {
		this.resetX();
		this.resetY();
	}

	@Override
	public void execute() { return; }

	@Override
	public void init() { 
		/*
		 * This method used to initialize all the variables using the entities own variables, 
		 * but since that contradicts the very purpose of this component, 
		 * variable initialization is going to be done with setters
		 */
		return;
	}

	@Override
	public String toString() {
		return "TransformComponent [x=" + x + ", y=" + y + "]";
	}
}
