
public class Zombie extends SmoothMover {

	public static final String INIT_IMAGE_NAME = "Zombie1.png";

	private double strength;
	private double resistance; // should be from 0.0-1.0, example: 0.7 stands for 70% less damage to get
	private double speed;
	private double health;

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
		setImage(INIT_IMAGE_NAME);
		this.strength = strength;
		this.resistance = resistance;
		this.speed = speed;
		this.health = health;
	}

	@Override
	public void act() {

	}

	private void dropCurrency() {
		if(health <= 0) {
			//GameState.coinsCounter++;
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
		if(getOneIntersectingObject(SlimeField.class) != null) {
			this.speed = speed * (1 - slowdown);
		}	
	}
}
