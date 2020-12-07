public class SniperTower extends Tower {

	public static final int PRICE = 50;
	private static final int DEFAULT_DAMAGE = 100;
	private static final int DEFAULT_RANGE = 230;
	private static final int DEFAULT_RELOAD_TIME = 350;

	public SniperTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	protected void shootProjectile(int destinationX, int destinationY, int damage) {
		Bullet bullet = new Bullet(destinationX, destinationY, damage);
		getWorld().addObject(bullet, getX(), getY());
	}

	@Override
	public int getPrice() {
		return PRICE;
	}

}
