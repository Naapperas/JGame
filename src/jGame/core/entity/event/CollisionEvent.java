package jGame.core.entity.event;

import java.awt.Point;
import java.util.Objects;

import jGame.core.entity.Entity;

/**
 * A collision event created when a colision happens between any 2 entities or
 * the entity and the walls.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class CollisionEvent {

	/**
	 * Represents the type of this collision.
	 * 
	 * @author Nuno Pereira
	 * @since 1.0.0
	 */
	public enum CollisionType {

		ENTITY_ENTITY, ENTITY_WALL;

	}

	// the relevant fields for this collision
	private Entity source, target;
	private String eventText;
	private int collisionX, collisionY;
	private Point collisionPoint;
	private CollisionType COLLISION_TYPE;
	
	/**
	 * Instantiates a new <code>CollisionEvent</code> with the given parameters.
	 * 
	 * @param source        the source of the collision event (must be an entity)
	 * @param target        the target of the collision event (can be {@code null},
	 *                      representing the walls)
	 * @param eventText     the text associated with this collision
	 * @param x             the x location where the collision happened
	 * @param y             the y location where the collision happened
	 * @param collisionType the type of collision that happened
	 * @since 1.0.0
	 */
	public CollisionEvent(Entity source, Entity target, String eventText, int x, int y, CollisionType collisionType) {
		super();
		this.source = Objects.requireNonNull(source);
		this.target = target;
		this.eventText = eventText;
		this.collisionX = x;
		this.collisionY = y;
		this.collisionPoint = new Point(this.collisionX, this.collisionY);
		this.COLLISION_TYPE = collisionType;
	}

	/**
	 * Returns the source of this event.
	 * 
	 * @return the source of the event
	 * @since 1.0.0
	 */
	public Entity getSource() {
		return source;
	}

	/**
	 * Returns the target of this event.
	 * 
	 * @return the target of this event
	 * @since 1.0.0
	 */
	public Entity getTarget() {
		return target;
	}

	/**
	 * Returns the text associated with this event.
	 * 
	 * @return the text associated with this event
	 * @since 1.0.0
	 */
	public String getEventText() {
		return eventText;
	}

	/**
	 * Returns the x position where this event happened.
	 * 
	 * @return the x position where this event happened
	 * @since 1.0.0
	 */
	public int getCollisionX() {
		return collisionX;
	}

	/**
	 * Returns the y position where this event happened.
	 * 
	 * @return the y position where this event happened
	 * @since 1.0.0
	 */
	public int getCollisionY() {
		return collisionY;
	}

	/**
	 * Returns the point where this event happened.
	 * 
	 * @return the point where this event happenedS
	 */
	public Point getCollisionPoint() {
		return collisionPoint;
	}

	/**
	 * Returns the type of collision represented by this event.
	 * 
	 * @return the type of collision represented by this event.
	 */
	public CollisionType getCOLLISION_TYPE() {
		return COLLISION_TYPE;
	}

	@Override
	public String toString() {
		return "CollisionEvent [source=" + source + ", target=" + Objects.requireNonNullElse(target, "window walls")
				+ ", eventText=" + eventText + ", collisionX="
				+ collisionX + ", collisionY=" + collisionY + ", collisionPoint=" + collisionPoint + ", COLLISION_TYPE="
				+ COLLISION_TYPE + "]";
	}
}
