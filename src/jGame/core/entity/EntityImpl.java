package jGame.core.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import jGame.core.entity.component.Component;
import jGame.core.entity.render.Sprite;

/**
 * Basic implementation of a square entity that is controlled by the player
 * using the WASD keys.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 *
 */
public final class EntityImpl extends Entity {

	protected EntityImpl() {
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param width     the width of this entity
	 * @param height    the height of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, int width, int height) {
		super(startingX, startingY, width, height);
		renderSprite = false;
	}
	
	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>sprite</code> object to
	 * be rendered.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param texture   the sprite to render as the entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, Sprite texture) {
		super(startingX, startingY, texture);
		renderSprite = true;
	}
	
	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions. Also sets the speed of this entity.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param width     the width of this entity
	 * @param height    the height of this entity
	 * @param speed     the given speed of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, int width, int height, int speed) {
		super(startingX, startingY, width, height, speed);
		renderSprite = false;
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>sprite</code> object to
	 * be rendered. Also sets the speed of this entity.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param texture   the sprite to render as the entity
	 * @param speed     the given speed of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, Sprite texture, int speed) {
		super(startingX, startingY, texture, speed);
		renderSprite = true;
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param width     the width of this entity
	 * @param height    the height of this entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, int width, int height, String name) {
		super(startingX, startingY, width, height, name);
		renderSprite = false;
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>sprite</code> object to
	 * be rendered.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param texture   the sprite to render as the entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, Sprite texture, String name) {
		super(startingX, startingY, texture, name);
		renderSprite = true;
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>width</code> and
	 * <code>height</code> dimensions. Also sets the speed of this entity.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param width     the width of this entity
	 * @param height    the height of this entity
	 * @param speed     the given speed of this entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, int width, int height, int speed, String name) {
		super(startingX, startingY, width, height, speed, name);
		renderSprite = false;
	}

	/**
	 * Creates an <code>EntityImpl</code> object in the given <code>x</code> and
	 * <code>y</code> coordinates and with the given <code>sprite</code> object to
	 * be rendered. Also sets the speed of this entity.
	 * 
	 * @param startingX the initial horizontal position of this entity
	 * @param startingY the initial vertical position of this entity
	 * @param texture   the sprite to render as the entity
	 * @param speed     the given speed of this entity
	 * @param name      the name of this entity
	 * @since 1.0.0
	 */
	public EntityImpl(int startingX, int startingY, Sprite texture, int speed, String name) {
		super(startingX, startingY, texture, speed, name);
		renderSprite = true;
	}

	@Override
	public synchronized void tick() {

		this.cc.execute();

		this.mc.execute();
		
		if(this.components != null)
			this.components.forEach(Component::execute);
	}

	@Override
	public synchronized void render(Graphics2D g) {
		
		// change rendering code based on render type, aka, if this entity has a sprite
		// or not
		if (renderSprite) {
			while (!g.drawImage(texture.render(), (int) this.getColisionBounds().getX(),
					(int) this.getColisionBounds().getY(), null));
		} else {
			Color startingColor = g.getColor();

			g.setColor(Color.WHITE);
			g.fillRect((int) this.getColisionBounds().getX(), (int) this.getColisionBounds().getY(),
					(int) this.getColisionBounds().getWidth(), (int) this.getColisionBounds().getHeight());
			g.setColor(startingColor);
		}
	}

	@Override
	public boolean isRenderable() {
		return true;
	}

	@Override
	public void restart() {
		this.tc.resetPosition();
	}
}
