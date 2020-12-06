import java.util.List;

import greenfoot.Color;

public class MineField extends Obstacle {

	public static final int PRICE = 20;
	private static final int DAMAGE = 100;
	private static final int RANGE = 50;
	private List<Zombie> zombies;
	private boolean attacked = false;
	private static final Color COLOR = new Color(0, 0, 0);
	private static final int POINTS = 500;

	public MineField() {
		createImage(COLOR, POINTS);
	}
	
	@Override
	public void act() {

	}

	private boolean checkIfZombiesInRange() {
		zombies = getObjectsInRange(RANGE, Zombie.class);
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
					zombie.absorbDamage(DAMAGE);
				}

			}
		}
	}
}
