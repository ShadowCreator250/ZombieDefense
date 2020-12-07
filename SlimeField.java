import java.util.List;

import greenfoot.Color;
import greenfoot.Greenfoot;

public class SlimeField extends Obstacle {

	public static final int PRICE = 20;
	private static final double DEFAULT_SLOWDOWN = 0.5; // should be from 0.0-1.0, example: 0.7 stands for 70% less speed
	private static final int SLOWDOWN_DURATION_TICKS = 500;
	private static final Color COLOR = new Color(0, 255, 0);
	private static final int POINTS = 1500;
	private List<Zombie> zombies;

	public SlimeField() {
		createImage(COLOR, POINTS);
	}

	@Override
	public void act() {
		super.act();
		if(!getWorld().isPaused()) {
			slowDownZombiesInRangeOnce();
		}
	}

	private void slowDownZombiesInRangeOnce() {
		if(getIntersectingObjects(Zombie.class).size() > 0) {
			zombies = getIntersectingObjects(Zombie.class);
			for (Zombie zombie : zombies) {
				if(!zombie.isSlowedDown()) {
					zombie.slowDown(DEFAULT_SLOWDOWN);
				}
			}
			removeSlowdown();
			getWorld().removeObject(this);
		}
	}

	private void removeSlowdown() {
		Greenfoot.delay(SLOWDOWN_DURATION_TICKS);
		for (Zombie zombie : zombies) {
			zombie.slowDown(0);
		}

	}

	@Override
	public int getPrice() {
		return PRICE;
	}
}
