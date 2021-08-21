package jGame.core.entity.component;

import jGame.core.entity.Entity;

/**
 * Special component class that performs custom behavior according to the user.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class ScriptComponent extends Component {
	
	private Runnable initScript, recurringScript;
	
	private MovementComponent mc;
	private CollisionComponent cc;
	private TransformComponent tc;
	private RenderComponent rc;
	
	/**
	 * @return the mc
	 */
	public MovementComponent getMc() {
		return mc;
	}

	/**
	 * @return the cc
	 */
	public CollisionComponent getCc() {
		return cc;
	}

	/**
	 * @return the tc
	 */
	public TransformComponent getTc() {
		return tc;
	}

	/**
	 * @return the rc
	 */
	public RenderComponent getRc() {
		return rc;
	}

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
	
	/**
	 * Sets the init script to run when the component is initiated.
	 * 
	 * @param initScript the script to run when the component is initiated.
	 * @since 2.0.0
	 */
	public void setInitScript(Runnable initScript) {
		this.initScript = initScript;
	}
	
	/**
	 * Sets the script to run everytime this component's {@code execute} method is ran.
	 * 
	 * @param recurringScript the script to run everytime this component's {@code execute} method is ran.
	 * @since 2.0.0
	 */
	public void setRecurringScript(Runnable recurringScript) {
		this.recurringScript = recurringScript;
	}
	
	/**
	 * Creates a script component attached to this entity.
	 * 
	 * @param entity the entity to attach this component too
	 * @param initScript the script to run when the component is initiated
	 * @param recurringScript the script to run everytime this component's {@code execute} method is ran
	 * @return the component created.
	 * @since 2.0.0
	 */
	public static ScriptComponent create(Entity entity, Runnable initScript, Runnable recurringScript) {	
		ScriptComponent sc = new ScriptComponent();
		sc.entity = entity;
		sc.initScript = initScript;
		sc.recurringScript = recurringScript;
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
