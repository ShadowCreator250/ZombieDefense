import greenfoot.Actor;
import greenfoot.Greenfoot;

public class BaseGate extends Actor {
	
	private static final String GATE_IMAGE_NAME = "Gate.png";
	
	public static double durability = 300;
	
	public BaseGate() {
		setImage(GATE_IMAGE_NAME);
	}
	
	@Override
	public void act() {
		checkForZombies();
	}
	
	private void checkForZombies() {
		for(Zombie zombie: getWorld().getObjects(Zombie.class)) {
			Greenfoot.delay(50);
			zombie.attackGate();
		}
	}
}
