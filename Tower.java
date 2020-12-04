import java.util.List;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

public class Tower extends Actor {

	private MouseInfo mouse = Greenfoot.getMouseInfo();

	public Tower() {
		
	}
	
	private void placeTower(Tower tower) {
		List<TowerCell> towerCells = getWorld().getObjects(TowerCell.class);
		for (TowerCell towerCell : towerCells) {
			if (mouse.getButton() == 1 && mouse.getX() == towerCell.getGridX() && mouse.getY() == towerCell.getGridY()) {
				getWorld().addObject(tower, mouse.getX(), mouse.getY());
			}
			else if (mouse.getButton() == 1 && (mouse.getX() != towerCell.getGridX() || mouse.getY() != towerCell.getGridY())){
				showErrorText();			
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
	
	private void showErrorText() {
		GreenfootImage image = new GreenfootImage(mouse.getX(), mouse.getY());
		setImage(image);
		image.setColor(Color.BLACK);
		image.drawString("You cannot place the tower here", 10, 10);
	}
}
