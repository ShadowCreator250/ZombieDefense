import greenfoot.Actor;

/**
 * An object which blocks the way for the zombies to get into the survivors
 * base. The player has to avoid that zombies attacking it.
 */

public class BaseGate extends Actor {

	private static final String GATE_IMAGE_NAME = "Gate.png";

	private double durability = 300;

	/**
	 * Creates an base gate object and sets its image.
	 */
	public BaseGate() {
		setImage(GATE_IMAGE_NAME);
	}

	public double getDurability() {
		return durability;
	}

	public void setDurability(double durability) {
		this.durability = durability;
	}

	/**
	 * reduces its health by the amount of damage it gets
	 * 
	 * @param damage the damage it absorbs
	 */
	public void absorbDamage(double damage) {
		this.durability -= damage;
	}
}
