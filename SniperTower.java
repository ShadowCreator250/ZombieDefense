import java.util.List;

public class SniperTower extends Tower {
	private static final int damage = 10;
	private static final int range = 100;
	private static final int reloadTime = 500;
	private int shootTime = reloadTime;
	private List<Zombie> zombies;

	public SniperTower() {

	}

	public void act() {
		
	}

	private boolean checkIfZombiesInRange() {
		zombies = getObjectsInRange(range, Zombie.class);
		if (zombies != null) {
			return true;
		} else {
			return false;
		}
	}

	public void attackOneZombieInRange() {
		if (checkIfZombiesInRange()) {
			for (int i = 0; i < zombies.size(); i++) {
				Zombie z = zombies.get(i);
				if (reload() == false) {
					z.health = z.health - (damage * (1 - z.resistance));
				}
			}
		}
	}

	private boolean reload() {
		if (shootTime < reloadTime) {
			shootTime++;
			return true;
		} else {
			shootTime = 0;
			return false;
		}
	}

}
