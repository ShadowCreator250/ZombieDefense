import java.util.List;

/**
 * 
 * A special path cell that represents the end of the path, where the survivors base gate is located.
 *
 */
public class EndPathCell extends PathCell {
	
	/**
	 * Creates an endpath cell object on the given place
	 * @param gridX - number of the cell in horizontal direction
	 * @param gridY - number of the cell in vertical direction
	 */
	public EndPathCell(int gridX, int gridY) {
		super(gridX, gridY);
	}

	/**
	 * Declares this cell as the end of the path at the world edge and looks for its neighbour cells.
	 */
	@Override
	public void evaluatePathSectionType() {
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		super.evaluatePathSectionType(neighbourPathCells.size() + 1, logicalOrNeighbouringPathCellsWithWorldEdges());
	}

}
