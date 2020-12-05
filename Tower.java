import java.util.List;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

public abstract class Tower extends Actor {

	private MouseInfo mouse = Greenfoot.getMouseInfo();
	private List<Zombie> zombies;
	private int displayTime = 0;

	private int range;
	private int reloadTime;
	private int shootCountDown;

	public Tower(int range, int reloadTime) {
		this.range = range;
		this.reloadTime = reloadTime;
		this.shootCountDown = reloadTime;
	}

	@Override
	public void act() {
		if(reload()) {
			shoot();
		}
	}

	private void shoot() {
		if(areZombiesInRange()) {
			for(Zombie target: zombies) {		
				Projectile p = new Arrow();
				getWorld().addObject(p, this.getX(), this.getY());
				shootCountDown = reloadTime;
				p.turnTowards(target.getX(), target.getY());
				p.move(calculateDistance(target));
				if(p.getExactX() == target.getExactX() && p.getExactY() == target.getExactY()) {
					//target.absorbDamage(damage); //damage needs to be added
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

	@Deprecated
	private void placeTower(Tower tower) {
		List<TowerCell> towerCells = getWorld().getObjects(TowerCell.class);
		for (TowerCell towerCell : towerCells) {
			if(mouse.getButton() == 1 && mouse.getX() == towerCell.getGridX() && mouse.getY() == towerCell.getGridY()) {
				getWorld().addObject(tower, mouse.getX(), mouse.getY());
			} else if(mouse.getButton() == 1 && (mouse.getX() != towerCell.getGridX() || mouse.getY() != towerCell.getGridY())) {
				showErrorText();
			}
		}
	}

	@Deprecated
	public void buyTower(Tower tower) {
		// if(currency > cost) {
		// placeTower(tower);
		// currency -= cost;
		// }
		// TODO: implement currency and the cost of each tower
	}

	@Deprecated
	private void showErrorText() {
		GreenfootImage image = new GreenfootImage(mouse.getX(), mouse.getY());
		setImage(image);
		image.setColor(Color.BLACK);
		image.drawString("You cannot place the tower here", 10, 10);
		int i = 300;
		while (displayTime < i) {
			displayTime++;
		}
		image.clear();
	}
}
