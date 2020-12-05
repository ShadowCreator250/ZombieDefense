import java.util.List;

public class SlimeField extends Obstacle {

	private static final double SLOWDOWN = 0.1; // should be from 0.0-1.0, example: 0.7 stands for 70% less speed
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
					zombie.slowDown(SLOWDOWN);
				}
			}
		}
	}
}
