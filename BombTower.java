public class BombTower extends Tower {
	private static final int DEFAULT_RANGE = 20;
	private static final int DEFAULT_RELOAD_TIME = 700;

	public BombTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME);
	}

	@Override
	protected void shootProjectile(int x, int y) {
		// TODO Auto-generated method stub

	}

}
