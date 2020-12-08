import java.util.List;

import greenfoot.GreenfootImage;

/**
 * A special projectile that is shot by an sniper tower. It can only attack one zombie at once.
 */
public class Bullet extends Projectile {

	public static final String BULLET_IMAGE_NAME = "bullet-projectile.png";
	private static final double DEFAULT_SPEED = 2.5;
	
	/**
	 * Creates an bullet object based on a projectile. Scales and sets its image.
	 * @param destinationX - the x-coordinate of the target
	 * @param destinationY - the x-coordinate of the target
	 * @param damage - the damage dealt by the bullet
	 */
	public Bullet(int destinationX, int destinationY, int damage) {
		super(destinationX, destinationY, DEFAULT_SPEED, BULLET_IMAGE_NAME, damage);
		GreenfootImage img = new GreenfootImage(BULLET_IMAGE_NAME);
		img.scale((int) (img.getWidth() / 1.5), (int) (img.getHeight() / 1.5));
		setImage(img);
	}
	
	/**
	 * @see Arrow.behaviourIfReachedDestination()
	 */
	@Override
	protected void behaviourIfReachedDestination() {
		List<Zombie> zombies = getObjectsInRange((int) Math.floor(Projectile.TOLERANCE_RANGE), Zombie.class);
		if(zombies.size() > 0) {
			zombies.get(0).absorbDamage(getDamage());
		}
		getWorld().removeObject(this);
	}

	/**
	 * @see Arrow.behaviourWhileMoving()
	 */
	@Override
	protected void behaviorWhileMoving() {
		Zombie zombie = (Zombie) getOneIntersectingObject(Zombie.class);
		if(zombie != null) {
			zombie.absorbDamage(getDamage());
			getWorld().removeObject(this);
		}
	}
}
