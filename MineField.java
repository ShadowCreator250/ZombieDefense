import java.util.List;

import greenfoot.Color;

public class MineField extends Obstacle {

	public static final int PRICE = 20;
	private static final int DEFAULT_DAMAGE = 100;
	private static final int DEFAULT_RANGE = 50;
	private List<Zombie> zombies;
	private boolean attacked = false;
	private static final Color COLOR = new Color(0, 0, 0);
	private static final int POINTS = 500;
	private int damage;

	public MineField() {
		super(DEFAULT_RANGE);
		this.damage = DEFAULT_DAMAGE;
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
					zombie.absorbDamage(DEFAULT_DAMAGE);
				}

			}
		}
	}

	@Override
	public int getPrice() {
		return PRICE;
	}
}
