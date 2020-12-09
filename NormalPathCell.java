import java.util.List;

/**
 * A special path cell that is located between starting and ending path cell.
 */
public class NormalPathCell extends PathCell {

	/**
	 * Creates an normal path cell on the given location.
	 * 
	 * @param gridX - number of the cell in horizontal direction
	 * @param gridY - number of the cell in vertical direction
	 */
	public NormalPathCell(int gridX, int gridY) {
		super(gridX, gridY);
	}

	/**
	 * Declares the location of this cell while looking for neighbour cells around it.
	 */
	@Override
	public void evaluatePathSectionType() {
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		super.evaluatePathSectionType(neighbourPathCells.size(), evaluateExistenceOfNeighbouringPathCells());
	}

}
