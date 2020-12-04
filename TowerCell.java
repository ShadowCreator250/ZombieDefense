import greenfoot.Color;

public class TowerCell extends Cell {

	public TowerCell(int gridX, int gridY) {
		super(gridX, gridY);
		// TODO: Monochrome img is temp
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(64, 64, 64)));
	}

}
