import java.util.List;

import greenfoot.Color;

public class StartPathCell extends PathCell {

	public StartPathCell(int gridX, int gridY) {
		super(gridX, gridY);
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(0, 255, 0)));
	}
	
	@Override
	public void evaluatePathSectionType() {
		//TODO: evaluate where zombies should come from -> where border is
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		switch (neighbourPathCells.size()) {
			case 0:
				setPathSectionType(PathSectionType.DEAD_END);
				break;
			case 1:
				//TODO: Straight or curve
				setPathSectionType(PathSectionType.STRAIGHT);
				// setPathSectionType(PathSectionType.CURVE);
				break;
			case 2:
				setPathSectionType(PathSectionType.T);
				break;
			case 3:
				setPathSectionType(PathSectionType.CROSS);
				break;
			case 4:
			default:
				setPathSectionType(PathSectionType.DOT);
				break;
		}
	}

}
