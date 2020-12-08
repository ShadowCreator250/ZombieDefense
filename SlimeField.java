import greenfoot.Color;

/**
 * An obstacle, which slows down all zombies that walk over it for a certain
 * time. It can be bought and placed by the player.
 */
public class SlimeField extends Obstacle {

	public static final int PRICE = 20;
	private static final double DEFAULT_SLOWDOWN = 0.5; // should be from 0.0-1.0, example: 0.7 stands for 70% less speed
	private int slowdownDuration = 500;
	private static final Color COLOR = new Color(0, 255, 0);
	private static final int POINTS = 1500;

	/**
	 * Creates an object and its image.
	 */
	public SlimeField() {
		createImage(COLOR, POINTS);
	}

	/**
	 * Does what an obstacle does and slows down all zombies in its range, when the
	 * game is not paused.
	 */
	@Override
	public void act() {
		super.act();
		if(!IsCountdownNotZero()) {
			getWorld().removeObject(this);
		}
	}

	private boolean IsCountdownNotZero() {
		slowdownDuration--;
		if(slowdownDuration > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getPrice() {
		return PRICE;
	}

	public static double getDefaultSlowdown() {
		return DEFAULT_SLOWDOWN;
	}
}
