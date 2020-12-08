import java.util.List;

public class EndPathCell extends PathCell {

	public EndPathCell(int gridX, int gridY) {
		super(gridX, gridY);
	}

	@Override
	public void evaluatePathSectionType() {
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		super.evaluatePathSectionType(neighbourPathCells.size() + 1, logicalOrNeighbouringPathCellsWithWorldEdges());
	}

}
