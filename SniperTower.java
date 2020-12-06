public class SniperTower extends Tower {

	public static final int PRICE = 30;
	private static final int DEFAULT_DAMAGE = 100;
	private static final int DEFAULT_RANGE = 230;
	private static final int DEFAULT_RELOAD_TIME = 200;

	public SniperTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	protected void shootProjectile(int x, int y) {
		// TODO Auto-generated method stub

	}

}
