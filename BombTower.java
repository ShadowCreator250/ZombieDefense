public class BombTower extends Tower {

	public static final int PRICE = 30;
	private static final int DEFAULT_DAMAGE = 50;
	private static final int DEFAULT_RANGE = 100;
	private static final int DEFAULT_RELOAD_TIME = 250;
	private static final int ZOMBIE_MOVEMENT_FORWARD_PREDICTION = 2;

	public BombTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	public int getPrice() {
		return PRICE;
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
