package jGame.core.entity;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class responsible for performing animations on entity sprites: rolling
 * through a series of images, thus creating the illusion of animation.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class Animation {

	//The actual list containing the animation images
	private List<Image> spriteImages = Collections.synchronizedList(new LinkedList<Image>());
	
	private int animationIndex = 0;
	private int animationDirection = 1;

	// a static list of animation tasks that holds all tasks for all animations
	private static List<Runnable> animationTasks = new LinkedList<Runnable>();

	// the thread responsible for animating sprites
	private static Thread animatorThread = new Thread() {

		{
			this.setName("Animator Thread");
		}

		@Override
		public void run() {

			while (true) {
				animationTasks.forEach((task) -> { task.run(); });
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	// the task responsible for changing this animation
	private final Runnable animationTask = () -> {
		
		this.animationIndex += this.animationDirection;
		
		if (this.animationIndex == 0 || this.animationIndex == this.spriteImages.size() - 1)
			this.animationDirection *= -1;
		
	};

	// if there are any animations being used, start the animator thread immediately
	static {
		animatorThread.start();
	}
	
	// queues the animation task for this animation on construction
	{
		queueAnimationTask(animationTask);
	}

	/**
	 * Queues the given animation task for future execution.
	 * 
	 * @param task the task needed to animate this animation's sprite
	 * @since 1.0.0
	 */
	private synchronized void queueAnimationTask(Runnable task) {
		animationTasks.add(task);
	}

	/**
	 * Returns the {@link Image} object representing the current animation frame.
	 * 
	 * @return an {@link Image} object representing the current animation frame~
	 * @since 1.0.0
	 */
	public Image animate() {
		
		Image i = spriteImages.get(animationIndex);
		
		return i;
	}
	
	/**
	 * Loads an image composing the overall animation.
	 * 
	 * @param sprite the new animation image to add
	 * @since 1.0.0
	 */
	public void loadAnimationSprites(Image sprite) {
		spriteImages.add(sprite);
	}
	
	/**
	 * Returns the bounds of the images loaded in this animation.
	 * 
	 * @return the bounds of the images in this animation.~
	 * @since 1.0.0
	 */
	public Rectangle getBounds() {
		
		Image sprite = spriteImages.get(0);
		
		return new Rectangle(sprite.getWidth(null), sprite.getHeight(null));
	}
}
