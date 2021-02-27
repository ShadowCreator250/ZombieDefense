import greenfoot.Greenfoot;

/**
 * A special tower with high range, high damage and llong reload time. It attacks zombies with
 * bullets and is the most expensive tower.
 */
public class SniperTower extends Tower {

	public static final int PRICE = 50;
	private static final int DEFAULT_DAMAGE = 100;
	private static final int DEFAULT_RANGE = 270;
	private static final int DEFAULT_RELOAD_TIME = 350;
	private static final int ZOMBIE_MOVEMENT_FORWARD_PREDICTION = 5;
	private static final String SHOOT_SOUND = "SniperTower_Shoot.wav";

	/**
	 * Creates an sniper tower object with its default values.
	 */
	public SniperTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
		setImage("Tower-wooden.png");
	}

	@Override
	public int getPrice() {
		return PRICE;
	}

	/**
	 * Shoots a bullet to the targeted zombie.
	 * 
	 * @param destinationX - the x-coordinate of the target
	 * @param destinationY - the y-coordinate of the target
	 * @param damage       - the damage an bomb deals
	 */
	protected void shootProjectile(int destinationX, int destinationY, int damage) {
		Bullet bullet = new Bullet(destinationX, destinationY, damage);
		getWorld().addObject(bullet, getX(), getY());
		Greenfoot.playSound(SHOOT_SOUND);
	}

	@Override
	public int getZombieMovementForwardPrediction() {
		return (int) (ZOMBIE_MOVEMENT_FORWARD_PREDICTION + 10 / getProjetile(0, 0, 0).getSpeed());
	}

	@Override
	public Bullet getProjetile(int destinationX, int destinationY, int damage) {
		return new Bullet(destinationX, destinationY, damage);
	}

}
