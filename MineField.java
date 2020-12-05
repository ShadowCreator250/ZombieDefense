import java.util.List;

public class MineField extends Obstacle {

	private static final int DAMAGE = 50;
	private static final int RANGE = 50;
	private List<Zombie> zombies;
	private boolean attacked = false;

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
