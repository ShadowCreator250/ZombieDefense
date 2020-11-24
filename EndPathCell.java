import greenfoot.Color;

public class EndPathCell extends PathCell {

	public EndPathCell(int gridX, int gridY) {
		super(gridX, gridY);
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(255, 0, 0)));
	}

}
