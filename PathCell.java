import greenfoot.Color;

public class PathCell extends Cell {
	
	private PathType pathType;

	public enum PathType {
		NORMAL, START, END;
		//START and END -> where the zombies should come from/go to
	}
	
	private enum PathSectionType {
		//TODO: What to do with PathSectionType
		DEAD_END, STRAIGHT, CURVE, T, FOUR_WAY;
		/* Could potentially be used to determine path for Zombies,
		 * if we don't use A* algorithm. 
		 * -> Should Zombies be intelligent and know how to get to survivors base
		 * or should they be dumb and wander aimlessly around until they reach gate?
		*/
	}
	
	public PathCell(int gridX, int gridY) {
		this(gridX, gridY, PathType.NORMAL);
	}

	public PathCell(int gridX, int gridY, PathType pathType) {
		super(gridX, gridY);
		this.pathType = pathType;
		chooseImage();
	}

	private void chooseImage() {
		//TODO: Monochrome img is temp
		switch (pathType) {
		case END:
			setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(255, 0, 0)));
			break;
		case START:
			setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(0, 255, 0)));
			break;
		case NORMAL:
		default:
			setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(255, 200, 0)));
			break;
		
		}
	}

	public PathType getPathType() {
		return pathType;
	}

}
