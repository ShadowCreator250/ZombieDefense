import java.util.List;

public class SniperTower extends Tower {
	private static final int DAMAGE = 10;
	private static final int RANGE = 100;
	private static final int RELOADTIME = 500;
	private int shootTime = RELOADTIME;
	private List<Zombie> zombies;

	public SniperTower() {

	}

	public void act() {
		
	}

	private boolean checkIfZombiesInRange() {
		zombies = getObjectsInRange(RANGE, Zombie.class);
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
					z.absorbDamage(DAMAGE);
				}
			}
		}
	}

	private boolean reload() {
		if (shootTime < RELOADTIME) {
			shootTime++;
			return true;
		} else {
			shootTime = 0;
			return false;
		}
	}

}
