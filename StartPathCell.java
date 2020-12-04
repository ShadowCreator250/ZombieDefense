import java.util.List;

import greenfoot.Color;

public class StartPathCell extends PathCell {

	public StartPathCell(int gridX, int gridY) {
		super(gridX, gridY);
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(0, 255, 0)));
	}

	@Override
	public void evaluatePathSectionType() {
		// TODO: evaluate where zombies should come from -> where border is
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		super.evaluatePathSectionType(neighbourPathCells.size() + 1, logicalOrNeighbouringPathCellsWithWorldEdges());
	}

	@Override
	protected String getBgImageName() {
		return "bg-start-path-temp.png";
	}

}
