import java.util.List;

import greenfoot.Color;

public class EndPathCell extends PathCell {

	public EndPathCell(int gridX, int gridY) {
		super(gridX, gridY);
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(255, 0, 0)));
	}

	@Override
	public void evaluatePathSectionType() {
		// TODO: evaluate where zombies should go to from -> where border is
		// or ends path on gate?
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		super.evaluatePathSectionType(neighbourPathCells.size() + 1, logicalOrNeighbouringPathCellsWithWorldEdges());
	}

	@Override
	protected String getBgImageName() {
		return "bg-end-path-temp.png";
	}

}
