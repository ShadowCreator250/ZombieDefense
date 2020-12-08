import greenfoot.Actor;
import greenfoot.Greenfoot;

/**
 * An object which blocks the way for the zombies to get into the survivors base. The player has to avoid that zombies attack it.
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
	
	/**
	 * @see checkForZombies()
	 */
	@Override
	public void act() {
		checkForZombies();
	}
	
	/**
	 * Looks, if zombies reach it.
	 */
	private void checkForZombies() {
		for(Zombie zombie: getWorld().getObjects(Zombie.class)) {
			Greenfoot.delay(50);
			zombie.attackGate();
		}
	}
	
	public double getDurability() {
		return durability;
	}
	
	public void setDurability(double durability) {
		this.durability = durability;
	}
}
