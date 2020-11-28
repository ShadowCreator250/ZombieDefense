import java.util.List;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;

public class Tower extends Actor {

	public Tower() {

	}

	private void placeTower(Tower tower) {
		MouseInfo mouse = Greenfoot.getMouseInfo();
		List<TowerCell> towerCells = getWorld().getObjects(TowerCell.class);
		for (TowerCell towerCell : towerCells) {
			if (mouse.getX() == towerCell.getGridX() && mouse.getY() == towerCell.getGridY() && mouse.getButton() == 1) {
				getWorld().addObject(tower, mouse.getX(), mouse.getY());
			}
			else {
				//TODO: show text "You cannot place the tower here"
			}
		}
	}
	
	public void buyTower(Tower tower) {
		//if(currency > cost) {
		// 	placeTower(tower);
		//	currency -= cost;
		//}
		//TODO: implement currency and the cost of each tower
	}
}
