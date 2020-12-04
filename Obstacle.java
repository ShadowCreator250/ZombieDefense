import java.util.List;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

public class Obstacle extends Actor {
	
	private MouseInfo mouse = Greenfoot.getMouseInfo();
	
	public Obstacle() {
		
	}
	
	private void placeObstacle(Obstacle obstacle) {
		List<PathCell> pathCells = getWorld().getObjects(PathCell.class);
		for (PathCell pathCell : pathCells) {
			if (mouse.getX() == pathCell.getGridX() && mouse.getY() == pathCell.getGridY() && mouse.getButton() == 1) {
				getWorld().addObject(obstacle, mouse.getX(), mouse.getY());
			}
			else if (mouse.getButton() == 1 && (mouse.getX() != pathCell.getGridX() || mouse.getY() != pathCell.getGridY())) {
				showErrorText();
			}
		}
	}
	
	public void buyObstacle(Obstacle obstacle) {
		//if(currency > cost) {
		// 	placeObstacle(obstacle);
		//	currency -= cost;
		//}
		//TODO: implement currency and the cost of each obstacle
	}
	
	private void showErrorText() {
		GreenfootImage image = new GreenfootImage(mouse.getX(), mouse.getY());
		setImage(image);
		image.setColor(Color.BLACK);
		image.drawString("You cannot place the tower here", 10, 10);
	}
}
