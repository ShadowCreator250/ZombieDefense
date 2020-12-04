import java.util.List;

import greenfoot.Color;

public class NormalPathCell extends PathCell {

	public NormalPathCell(int gridX, int gridY) {
		super(gridX, gridY);
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(255, 200, 0)));
	}

	public void evaluatePathSectionType() {
		List<PathCell> neighbourPathCells = getNeighbouringPathCells();
		boolean[] neighbourPathCellsLocation = evaluateLocationsOfNeighbouringPathCells();
		switch (neighbourPathCells.size()) {
			case 1:
				setPathSectionType(PathSectionType.DEAD_END);
				setImage(new ImageCombiner(getBgImageName(), "dead_end-path-temp.png").combineToGFImg());
				for (int i = 0; i < neighbourPathCellsLocation.length; i++) {
					if(neighbourPathCellsLocation[i]) {
						setDirection(i);
					}
				}
				break;
			case 2:
				PathCell cell1 = neighbourPathCells.get(0);
				PathCell cell2 = neighbourPathCells.get(1);
				if(cell1.getGridX() == cell2.getGridX() || cell1.getGridY() == cell2.getGridY()) {
					setPathSectionType(PathSectionType.STRAIGHT);
					setImage(new ImageCombiner(getBgImageName(), "straight-path-temp.png").combineToGFImg());
					if(neighbourPathCellsLocation[1]) {
						setDirection(1);
					} else {
						setDirection(0);
					}
				} else {
					setPathSectionType(PathSectionType.CURVE);
					setImage(new ImageCombiner(getBgImageName(), "curve-path-temp.png").combineToGFImg());
					for (int i = 0; i < neighbourPathCellsLocation.length; i++) {
						if(neighbourPathCellsLocation[i] && neighbourPathCellsLocation[(i + 1) % neighbourPathCellsLocation.length]) {
							setDirection(i);
						}
					}
				}
				break;
			case 3:
				setPathSectionType(PathSectionType.T);
				setImage(new ImageCombiner(getBgImageName(), "t-path-temp.png").combineToGFImg());
				for (int i = 1; i < neighbourPathCellsLocation.length; i++) {
					if(neighbourPathCellsLocation[(neighbourPathCellsLocation.length + i - 1) % neighbourPathCellsLocation.length]
							&& neighbourPathCellsLocation[i] && neighbourPathCellsLocation[(i + 1) % neighbourPathCellsLocation.length]) {
						setDirection(i - 1);
					}
				}
				break;
			case 4:
			case 5:
				setPathSectionType(PathSectionType.CROSS);
				setImage(new ImageCombiner(getBgImageName(), "cross-path-temp.png").combineToGFImg());
				break;
			case 0:
			default:
				setPathSectionType(PathSectionType.DOT);
				setImage(new ImageCombiner(getBgImageName(), "dot-path-temp.png").combineToGFImg());
				break;
		}
	}

	@Override
	protected String getBgImageName() {
		return "bg-normal-path-temp.png";
	}

}
