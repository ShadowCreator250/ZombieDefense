import java.util.List;

import greenfoot.Color;
import greenfoot.Greenfoot;

public class MineField extends Obstacle {

	public static final int PRICE = 20;
	private static final int DEFAULT_DAMAGE = 150;
	private static final int DEFAULT_RANGE = 50;
	private static final String EXPLOSION_SOUND = "Bomb_Explosion.wav";
	private static final Color COLOR = new Color(0, 0, 0);
	private static final int POINTS = 500;
	
	private List<Zombie> zombies;

	public MineField() {
		createImage(COLOR, POINTS);
	}

	@Override
	public void act() {
		super.act();
		if(!getWorld().isPaused()) {
			attackZombiesInRangeOnce();
		}
	}

	private boolean checkIfZombiesInRange() {
		zombies = getObjectsInRange(DEFAULT_RANGE, Zombie.class);
		if(zombies.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	private void attackZombiesInRangeOnce() {
		if(checkIfZombiesInRange()) {
			Greenfoot.delay(25);
			for (Zombie zombie : zombies) {
				if(!zombie.isAttacked()) {
					zombie.setAttacked(true);
					zombie.absorbDamage(DEFAULT_DAMAGE);
				}
			}
			Greenfoot.playSound(EXPLOSION_SOUND);
			getWorld().removeObject(this);
		}
	}

	@Override
	public int getPrice() {
		return PRICE;
	}
}
