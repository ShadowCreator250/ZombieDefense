import java.util.ArrayList;
import java.util.List;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;

/**
 * Divides the World in chunk-like sectors.<br>
 * These sectors define where for example the path is and where towers can be
 * placed.
 */
public abstract class Cell extends Actor {

	private int gridX;
	private int gridY;

	public Cell(int gridX, int gridY) {
		this.gridX = gridX;
		this.gridY = gridY;
	}

	/**
	 * Produces an img that only has one color
	 * 
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
	 * 
	 * @param otherCell - The new Cell. What you enter as gridX + gridY value is
	 *                  irrelevant. They will get replaced.
	 */
	public void replaceWith(Cell otherCell) {
		otherCell.setGridX(this.gridX);
		otherCell.setGridY(this.gridY);
		GameWorld world = getWorld();
		world.replaceCellInGrid(otherCell);
		world.addObject(otherCell, this.getX(), this.getY());
		world.removeObject(this);
	}

	public List<Cell> getNeighbourCells(boolean withCorners) {
		List<Cell> neighbours = new ArrayList<>();
		Cell[][] grid = getWorld().getGrid();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if((x == 0 && y == 0) || (withCorners || isCornerBlockCheck(x, y))) {
					continue;
				}
				int checkX = this.getGridX() + x;
				int checkY = this.getGridY() + y;
				if(checkX >= 0 && checkX < GameWorld.GRID_SIZE_X && checkY >= 0 && checkY < GameWorld.GRID_SIZE_Y) {
					neighbours.add(grid[checkX][checkY]);
				}
			}
		}
		return neighbours;
	}

	private boolean isCornerBlockCheck(int x, int z) {
		if(Math.abs(x) == 1 && Math.abs(z) == 1) {
			return true;
		}
		return false;
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	@Override
	public GameWorld getWorld() {
		return (GameWorld) super.getWorld();
	}

}
