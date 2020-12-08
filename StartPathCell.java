import java.util.List;

import greenfoot.Color;

public class StartPathCell extends PathCell {

	public static final String BACKGROUND_IMAGE_NAME = "bg-start-path-temp.png";

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
		return BACKGROUND_IMAGE_NAME;
	}

	public void spawnZombie(Zombie zombie) {
		boolean[] wallsExistence = evaluateExistenceOfWorldEdgesAsNeighbours();
		int x = getX() + zombie.getPathOffsetX();
		int y = getY() + zombie.getPathOffsetY();
		if(wallsExistence[2]) {
			x = 0;
		} else if(wallsExistence[0]) {
			x = getWorld().getWidth() - 1;
		} else if(wallsExistence[3]) {
			y = 0;
		} else if(wallsExistence[1]) {
			y = getWorld().getHeight() - 1;
		}
		getWorld().addObject(zombie, x, y);
	}

}
