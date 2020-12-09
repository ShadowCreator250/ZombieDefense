import java.util.List;

/**
 * A special path cell that defines the start of the path.
 */
public class StartPathCell extends PathCell {

	public StartPathCell(int gridX, int gridY) {
		super(gridX, gridY);
	}

	/**
	 * Declares this cell as the start cell of the path and looks for its neighbour cells.
	 */
	@Override
	public void evaluatePathSectionType() {
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		super.evaluatePathSectionType(neighbourPathCells.size() + 1, logicalOrNeighbouringPathCellsWithWorldEdges());
	}

	/**
	 * Spawns the zombies outside the world on each wave.
	 * 
	 * @param zombie - a zombie that is defined in the different waves
	 */
	public void spawnZombie(Zombie zombie) {
		boolean[] wallsExistence = evaluateExistenceOfWorldEdgesAsNeighbours();
		int x = getX() + zombie.getPathOffsetX();
		int y = getY() + zombie.getPathOffsetY();
		if(wallsExistence[2]) {
			x = 0;
		} else if(wallsExistence[0]) {
			x = getWorld().getWidth() - 1;
		} else if(wallsExistence[3]) {
			y = 0;
		} else if(wallsExistence[1]) {
			y = getWorld().getHeight() - 1;
		}
		getWorld().addObject(zombie, x, y);
	}

}
