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
		if(!initialized) {
			initialize();
		}
		if(!getWorld().isPaused()) {
			move();
			if(hasReachedDestination(destinationX, destinationY, TOLERANCE_RANGE)) {
				behaviourIfReachedDestination();
			}
			if(getWorld() != null) { // prevents IllegalStateException: Actor not in world
				behaviorWhileMoving();
			}
			if(atWorldEdge()) {
				getWorld().removeObject(this);
			}
		}
	}

	protected abstract void behaviorWhileMoving();

	protected abstract void behaviourIfReachedDestination();

	private void initialize() {
		this.initialized = true;
		turnTowards(destinationX, destinationY);
		this.addForce(new Vector(destinationX - getX(), destinationY - getExactY(), speed));
	}

	public int getDamage() {
		return damage;
	}

}
