import java.util.List;

import greenfoot.Color;
import greenfoot.Greenfoot;

/**
 * An obstacle, that explodes when zombies walk over it and deals a lot of
 * damage to them. It can be bought and placed by the player.
 */
public class MineField extends Obstacle {

	public static final int PRICE = 20;
	private static final int DEFAULT_DAMAGE = 150;
	private static final int DEFAULT_RANGE = 50;
	private static final String EXPLOSION_SOUND = "Bomb_Explosion.wav";
	private static final Color COLOR = new Color(0, 0, 0);
	private static final int POINTS = 500;

	private List<Zombie> zombies;

	/**
	 * Creates an object and its image.
	 */
	public MineField() {
		createImage(COLOR, POINTS);
	}

	/**
	 * Does what an obstacle does and attacks all zombies in its range, when the
	 * game is not paused.
	 */
	@Override
	public void act() {
		super.act();
		if(!getWorld().isPaused()) {
			attackZombiesInRangeOnce();
		}
	}

	/**
	 * Checks for zombies in the range defined by @link{DEFAULT_RANGE}. If there are
	 * zombies it returns true, if there are no zombies it returns false.
	 * 
	 * @return <code>true</code>, when there are zombies in the range
	 *         <p>
	 *         <code>false</code>, when there are no zombies in the range.
	 */
	private boolean checkIfZombiesInRange() {
		zombies = getObjectsInRange(DEFAULT_RANGE, Zombie.class);
		if(zombies.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Explodes and attacks the zombies that are in range after an short delay. Each
	 * zombie gets damage only once, after that the MineField gets removed.
	 */
	private void attackZombiesInRangeOnce() {
		if(checkIfZombiesInRange()) {
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
