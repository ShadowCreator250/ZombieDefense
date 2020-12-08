import java.util.List;

public class NormalPathCell extends PathCell {

	public NormalPathCell(int gridX, int gridY) {
		super(gridX, gridY);
	}

	@Override
	public void evaluatePathSectionType() {
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		super.evaluatePathSectionType(neighbourPathCells.size(), evaluateExistenceOfNeighbouringPathCells());
	}

}
