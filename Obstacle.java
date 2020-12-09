import java.util.Random;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 * An abstract class that determines the plan for an obstacle, but needs to be
 * specialized as a special obstacle (one of the subclasses).
 */
public abstract class Obstacle extends Actor {

	private static final int SIZE = 50;
	private static final int HALFSIZE = SIZE / 2;

	/**
	 * @see checkRemoveObstacleClick()
	 */
	@Override
	public void act() {
		checkRemoveObstacleClick();
	}

	/**
	 * Deletes the obstacle when clicking on it while using the delete tool. The
	 * player gets his coins back.
	 */
	private void checkRemoveObstacleClick() {
		if(Greenfoot.mouseClicked(this) && Greenfoot.getMouseInfo().getButton() == 1 && Greenfoot.getMouseInfo() != null) {
			CursorImage.MouseState mouseState = getWorld().getCursorImage().getMouseState();
			if(mouseState == CursorImage.MouseState.DELETE_TOOL) {
				getWorld().getCoinsCounter().add(this.getPrice());
				getWorld().removeObject(this);
			}
		}
	}

	public abstract int getPrice();

	@Override
	public GameWorld getWorld() {
		return (GameWorld) super.getWorld();
	}

	/**
	 * Creates and sets the image for the special obstacle object.
	 * 
	 * @param color  - the color of the image
	 * @param points - how many points are put inside the image
	 */
	public void createImage(Color color, int points) {
		GreenfootImage image = new GreenfootImage(SIZE, SIZE);

		for (int i = 0; i < points; i++) {
			int x = randomCoord();
			int y = randomCoord();

			image.setColorAt(x, y, color);
		}
		setImage(image);
	}

	/**
	 * Provides random generated coordinates that are needed to create the image.
	 * 
	 * @return the coordinate 0 - if the generated coordinate is negative
	 *         <p>
	 *         the coordinate (size of the image minus 2) - as an maximum coordinate
	 *         <p>
	 *         the generated coordinate - if it is between zero and the maximum
	 */
	private int randomCoord() {
		int val = HALFSIZE + (int) (new Random().nextGaussian() * (HALFSIZE / 2));

		if(val < 0) {
			return 0;
		}

		if(val > SIZE - 2) {
			return SIZE - 2;
		} else {
			return val;
		}
	}
}
