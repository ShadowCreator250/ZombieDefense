import greenfoot.Greenfoot;

public class SniperTower extends Tower {

	public static final int PRICE = 50;
	private static final int DEFAULT_DAMAGE = 100;
	private static final int DEFAULT_RANGE = 270;
	private static final int DEFAULT_RELOAD_TIME = 350;
	private static final int ZOMBIE_MOVEMENT_FORWARD_PREDICTION = 3;
	private static final String SHOOT_SOUND = "SniperTower_Shoot.wav";

	public SniperTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	public int getPrice() {
		return PRICE;
	}
	
	protected void shootProjectile(int destinationX, int destinationY, int damage) {
		Bullet bullet = new Bullet(destinationX, destinationY, damage);
		getWorld().addObject(bullet, getX(), getY());
		Greenfoot.playSound(SHOOT_SOUND);
	}

	@Override
	public int getZombieMovementForwardPrediction() {
		return ZOMBIE_MOVEMENT_FORWARD_PREDICTION;
	}

	@Override
	public Bullet getProjetile(int destinationX, int destinationY, int damage) {
		return new Bullet(destinationX, destinationY, damage);
	}

}
