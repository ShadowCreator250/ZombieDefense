import java.util.List;

import greenfoot.Color;

public class NormalPathCell extends PathCell {

	public static final String BACKGROUND_IMAGE_NAME = "bg-normal-path-temp.png";

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
		return BACKGROUND_IMAGE_NAME;
	}

}
