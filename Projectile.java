/**
 * An abstract class that determines the plan for a projectile, but needs to be specialized as a
 * special projectile (one of the subclasses).
 */
public abstract class Projectile extends SmoothMover {

	public static final double TOLERANCE_RANGE = 3.0;

	private boolean initialized;
	private int destinationX;
	private int destinationY;
	private double speed;
	private int damage;

	/**
	 * Creates an projectile object with the given characteristics, used at its subclasses.
	 * 
	 * @param destinationX - the x-coordinate of the target
	 * @param destinationY - the y-coordinate of the target
	 * @param speed        - how fast the projectile ist
	 * @param imageName    - name of the image file to set
	 * @param damage       - the damage the projectile deals
	 */
	public Projectile(int destinationX, int destinationY, double speed, String imageName, int damage) {
		super(new Vector());
		this.destinationX = destinationX;
		this.destinationY = destinationY;
		this.speed = speed;
		this.damage = damage;
		this.initialized = false;

	}

	/**
	 * Sets the direction and the movement of the projectile. Also checks if its destination is reached.
	 */
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
			if(getWorld() != null && atWorldEdge()) {
				getWorld().removeObject(this);
			}
		}
	}

	/**
	 * this is executed while the projectile flies
	 */
	protected abstract void behaviorWhileMoving();

	/**
	 * this is executed when the projectile reaches its destination
	 */
	protected abstract void behaviourIfReachedDestination();

	/**
	 * Sets the direction and the movement of the projectile.
	 */
	private void initialize() {
		this.initialized = true;
		turnTowards(destinationX, destinationY);
		this.addForce(new Vector(destinationX - getX(), destinationY - getExactY(), speed));
	}

	public int getDamage() {
		return damage;
	}

	@Override
	public double getSpeed() {
		return speed;
	}
}
