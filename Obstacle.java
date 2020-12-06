import java.util.List;
import java.util.Random;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

public class Obstacle extends Actor {

	private MouseInfo mouse = Greenfoot.getMouseInfo();
	private int displayTime = 0;
	private static final int SIZE = 50;
	private static final int HALFSIZE = SIZE/2;
	private static final Random randomizer = new Random();

	public Obstacle() {

	}

	@Deprecated
	private void placeObstacle(Obstacle obstacle) {
		List<PathCell> pathCells = getWorld().getObjects(PathCell.class);
		for (PathCell pathCell : pathCells) {
			if(mouse.getX() == pathCell.getGridX() && mouse.getY() == pathCell.getGridY() && mouse.getButton() == 1) {
				getWorld().addObject(obstacle, mouse.getX(), mouse.getY());
			} else if(mouse.getButton() == 1 && (mouse.getX() != pathCell.getGridX() || mouse.getY() != pathCell.getGridY())) {
				showErrorText();
			}
		}
	}

	@Deprecated
	public void buyObstacle(Obstacle obstacle) {
		// if(currency > cost) {
		// placeObstacle(obstacle);
		// currency -= cost;
		// }
		// TODO: implement currency and the cost of each obstacle
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
	
	public void createImage(Color color, int points) {
		GreenfootImage image = new GreenfootImage(SIZE, SIZE);

        for (int i = 0; i < points; i++) {
            int x = randomCoord();
            int y = randomCoord();

            image.setColorAt(x, y, color);
        }
        setImage(image);
	}
	
	private int randomCoord() {
        int val = HALFSIZE + (int) (randomizer.nextGaussian() * (HALFSIZE / 2));
        
        if (val < 0)
            return 0;

        if (val > SIZE - 2)
            return SIZE - 2;
        else
            return val;
    }
}
