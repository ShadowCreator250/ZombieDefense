public class SniperTower extends Tower {

	public static final int PRICE = 50;
	private static final int DEFAULT_DAMAGE = 100;
	private static final int DEFAULT_RANGE = 270;
	private static final int DEFAULT_RELOAD_TIME = 350;
	private static final int ZOMBIE_MOVEMENT_FORWARD_PREDICTION = 3;

	public SniperTower() {
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
	public Bullet getProjetile(int destinationX, int destinationY, int damage) {
		return new Bullet(destinationX, destinationY, damage);
	}

}
