import java.util.List;

import greenfoot.GreenfootImage;

public class Arrow extends Projectile {

	public static final String ARROW_IMAGE_NAME = "arrow-projectile.png";
	private static final double DEFAULT_SPEED = 2.0;

	public Arrow(int destinationX, int destinationY, int damage) {
		super(destinationX, destinationY, DEFAULT_SPEED, ARROW_IMAGE_NAME, damage);
		GreenfootImage img = new GreenfootImage(ARROW_IMAGE_NAME);
		img.scale((int) (img.getWidth() / 1.5), (int) (img.getHeight() / 1.5));
		setImage(img);
	}

	@Override
	protected void behaviourIfReachedDestination() {
		List<Zombie> zombies = getObjectsInRange((int) Math.floor(Projectile.TOLERANCE_RANGE), Zombie.class);
		if(zombies.size() > 0) {
			zombies.get(0).absorbDamage(getDamage());
		}
		getWorld().removeObject(this);
	}

	@Override
	protected void behaviorWhileMoving() {
		Zombie zombie = (Zombie) getOneIntersectingObject(Zombie.class);
		if(zombie != null) {
			zombie.absorbDamage(getDamage());
			getWorld().removeObject(this);
		}
	}

}
