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
	
	@Override
	public void execute() { return; }

	@Override
	public void init() {

	}
}
