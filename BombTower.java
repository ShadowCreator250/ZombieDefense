public class BombTower extends Tower {

	public static final int PRICE = 50;
	private static final int DEFAULT_DAMAGE = 75;
	private static final int DEFAULT_RANGE = 100;
	private static final int DEFAULT_RELOAD_TIME = 350;

	public BombTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	protected void shootProjectile(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPrice() {
		return PRICE;
	}

}
