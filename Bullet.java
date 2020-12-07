import java.util.List;

import greenfoot.GreenfootImage;

public class Bullet extends Projectile {

	public static final String BULLET_IMAGE_NAME = "bullet-projectile.png";
	private static final double DEFAULT_SPEED = 2.5;

	public Bullet(int destinationX, int destinationY, int damage) {
		super(destinationX, destinationY, DEFAULT_SPEED, BULLET_IMAGE_NAME, damage);
		GreenfootImage img = new GreenfootImage(BULLET_IMAGE_NAME);
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
}
