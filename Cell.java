import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;

/**
 * Divides the World in chunk-like sectors.<br>
 * These sectors define where for example the path is and where towers can be placed.
 */
public abstract class Cell extends Actor{
	
	private int gridX;
	private int gridY;

	public Cell(int gridX, int gridY) {
		this.gridX = gridX;
		this.gridY = gridY;
	}
	
	/**
	 * Produces an img that only has one color
	 * @param sizeX - img size x
	 * @param sizeY - img size y
	 * @param color - A Greenfoot.Color
	 * @return A GreenfootImage
	 */
	GreenfootImage paintMonochromeImage(int sizeX, int sizeY, Color color) {
		GreenfootImage img = new GreenfootImage(sizeX, sizeY);
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				img.setColorAt(x, y, color);
			}
		}
		return img;
	}
	
	/**
	 * Replaces the current Cell with another and changes the Grid respectively.
	 * @param otherCell - The new Cell. What you enter as gridX + gridY value is irrelevant. They will get replaced.
	 */
	public void replaceWith(Cell otherCell) {
		if(otherCell instanceof PathCell) {
			otherCell = new PathCell(gridX, gridY, ((PathCell) otherCell).getPathType());
		} else if(otherCell instanceof TowerCell) {
			otherCell = new TowerCell(gridX, gridY);
		} else if(otherCell instanceof NormalCell) {
			otherCell = new NormalCell(gridX, gridY);
		}
		GameWorld world = (GameWorld) getWorld();
		world.replaceCellInGrid(gridX, gridY, otherCell);
		world.addObject(otherCell, this.getX(), this.getY());
		world.removeObject(this);
	}
	
	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return gridY;
	}
	
}
