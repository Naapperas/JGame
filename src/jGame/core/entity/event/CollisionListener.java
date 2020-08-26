package jGame.core.entity.event;

/**
 * Listener used to process collision events made when a collision happens. It
 * is up to the client to register listeners in objects for event processing.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public interface CollisionListener {

	/**
	 * Processes the given {@code CollisionEvent}.
	 * 
	 * @param collision the event representing the collision
	 * @since 1.0.0
	 * @see CollisionEvent
	 */
	public void onCollision(CollisionEvent collision);

}
