package jGame.core.entity.component;

import jGame.core.entity.Entity;

/**
 * Special component class that performs custom behavior according to the user.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class ScriptComponent extends Component {

	class ScriptRunnable implements Runnable {

		@Override
		public void run() {
			
		}
	}
	
	private Runnable initScript, recurringScript;
	
	private MovementComponent mc;
	private CollisionComponent cc;
	private TransformComponent tc;
	private RenderComponent rc;
	
	private ScriptComponent() {}
	
	/**
	 * Creates a script component attached to this entity.
	 * 
	 * @param entity the entity to attach this component too
	 * @return the component created.
	 * @since 2.0.0
	 */
	public static ScriptComponent create(Entity entity) {
		ScriptComponent sc = new ScriptComponent();
		sc.entity = entity;
		return sc;
	}
	
	@Override
	public void execute() {
		recurringScript.run();
	}

	@Override
	public void init() {
		this.mc = (MovementComponent) this.entity.getComponent(MovementComponent.class);
		this.cc = (CollisionComponent) this.entity.getComponent(CollisionComponent.class);
		this.rc = (RenderComponent) this.entity.getComponent(RenderComponent.class);
		this.tc = (TransformComponent) this.entity.getComponent(TransformComponent.class);
		initScript.run();
	}
}
