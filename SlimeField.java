import java.util.List;

import greenfoot.Color;

public class SlimeField extends Obstacle {

	public static final int PRICE = 20;
	private static final double DEFAULT_SLOWDOWN = 0.5; // should be from 0.0-1.0, example: 0.7 stands for 70% less speed
	private static final int DEFAULT_RANGE = 50;
	private List<Zombie> zombies;
	private boolean attacked = false;
	private static final Color COLOR = new Color(0, 255, 0);
	private static final int POINTS = 1500;
	private double slowdown;

	public SlimeField() {
		super(DEFAULT_RANGE);
		this.slowdown = DEFAULT_SLOWDOWN;
		createImage(COLOR, POINTS);
	}

	private boolean checkIfZombiesInRange() {
		zombies = getObjectsInRange(DEFAULT_RANGE, Zombie.class);
		if(zombies != null) {
			return true;
		} else {
			return false;
		}
	}

	public void attackZombiesInRangeOnce() {
		if(checkIfZombiesInRange()) {
			for (Zombie zombie : zombies) {
				if(attacked == false) {
					attacked = true;
					zombie.slowDown(DEFAULT_SLOWDOWN);
				}
			}
		}
	}

	@Override
	public int getPrice() {
		return PRICE;
	}
}
