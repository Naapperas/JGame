package jGame.core.entity.component;

import jGame.core.entity.Entity;
import jGame.core.entity.EntityManager;
import jGame.core.entity.event.CollisionEvent;
import jGame.core.launcher.GameLauncher;

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

		// wall collision code
		if (this.entity.getColisionBounds().getX() <= 0
				|| this.entity.getColisionBounds().getX() + this.entity.getColisionBounds().getWidth() >= GameLauncher
				.getMainWindow().getWindowCanvas().getBounds().getWidth()) {
			CollisionEvent theCollision = new CollisionEvent(this.entity, null, "left/right wall hit",
					this.entity.getColisionBounds().x,
					this.entity.getColisionBounds().y, CollisionEvent.CollisionType.ENTITY_WALL);

			this.entity.getCollisionListeners().forEach((listener) -> { listener.onCollision(theCollision);
			});
		}
		
		if (this.entity.getColisionBounds().getY() <= 0
				|| this.entity.getColisionBounds().getY() + this.entity.getColisionBounds().getHeight() >= GameLauncher.getMainWindow()
						.getWindowCanvas().getBounds().getHeight()) {
			CollisionEvent theCollision = new CollisionEvent(this.entity, null, "top/botton wall hit",
					this.entity.getColisionBounds().x, this.entity.getColisionBounds().y, CollisionEvent.CollisionType.ENTITY_WALL);

			this.entity.getCollisionListeners().forEach((listener) -> { listener.onCollision(theCollision); });
		}

		// entity collision code
		for (Entity entity : EntityManager.getEntitiesList()) {

			if(entity == this.entity)
				continue; //can't collide with ourselves
			
			if (this.entity.getColisionBounds().intersects(entity.getColisionBounds())) {

				CollisionEvent theCollision = new CollisionEvent(this.entity, entity, entity.toString() + " hit",
						this.entity.getColisionBounds().x, this.entity.getColisionBounds().y,
						CollisionEvent.CollisionType.ENTITY_ENTITY);

				this.entity.getCollisionListeners().forEach((listener) -> { listener.onCollision(theCollision); });

			}
		}

	}

}
