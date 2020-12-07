import java.util.Random;

import greenfoot.GreenfootImage;

public class Zombie extends SmoothMover {

	public static final String OUTSIDE_IMAGE_NAME = "Zombie_Outside.png";
	public static final String INIT_IMAGE_NAME = "Zombie1.png";

	private double strength;
	private double resistance; // should be from 0.0-1.0, example: 0.7 stands for 70% less damage to get
	public double speed;
	private double health;
	public boolean slowedDown = false;
	public boolean attacked = false;

	public Zombie() {
		this(1.0, 0.0, 1.0, 100);
	}

	/**
	 * @param strength   - how much damage he deals in per cent (1.0 is default)
	 * @param resistance - how much damage he can resist in per cent (0 is default)
	 * @param speed      - how fast he is in percent (1.0 is default)
	 * @param health     - how many health points he has (100 is default)
	 */
	public Zombie(double strength, double resistance, double speed, double health) {
		GreenfootImage img = new GreenfootImage(OUTSIDE_IMAGE_NAME);
		img.scale((int) (img.getWidth() / 3), (int) (img.getHeight() / 3));
		setImage(img);
		this.strength = strength;
		this.resistance = resistance;
		this.speed = speed;
		this.health = health;
	}

	@Override
	public void act() {
		if(!getWorld().isPaused()) {
			dropCurrency();
		}
	}

	private void dropCurrency() {
		if(health <= 0) {
			getWorld().getGameState().getCoinsCounter().add(new Random().nextInt(3) + 3);
			getWorld().removeObject(this);
		}
	}

	public void attackGate() {
		// if (getOneIntersectingObject(Gate.class)) {
		// TODO: create class Gate, decrease the Gate´s health
		// }
	}

	public void absorbDamage(int damage) {
		this.health = health - (damage * (1 - resistance));
	}

	public void slowDown(double slowdown) {
		this.speed = speed * (1 - slowdown);
	}
}
