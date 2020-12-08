import java.util.List;

import greenfoot.GreenfootImage;

/**
 * A special projectile that is shot by an archer tower. It can only attack one zombie at once.
 */
public class Arrow extends Projectile {

	public static final String ARROW_IMAGE_NAME = "arrow-projectile.png";
	private static final double DEFAULT_SPEED = 2.0;
	
	/**
	 * Creates an arrow object based on a projectile. Scales and sets its image.
	 * @param destinationX - the x-coordinate of the target
	 * @param destinationY - the x-coordinate of the target
	 * @param damage - the damage dealt by the arrow
	 */
	public Arrow(int destinationX, int destinationY, int damage) {
		super(destinationX, destinationY, DEFAULT_SPEED, ARROW_IMAGE_NAME, damage);
		GreenfootImage img = new GreenfootImage(ARROW_IMAGE_NAME);
		img.scale((int) (img.getWidth() / 1.5), (int) (img.getHeight() / 1.5));
		setImage(img);
	}
	
	/**
	 * Attacks the target when reaching it, then gets removed.
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
	 * Attacks a zombie when hitting it before reaching its original target, then gets removed.
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
