public abstract class Projectile extends SmoothMover {

	public static final double TOLERANCE_RANGE = 3.0;

	private boolean initialized;
	private int destinationX;
	private int destinationY;
	private double speed;
	private int damage;

	public Projectile(int destinationX, int destinationY, double speed, String imageName, int damage) {
		super(new Vector());
		this.destinationX = destinationX;
		this.destinationY = destinationY;
		this.speed = speed;
		this.damage = damage;
		this.initialized = false;

	}

	@Override
	public void act() {
		if(!getWorld().isPaused()) {
			if(!initialized) {
				initialize();
			}
			move();
			checkIfReachedDestination();

		}
	}

	private void checkIfReachedDestination() {
		double dx = calcDistance(getExactX(), destinationX);
		double dy = calcDistance(getExactY(), destinationY);
		double remainingDistance = Math.sqrt(dx * dx + dy * dy);
		if(remainingDistance <= TOLERANCE_RANGE) {
			behaviourIfReachedDestination();
		}

	}

	protected abstract void behaviourIfReachedDestination();

	private void initialize() {
		this.initialized = true;
		turnTowards(destinationX, destinationY);
		this.addForce(new Vector((double) destinationX - getX(), (double) destinationY - getExactY()));
		this.setSpeed(speed);
	}

	public int getDamage() {
		return damage;
	}

}
