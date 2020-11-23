import java.util.ArrayList;
import java.util.List;

import greenfoot.Greenfoot;
import greenfoot.World;

public class GameWorld extends World {

	public final static int GRID_SIZE_X = 15;
	public final static int GRID_SIZE_Y = 9;
	public final static int CELL_SIZE = 64;
	private Cell[][] grid;

	public GameWorld() {
		super(GRID_SIZE_X * CELL_SIZE, GRID_SIZE_Y * CELL_SIZE, 1);
		Greenfoot.setSpeed(50);
		this.grid = new Cell[GRID_SIZE_X][GRID_SIZE_Y];
		fillGridArrayWithEmptyCells();
		placeCells();
	}

	private void fillGridArrayWithEmptyCells() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y] = new NormalCell(x, y);
			}
		}
	}
	
	private void placeCells() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				addObject(grid[x][y], x*CELL_SIZE + CELL_SIZE/2, y*CELL_SIZE + CELL_SIZE/2);
			}
		}
	}

	/**
	 * Finds the first {@link Cell} with a given Cell type in the grid.
	 * @param type
	 * @return The first {@link Cell} with the given type.<br>
	 *         Returns <code>null<code> if no {@link Cell} is found.
	 */
	private Cell findFirstCellWithCellType(Class<? extends Cell> type) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				if (type.isInstance(grid[x][y])) {
					return grid[x][y];
				}
			}
		}
		return null;
	}
	
	/**
	 * Finds all {@link Cell}s with a given Cell type in the grid.
	 * @param type
	 * @return A List of {@link Cell}s with the given type.<br>
	 *         Returns an empty List if no {@link Cell} is found.
	 */
	private List<Cell> findAllCellsWithCellType(Class<? extends Cell> type) {
		List<Cell> result = new ArrayList<>();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				if (type.isInstance(grid[x][y])) {
					result.add(grid[x][y]);
				}
			}
		}
		return result;
	}

	public List<Cell> getNeighbourCells(Cell cell, boolean withCorners) {
		List<Cell> neighbours = new ArrayList<>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if ((x == 0 && y == 0) || (withCorners || isCornerBlockCheck(x, y))) {
					continue;
				}
				int checkX = cell.getGridX() + x;
				int checkY = cell.getGridY() + y;
				if (checkX >= 0 && checkX < GRID_SIZE_X && checkY >= 0 && checkY < GRID_SIZE_Y) {
					neighbours.add(grid[checkX][checkY]);
				}
			}
		}
		return neighbours;
	}

	private boolean isCornerBlockCheck(int x, int z) {
		if (Math.abs(x) == 1 && Math.abs(z) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the cell on a specific world position (if its grid position is not known).
	 * @param x the positions x value
	 * @param y the positions y value
	 * @return the {@link Cell} found on this position <br>
	 *         returns <code>null</code> if no {@link Cell} is found on this position
	 */
	public Cell CellFromWorldPos(int x, int y) {
		if (x < 0 || x > getWidth() || y < 0 || y > getHeight()) {
			return null;
		}
		return grid[x/CELL_SIZE][y/CELL_SIZE];
	}
	
	public void replaceCellInGrid(int gridX, int gridY, Cell cell) {
		if(gridX >= 0 && gridX < GRID_SIZE_X && gridY >= 0 && gridY < GRID_SIZE_Y) {
			grid[gridX][gridY] = cell;
		}
	}

	public Cell[][] getGrid() {
		return grid;
	}

}
