package jGame.core.entity.component;

import jGame.core.entity.Entity;
import jGame.core.launcher.GameLauncher;
import jGame.core.utils.MathUtils;

/**
 * The default implementation of a movement component.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class MovementComponent extends Component {

	private MovementComponent() {
		// make uninstantiable
	}	
	
	/**
	 * Creates a movement component attached to this entity.
	 * 
	 * @param entity the entity to attach this component too
	 * @return the component created.
	 * @since 2.0.0
	 */
	public static MovementComponent create(Entity entity) {
		MovementComponent mc = new MovementComponent();
		mc.entity = entity;
		return mc;
	}
	
	@Override
	public void execute() {
		// movement direction code
		if (this.entity.moveUp && !this.entity.moveDown)
			this.entity.moveVertical = -1;
		else if (this.entity.moveDown && !this.entity.moveUp)
			this.entity.moveVertical = 1;
		else if (!this.entity.moveUp && !this.entity.moveDown)
			this.entity.moveVertical = 0;

		if (this.entity.moveRight && !this.entity.moveLeft)
			this.entity.moveHorizontal = 1;
		else if (this.entity.moveLeft && !this.entity.moveRight)
			this.entity.moveHorizontal = -1;
		else if (!this.entity.moveRight && !this.entity.moveLeft)
			this.entity.moveHorizontal = 0;
		
		this.entity.velX = this.entity.moveHorizontal * this.entity.speed;
		this.entity.velY = this.entity.moveVertical * this.entity.speed;

		// movement code
		this.entity.x += this.entity.velX;
		this.entity.y += this.entity.velY;

		// movement constraints code

		// make so the x and y coordinates don't make the entity go out of the window
		this.entity.x = MathUtils.clamp(this.entity.x, 0, (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getWidth()
				- this.entity.getColisionBounds().getWidth()));
		this.entity.y = MathUtils.clamp(this.entity.y, 0, (int) (GameLauncher.getMainWindow().getWindowCanvas().getBounds().getHeight()
				- this.entity.getColisionBounds().getHeight()));

		// collision bounds relocation code
		this.entity.getColisionBounds().setLocation(this.entity.x, this.entity.y);
	}

}
