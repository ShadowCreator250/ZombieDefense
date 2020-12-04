public class ArcherTower extends Tower {
	private static final int DEFAULT_RANGE = 100;
	private static final int DEFAULT_RELOAD_TIME = 100;

	public ArcherTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME);
	}

	@Override
	protected void shootProjectile(int x, int y) {
		// TODO Auto-generated method stub

	}
}
