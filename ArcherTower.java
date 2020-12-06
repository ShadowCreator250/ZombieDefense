public class ArcherTower extends Tower {

	public static final int PRICE = 5;
	private static final int DEFAULT_DAMAGE = 5;
	private static final int DEFAULT_RANGE = 160;
	private static final int DEFAULT_RELOAD_TIME = 40;

	public ArcherTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	protected void shootProjectile(int x, int y) {
		// TODO Auto-generated method stub

	}
}
