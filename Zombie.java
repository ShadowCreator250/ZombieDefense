import java.util.List;
import java.util.Random;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 * A SmoothMover that has got the aim to destroy the base gate of the player.
 * For this it searches its path individually, after finding it stays there and
 * attacks the gate.
 */
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

	/**
	 * Creates an zombie object with default values for strength, resistance, speed
	 * and health.
	 */
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

	/**
	 * It searches its own path to find the base gate and stays there when reached.
	 * When getting too much damage, it drops currency for the player.
	 */
	@Override
	public void act() {
		if(path == null) {
			initializePath();
		}
		if(!getWorld().isPaused()) {
			if(pathExists) {
				move();
				if(hasReachedDestination(calcDestinationX(), calcDestinationY(), TOLERANCE_RANGE)) {
					behaviourIfTargetReached();
					behaviourIfReachedEndCell();
				}
			}
			dropCurrencyIfDead();
			slowDownIfOnSlimeField();
		}
	}

	/**
	 * defines what the zombie should do if it reaches the EndCell and therefore the
	 * Gate
	 */
	private void behaviourIfReachedEndCell() {
		if(getWorld().cellFromWorldPos(getX(), getY()) instanceof EndPathCell) {
			if(getOneIntersectingObject(BaseGate.class) != null) {
				this.getMovement().setNeutral();
				attackGate();
			} else if(getObjectsInRange(GameWorld.CELL_SIZE, BaseGate.class).size() > 0) {
				BaseGate gate = (BaseGate) getObjectsInRange(GameWorld.CELL_SIZE * 2, BaseGate.class).get(0);
				this.getMovement().setNeutral();
				this.getMovement().add(new Vector(gate.getX() - getX(), gate.getY() - getY(), initalSpeed));
			}
		}

	}

	/**
	 * Initializes the path for the zombie where it walks to.
	 */
	private void initializePath() {
		path = getWorld().getOneRandomPath();
		if(path.size() > 0) {
			updateMovementAndRotation();
			pathExists = true;
		} else {
			System.err.println("No path was found.");
		}
	}

	/**
	 * Sets the direction and speed of the zombie, so it walks to the next point on
	 * its path.
	 */
	private void updateMovementAndRotation() {
		double speed = this.getSpeed();
		this.getMovement().setNeutral();
		this.getMovement().add(new Vector(calcDestinationX() - getX(), calcDestinationY() - getY(), speed));
		this.turnTowards(calcDestinationX(), calcDestinationY());
	}

	/**
	 * Sets a new target on the path for the zombie after reaching its last target.
	 */
	private void behaviourIfTargetReached() {
		this.targetedPathCellIndex += 1;
		updateMovementAndRotation();
	}

	/**
	 * Slows down the zombie when walking over a slime field.
	 */
	private void slowDownIfOnSlimeField() {
		if(getIntersectingObjects(SlimeField.class).size() > 0) {
			if(!isSlowedDown()) {
				slowDown(SlimeField.getDefaultSlowdown());
			}
		} else {
			if(isSlowedDown()) {
				slowDown(0);
			}
		}
	}

	private boolean targetNodeIsPathEnd() {
		return !(targetedPathCellIndex < path.size() - 1);
	}

	private int calcDestinationX() {
		return path.get(targetedPathCellIndex).getX() + pathOffsetX;
	}

	private int calcDestinationY() {
		return path.get(targetedPathCellIndex).getY() + pathOffsetY;
	}

	/**
	 * Drops coins for the player when the zombie has got no more health.
	 */
	private void dropCurrencyIfDead() {
		if(health <= 0) {
			getWorld().getGameState().getCoinsCounter().add(new Random().nextInt(3) + 4);
			Greenfoot.playSound(DEATH_SOUND);
			getWorld().removeObject(this);
		}
	}

	/**
	 * Stops the movement and deals damage to the base gate when reaching it.
	 */
	public void attackGate() {
		BaseGate gate = (BaseGate) getOneIntersectingObject(BaseGate.class);
		if(gate != null) {
			gate.absorbDamage(DEFAULT_DAMAGE * strength);
		}
	}

	private int calcPathOffet() {
		int random = new Random().nextInt(PathCell.PATH_WIDTH / 2);
		int factor = new Random().nextInt(2) == 0 ? -1 : 1;
		return factor * random;
	}

	/**
	 * Absorbs damage when hitted by a tower.
	 * 
	 * @param damage - the damage dealt by the tower
	 */
	public void absorbDamage(int damage) {
		Greenfoot.playSound(HURT_SOUND);
		this.health = health - (damage * (1 - resistance));
	}

	/**
	 * Slows down the zombie.
	 * 
	 * @param slowdownFactor - how strong the slowdown is
	 */

	public void slowDown(double slowdownFactor) {
		this.setSpeed(getSpeed() * (1 - slowdownFactor));
		if(this.getSpeed() == getInitalSpeed()) {
			slowedDown = false;
		} else {
			slowedDown = true;
		}
	}

	/**
	 * Gets an image that has to be scaled down.
	 * 
	 * @param imageName - the name of the image to scale
	 * 
	 * @return the scaled image
	 */
	private GreenfootImage makeScaledImage(String imageName) {
		GreenfootImage img = new GreenfootImage(imageName);
		img.scale((int) (img.getWidth() / 3), (int) (img.getHeight() / 3));
		return img;
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
