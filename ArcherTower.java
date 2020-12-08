import greenfoot.Greenfoot;

public class ArcherTower extends Tower {

	public static final int PRICE = 5;
	private static final int DEFAULT_DAMAGE = 15;
	private static final int DEFAULT_RANGE = 160;
	private static final int DEFAULT_RELOAD_TIME = 40;
	private static final int ZOMBIE_MOVEMENT_FORWARD_PREDICTION = 5;
	private static final String SHOOT_SOUND = "ArcherTower_Shoot.wav";

	public ArcherTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	public int getPrice() {
		return PRICE;
	}

	protected void shootProjectile(int destinationX, int destinationY, int damage) {
		Arrow arrow = new Arrow(destinationX, destinationY, damage);
		getWorld().addObject(arrow, getX(), getY());
		Greenfoot.playSound(SHOOT_SOUND);
	}

	@Override
	public int getZombieMovementForwardPrediction() {
		return ZOMBIE_MOVEMENT_FORWARD_PREDICTION;
	}

	@Override
	public Arrow getProjetile(int destinationX, int destinationY, int damage) {
		return new Arrow(destinationX, destinationY, damage);
	}
}
