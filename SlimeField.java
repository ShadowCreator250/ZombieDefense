import greenfoot.Color;

public class SlimeField extends Obstacle {

	public static final int PRICE = 20;
	private static final double DEFAULT_SLOWDOWN = 0.5; // should be from 0.0-1.0, example: 0.7 stands for 70% less speed
	private static final Color COLOR = new Color(0, 255, 0);
	private static final int POINTS = 1500;
	private boolean slowedDown = false;

	public SlimeField() {
		super();
		createImage(COLOR, POINTS);
	}

	public void act() {
		//slowDownZombiesInRangeOnce();
	}
	
	private void slowDownZombiesInRangeOnce() {
		for(Zombie zombie: getWorld().getObjects(Zombie.class)) {
			if(slowedDown == false ) {
				slowedDown = true;
				zombie.slowDown(DEFAULT_SLOWDOWN);
			}
			
		}		
	}

	@Override
	public int getPrice() {
		return PRICE;
	}
}
