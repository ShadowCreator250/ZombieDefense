import greenfoot.Greenfoot;

/**
 * A special tower with low range, medium damage and medium reload time. It attacks zombies with bombs and is more expensive than the archer tower.
 */
public class BombTower extends Tower {

	public static final int PRICE = 30;
	private static final int DEFAULT_DAMAGE = 50;
	private static final int DEFAULT_RANGE = 100;
	private static final int DEFAULT_RELOAD_TIME = 250;
	private static final int ZOMBIE_MOVEMENT_FORWARD_PREDICTION = 2;
	private static final String FLYING_SOUND = "Bomb_Ignite.wav";

	/**
	 * Creates an bomb tower object with its default values.
	 */
	public BombTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	public int getPrice() {
		return PRICE;
	}
	
	/**
	 * Shoots an bomb which attacks all zombies in a small range around its impact.
	 * 
	 * @param destinationX - the x-coordinate of the target
	 * @param destinationY - the y-coordinate of the target
	 * @param damage - the damage an bomb deals
	 */
	protected void shootProjectile(int destinationX, int destinationY, int damage) {
		Bomb bomb = new Bomb(destinationX, destinationY, damage);
		getWorld().addObject(bomb, getX(), getY());
		Greenfoot.playSound(FLYING_SOUND);
	}

	@Override
	public int getZombieMovementForwardPrediction() {
		return ZOMBIE_MOVEMENT_FORWARD_PREDICTION;
	}

	@Override
	public Projectile getProjetile(int destinationX, int destinationY, int damage) {
		return new Bomb(destinationX, destinationY, damage);
	}

}
