public class ArcherTower extends Tower {

	public static final int PRICE = 5;
	private static final int DEFAULT_DAMAGE = 5;
	private static final int DEFAULT_RANGE = 160;
	private static final int DEFAULT_RELOAD_TIME = 40;

	public ArcherTower() {
		super(DEFAULT_RANGE, DEFAULT_RELOAD_TIME, DEFAULT_DAMAGE);
	}

	@Override
	protected void shootProjectile(int destinationX, int destinationY, int damage) {
		Arrow arrow = new Arrow(destinationX, destinationY, damage);
		getWorld().addObject(arrow, getX(), getY());
	}

	@Override
	public int getPrice() {
		return PRICE;
	}
}
