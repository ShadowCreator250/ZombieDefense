import java.io.File;

import greenfoot.Color;

public class NormalPathCell extends PathCell {

	public NormalPathCell(int gridX, int gridY) {
		super(gridX, gridY);
		setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(255, 200, 0)));
	}
	
	public void setImg() {
		setImage(new ImageCombiner(new File("./images/"), "bg-normal-path-temp.png", "dot-path-temp.png").combineToGFImg());
	}

}
