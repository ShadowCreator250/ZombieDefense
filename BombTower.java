import greenfoot.Greenfoot;

public class BombTower extends Tower {

	public static final int PRICE = 30;
	private static final int DEFAULT_DAMAGE = 50;
	private static final int DEFAULT_RANGE = 100;
	private static final int DEFAULT_RELOAD_TIME = 250;
	private static final int ZOMBIE_MOVEMENT_FORWARD_PREDICTION = 2;
	private static final String FLYING_SOUND = "Bomb_Ignite.wav";

	public BombTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	public int getPrice() {
		return PRICE;
	}
	
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
