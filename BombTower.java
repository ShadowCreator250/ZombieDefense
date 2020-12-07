public class BombTower extends Tower {

	public static final int PRICE = 30;
	private static final int DEFAULT_DAMAGE = 75;
	private static final int DEFAULT_RANGE = 100;
	private static final int DEFAULT_RELOAD_TIME = 200;

	public BombTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	protected void shootProjectile(int destinationX, int destinationY, int damage) {
		Bomb bomb = new Bomb(destinationX, destinationY, damage);
		getWorld().addObject(bomb, getX(), getY());
	}

	@Override
	public int getPrice() {
		return PRICE;
	}

}
