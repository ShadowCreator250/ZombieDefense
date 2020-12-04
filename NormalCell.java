import greenfoot.Color;

public class NormalCell extends Cell {

	public NormalCell(int gridX, int gridY) {
		super(gridX, gridY);
		// TODO: Monochrome img is temp
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(0, 200, 100)));
	}

}
