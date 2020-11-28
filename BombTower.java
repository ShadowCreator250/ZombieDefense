import java.util.List;

public class BombTower extends Tower {
	private static final int damage = 50;
	private static final int range = 20;
	private static final int reloadTime = 700;
	private int shootTime = reloadTime;
	private List<Zombie> zombies;
	
	public BombTower() {		
		
	}
	
	public void act() {
		
	}
	
	private boolean checkIfZombiesInRange() {
		zombies = getObjectsInRange(range, Zombie.class);
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
					zombie.health = zombie.health - (damage * (1-zombie.resistance));
				}
			}
		}
	}
	
	private boolean reload() {
		if(shootTime < reloadTime) {
			shootTime++;
			return true;			
		}
		else {
			shootTime = 0;
			return false;
		}
	}
	
}
