package jGame.core.entity.component;

import jGame.core.entity.Entity;

/**
 * The default implementation of a collision component.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class CollisionComponent extends Component {

	private CollisionComponent() {
		// make uninstantiable
	}
	
	/**
	 * Creates a collision component attached to this entity.
	 * 
	 * @param entity the entity to attach this component too
	 * @return the component created.
	 * @since 2.0.0
	 */
	public static CollisionComponent create(Entity entity) {
		CollisionComponent cc = new CollisionComponent();
		cc.entity = entity;
		return cc;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
