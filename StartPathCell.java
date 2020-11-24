import greenfoot.Color;

public class StartPathCell extends PathCell {

	public StartPathCell(int gridX, int gridY) {
		super(gridX, gridY);
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(0, 255, 0)));
	}

}
