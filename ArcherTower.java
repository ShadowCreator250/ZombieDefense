public class ArcherTower extends Tower {

	public static final int PRICE = 5;
	private static final int DEFAULT_DAMAGE = 10;
	private static final int DEFAULT_RANGE = 160;
	private static final int DEFAULT_RELOAD_TIME = 40;
	private static final int ZOMBIE_MOVEMENT_FORWARD_PREDICTION = 5;

	public ArcherTower() {
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
	public Arrow getProjetile(int destinationX, int destinationY, int damage) {
		return new Arrow(destinationX, destinationY, damage);
	}
}
