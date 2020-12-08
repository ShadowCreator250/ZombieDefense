import java.util.List;
import java.util.Random;

import greenfoot.Actor;
import greenfoot.Greenfoot;

/**
 * An abstract class that determines the plan for a tower, but needs to be specialized as a special tower (one of the subclasses).
 */
public abstract class Tower extends Actor {

	public static final String INIT_IMAGE_NAME = "Tower1.png";

	private int range;
	private int reloadTime;
	private int shootCountDown;
	private int damage;

	/**
	 * Creates an tower object with given characteristics, used at its subclasses.
	 * 
	 * @param range - how far it can attack
	 * @param reloadTime - how long it needs to reload and shoot again
	 * @param damage - how much damage it deals
	 */
	public Tower(int range, int reloadTime, int damage) {
		setImage(INIT_IMAGE_NAME);
		this.range = range;
		this.reloadTime = reloadTime;
		this.damage = damage;
		this.shootCountDown = 0;
	}
	
	/**
	 * Shoots on zombies when the world is not paused and has to reload. Can be deleted after placed by the player.
	 */
	@Override
	public void act() {
		if(!getWorld().isPaused()) {
			if(reload()) {
				shoot();
			}
		}
		checkRemoveTowerClick();
	}
	
	/**
	 * Deletes the tower when clicking on it while using the delete tool. The player gets his coins back.
	 */
	private void checkRemoveTowerClick() {
		if(Greenfoot.mouseClicked(this) && Greenfoot.getMouseInfo().getButton() == 1) {
			GameState.MouseState mouseState = getWorld().getGameState().getMouseState();
			if(mouseState == GameState.MouseState.DELETE_TOOL) {
				getWorld().getGameState().getCoinsCounter().add(this.getPrice());
				getWorld().removeObject(this);
			}
		}
	}
	
	/**
	 * Shoots at a zombie in range that got set as target. 
	 */
	private void shoot() {
		if(areZombiesInRange()) {
			int index = new Random().nextInt(getZombiesInRange().size());
			Zombie target = getZombiesInRange().get(index);
			shootProjectileAt(target, damage);
			shootCountDown = reloadTime;
		}
	}
	
	/**
	 * Predicts the movement of the targeted zombie and shoots a projectile to it.
	 *  
	 * @param target - the zombie that was set as target.
	 * @param damage - sets the damage the projectile will deal
	 */
	public void shootProjectileAt(Zombie target, int damage) {
		int destinationX = (int) Math.round(target.getX() + target.getMovement().getX() * getZombieMovementForwardPrediction());
		int destinationY = (int) Math.round(target.getY() + target.getMovement().getY() * getZombieMovementForwardPrediction());
		getWorld().addObject(getProjetile(destinationX, destinationY, damage), getX(), getY());
	}
	
	/**
	 * Reloads a new projectile for the tower which needs some time (the countdown).
	 * 
	 * @return <code>true</code> - if the countdown is zero (the projectile reloaded) <p> <code>false</code> - if the countdown is not zero (not reloaded)
	 */
	private boolean reload() {
		if(shootCountDown > 0) {
			shootCountDown--;
			return false;
		} else {
			return true;
		}
	}

	private boolean areZombiesInRange() {
		return getZombiesInRange().size() > 0;
	}

	private List<Zombie> getZombiesInRange() {
		return getObjectsInRange(range, Zombie.class);
	}

	public abstract int getPrice();

	public abstract int getZombieMovementForwardPrediction();

	public abstract Projectile getProjetile(int destinationX, int destinationY, int damage);

	@Override
	public GameWorld getWorld() {
		return (GameWorld) super.getWorld();
	}
}
