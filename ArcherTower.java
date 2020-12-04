import java.util.List;

public class ArcherTower extends Tower {
	private static final int DAMAGE = 1;
	private static final int RANGE = 50;
	private static final int RELOADTIME = 100;
	private int shootTime = RELOADTIME;
	private List<Zombie> zombies;
	
	public ArcherTower() {		
		
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
