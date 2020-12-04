import java.util.List;

import greenfoot.Color;

public class NormalPathCell extends PathCell {

	public NormalPathCell(int gridX, int gridY) {
		super(gridX, gridY);
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(255, 200, 0)));
	}

	@Override
	public void evaluatePathSectionType() {
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		super.evaluatePathSectionType(neighbourPathCells.size(), evaluateExistenceOfNeighbouringPathCells());
	}

	@Override
	protected String getBgImageName() {
		return "bg-normal-path-temp.png";
	}

}
