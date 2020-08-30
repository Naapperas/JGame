package jGame.core.entity;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import jGame.core.serializable.SerializableObject;

/**
 * This class acts as a way of displaying custom entity visuals, supporting animations for said entities.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class Sprite implements SerializableObject {
	
	//fields for animated sprite
	private Animation animation = null;
	
	//fields for static sprite
	private Image spriteImage = null;

	private boolean isAnimatedSprite = false;
	
	// TODO: make overloads for all overloads of ImageIO.read

	/**
	 * Loads all animation frames for using by the sprite when animating.
	 * 
	 * @param inputs an array of {@link InputStream} objects containing image data
	 * @throws Exception if we try to change the type (static/animated) of this
	 *                   sprite
	 * @see ImageIO#read(InputStream)
	 * @since 1.0.0
	 */
	public void loadAnimationSprite(InputStream[] inputs) throws Exception {
		if(spriteImage != null)
			throw new Exception("Sprite already has static image, cant cast into animation!");

		animation = new Animation();

		for (InputStream input : inputs) {
			animation.loadAnimationSprites(ImageIO.read(input));
			((FileInputStream) input).close();
		}
		
		isAnimatedSprite = true;
	}
	
	/**
	 * Loads an image to render on top of the entity this sprite is associated to.
	 * 
	 * @param input an {@link InputStream} object containing image data
	 * @throws Exception if we try to change the type (static/animated) of this
	 *                   sprite
	 * @see ImageIO#read(InputStream)
	 * @since 1.0.0
	 */
	public void loadSpriteImage(InputStream input) throws Exception {
		if(animation != null)
			throw new Exception("Sprite already has animation, cant cast into static image!");
		
		spriteImage = ImageIO.read(input);
		
		isAnimatedSprite = false;
		input.close();
	}
	
	/**
	 * Returns the image to be rendered.
	 * 
	 * @return this sprite's image, if it is static, or the current animation frame,
	 *         if it is animated
	 * @since 1.0.0
	 */
	public Image render() {	
		return this.isAnimatedSprite ? this.animation.animate() : this.spriteImage;
	}
	
	/**
	 * Returns the bounds of the sprite. Mainly used for collision detection by
	 * {@link Entity#tick()}.
	 * 
	 * @return the bounds of this sprite
	 * @since 1.0.0
	 */
	public Rectangle getBounds() {
		return this.isAnimatedSprite ? this.animation.getBounds() : new Rectangle(spriteImage.getWidth(null), spriteImage.getHeight(null));
	}
}
