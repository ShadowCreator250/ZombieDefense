import java.util.List;

import greenfoot.Actor;
import greenfoot.Greenfoot;

public abstract class Tower extends Actor {

	public static final String INIT_IMAGE_NAME = "Tower1.png";
	private List<Zombie> zombies;

	private int range;
	private int reloadTime;
	private int shootCountDown;
	private int damage;

	public Tower(int range, int reloadTime, int damage) {
		setImage(INIT_IMAGE_NAME);
		this.range = range;
		this.reloadTime = reloadTime;
		this.damage = damage;
		this.shootCountDown = reloadTime;
	}

	@Override
	public void act() {
		if(!getWorld().isPaused()) {
			if(reload()) {
				shoot();
			}
		}
		checkRemoveTowerClick();
	}

	private void checkRemoveTowerClick() {
		if(Greenfoot.mouseClicked(this) && Greenfoot.getMouseInfo().getButton() == 1) {
			GameState.MouseState mouseState = getWorld().getGameState().getMouseState();
			if(mouseState == GameState.MouseState.DELETE_TOOL) {
				getWorld().getGameState().getCoinsCounter().add(this.getPrice());
				getWorld().removeObject(this);
			}
		}
	}

	private void shoot() {
		if(areZombiesInRange()) {
			for (Zombie target : zombies) {
				Projectile p = selectCorrectProjectile();
				getWorld().addObject(p, this.getX(), this.getY());
				shootCountDown = reloadTime;
				p.turnTowards(target.getX(), target.getY());
				p.move(calculateDistance(target));
				if(p.getExactX() == target.getExactX() && p.getExactY() == target.getExactY()) {
					// target.absorbDamage(damage); //damage needs to be added
					getWorld().removeObject(p);
				}
			}
		}
	}

	private double calculateDistance(Zombie target) {
		double d = Math.pow((target.getExactX() - this.getX()), 2) + Math.pow((target.getExactY() - this.getY()), 2);
		double distance = Math.sqrt(d);
		return distance;
	}

	private Projectile selectCorrectProjectile() {
		Projectile p;
		if(this instanceof ArcherTower) {
			p = new Arrow();
		} else if(this instanceof BombTower) {
			p = new Bomb();
		} else {
			p = new Bullet();
		}
		return p;
	}

	protected abstract void shootProjectile(int x, int y);

	private boolean reload() {
		if(shootCountDown > 0) {
			shootCountDown--;
			return false;
		} else {
			return true;
		}
	}

	private boolean areZombiesInRange() {
		zombies = getObjectsInRange(range, Zombie.class);
		if(zombies.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public abstract int getPrice();

	@Override
	public GameWorld getWorld() {
		return (GameWorld) super.getWorld();
	}
}
