package jGame.core.entity.component;

import jGame.core.entity.Entity;

/**
 * Base class for any component.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public abstract class Component {

	/**
	 * The entity to which this component is attached to.
	 * 
	 * @since 2.0.0
	 */
	protected Entity entity;
	
	/**
	 * Creates a component attached to this entity.
	 * 
	 * @param entity the entity to attach this component too
	 * @return the component created. Default implementation always returns null
	 * @since 2.0.0
	 */
	public static Component create(Entity entity) {
		return null;
	}
	
	/**
	 * The method to execute in every component call.
	 * 
	 * @since 2.0.0
	 */
	public abstract void execute();
	
	/**
	 * The method to execute once when the entity this component is attached to is initialized
	 * 
	 * @since 2.0.0
	 */
	public abstract void init();

	@Override
	public String toString() {
		return "Component [entity=" + entity.toString() + "]";
	}
}
