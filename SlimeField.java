import java.util.List;

import greenfoot.Color;
import greenfoot.Greenfoot;

/** 
 * An obstacle, which slows down all zombies that walk over it for a certain time. It can be bought and placed by the player.
 */
public class SlimeField extends Obstacle {

	public static final int PRICE = 20;
	private static final double DEFAULT_SLOWDOWN = 0.5; // should be from 0.0-1.0, example: 0.7 stands for 70% less speed
	private int slowdownDuration = 20;
	private static final Color COLOR = new Color(0, 255, 0);
	private static final int POINTS = 1500;
	private List<Zombie> zombies;

	/**
	 * Creates an slime field object and its image.
	 */
	public SlimeField() {
		createImage(COLOR, POINTS);
	}
	
	/**
	 * Does what an obstacle does and slows down all zombies in its range, when the game is not paused.
	 */
	@Override
	public void act() {
		super.act();
		if(!getWorld().isPaused()) {
			slowDownZombiesInRangeOnce();
		}
	}

	/**
	 * Slows down all zombies which walk over the SlimeField. It is removed after that.
	 */
	private void slowDownZombiesInRangeOnce() {
		if(getIntersectingObjects(Zombie.class).size() > 0) {
			zombies = getIntersectingObjects(Zombie.class);
			for (Zombie zombie : zombies) {
				if(!zombie.isSlowedDown()) {
					zombie.slowDown(DEFAULT_SLOWDOWN);
				}
			}
			removeSlowdown();
		}
	}
	
	/**
	 * Removes the slowdown after the countdown set by @link{SLOWDOWN_DURATION_TICKS} of all zombies, that are slowed down.
	 */
	private void removeSlowdown() {
		if(IsCountdownNotZero()) {	
			for (Zombie zombie : zombies) {
				zombie.slowDown(0);
			}
		}
		else {
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
}
