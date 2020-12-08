import java.util.List;
import java.util.Random;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class Zombie extends SmoothMover {

	public static final String OUTSIDE_IMAGE_NAME = "Zombie_Outside.png";
	public static final String INIT_IMAGE_NAME = "Zombie1.png";
	private static final String HURT_SOUND = "Zombie_Hurt.wav";
	private static final String DEATH_SOUND = "Zombie_Death.wav";

	private static final double TOLERANCE_RANGE = 2.0;
	private static final double DEFAULT_DAMAGE = 10.0;

	private double strength;
	private double resistance; // should be from 0.0-1.0, example: 0.7 stands for 70% less damage to get
	private double health;
	private double initalSpeed;
	private boolean slowedDown = false;
	private boolean attacked = false;
	private List<PathCell> path = null;
	private boolean pathExists = false;
	private int pathOffsetX;
	private int pathOffsetY;
	private int targetedPathCellIndex = 0;

	public Zombie() {
		this(1.0, 0.0, 0.5, 100);
	}

	/**
	 * @param strength   - how much damage he deals in per cent (1.0 is default)
	 * @param resistance - how much damage he can resist in per cent (0 is default)
	 * @param speed      - how fast he is in percent (1.0 is default)
	 * @param health     - how many health points he has (100 is default)
	 */
	public Zombie(double strength, double resistance, double speed, double health) {
		super();
		setImage(makeScaledImage(OUTSIDE_IMAGE_NAME));
		this.strength = strength;
		this.resistance = resistance;
		this.initalSpeed = speed;
		this.setSpeed(initalSpeed);
		this.health = health;
		this.pathOffsetX = calcPathOffet();
		this.pathOffsetY = calcPathOffet();
	}

	@Override
	public void act() {
		if(path == null) {
			initializePath();
		}
		if(!getWorld().isPaused()) {
			if(pathExists) {
				if(targetNodeIsNotPathEnd()) {
					move();
					if(hasReachedDestination(calcDestinationX(), calcDestinationY(), TOLERANCE_RANGE)) {
						behaviourifTargetReached();
					}
				}
			} else { // TODO: targetedPathCellIndex == path.size() - 1 -> EndPathCell

			}
			dropCurrencyIfDead();
		}
	}

	private GreenfootImage makeScaledImage(String imageName) {
		GreenfootImage img = new GreenfootImage(imageName);
		img.scale((int) (img.getWidth() / 3), (int) (img.getHeight() / 3));
		return img;
	}

	private void initializePath() {
		path = getWorld().getOneRandomPath();
		if(path.size() > 0) {
			updateMovementAndRotation();
			pathExists = true;
		} else {
			System.err.println("No path was found.");
		}
	}

	private void updateMovementAndRotation() {
		double speed = this.getSpeed();
		this.getMovement().setNeutral();
		this.getMovement().add(new Vector(calcDestinationX() - getX(), calcDestinationY() - getY(), speed));
		this.turnTowards(calcDestinationX(), calcDestinationY());
	}

	private void behaviourifTargetReached() {
		this.targetedPathCellIndex += 1;
		updateMovementAndRotation();
	}

	private boolean targetNodeIsNotPathEnd() {
		return targetedPathCellIndex < path.size() - 1;
	}

	private int calcDestinationX() {
		return path.get(targetedPathCellIndex).getX() + pathOffsetX;
	}

	private int calcDestinationY() {
		return path.get(targetedPathCellIndex).getY() + pathOffsetY;
	}

	private void dropCurrencyIfDead() {
		if(health <= 0) {
			getWorld().getGameState().getCoinsCounter().add(new Random().nextInt(3) + 4);
			Greenfoot.playSound(DEATH_SOUND);
			getWorld().removeObject(this);
		}
	}

	public void attackGate() {
		if(getOneIntersectingObject(BaseGate.class) != null) {
			this.slowDown(1);
			BaseGate.durability -= DEFAULT_DAMAGE * strength;
		}
	}

	private int calcPathOffet() {
		int random = new Random().nextInt(PathCell.PATH_WIDTH / 2);
		int factor = new Random().nextInt(2) == 0 ? -1 : 1;
		return factor * random;
	}

	public void absorbDamage(int damage) {
		Greenfoot.playSound(HURT_SOUND);
		this.health = health - (damage * (1 - resistance));
	}

	public void slowDown(double slowdownFactor) {
		this.setSpeed(getSpeed() * (1 - slowdownFactor));
		if(this.getSpeed() == getInitalSpeed()) {
			slowedDown = false;
		} else {
			slowedDown = true;
		}
	}

	public boolean isSlowedDown() {
		return slowedDown;
	}

	public double getInitalSpeed() {
		return initalSpeed;
	}

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}

	public int getPathOffsetX() {
		return pathOffsetX;
	}

	public int getPathOffsetY() {
		return pathOffsetY;
	}
}
