import java.util.List;

public class BombTower extends Tower {
	private static final int DAMAGE = 50;
	private static final int RANGE = 20;
	private static final int RELOADTIME = 700;
	private int shootTime = RELOADTIME;
	private List<Zombie> zombies;
	
	public BombTower() {		
		
	}
	
	public void act() {
		
	}
	
	private boolean checkIfZombiesInRange() {
		zombies = getObjectsInRange(RANGE, Zombie.class);
		if (zombies != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void attackZombiesInRange() {
		if (checkIfZombiesInRange()) {
			for(Zombie zombie: zombies) {
				if(reload() == false) {
					zombie.absorbDamage(DAMAGE);
				}
			}
		}
	}
	
	private boolean reload() {
		if(shootTime < RELOADTIME) {
			shootTime++;
			return true;			
		}
		else {
			shootTime = 0;
			return false;
		}
	}
	
}
