import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
/**
 * Divides the World in chunk-like sectors
 * 
 * These sectors define where the path is and where towers can be placed.
 */
public class Cell extends Actor{
	
	public enum CellType {
		PATH, TOWER, NONE, START, END;
		//PATH and TOWER are self-explanatory 
		//NONE -> nothing should be placed here (for the unused surrounding area)
		//START and END -> where the zombies should come from/go to
	}
	
	private CellType cellType;
	private int gridX;
	private int gridY;

	public Cell(CellType nodeType, int gridX, int gridY) {
		this.setCellType(nodeType);
		this.gridX = gridX;
		this.gridY = gridY;
		chooseImage();
	}

	private void chooseImage() {
		//TODO: monochrome images are temporarily 
		switch (cellType) {
		case END:
			setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(255, 0, 0)));
			break;
		case NONE:
			setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(0, 255, 100)));
			break;
		case PATH:
			setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(255, 200, 0)));
			break;
		case START:
			setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(0, 255, 0)));
			break;
		case TOWER:
			setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(64, 64, 64)));
			break;
		default:
			setImage(paintMonochromeImage(GameWorld.CELL_SIZE, GameWorld.CELL_SIZE, new Color(255, 255, 255)));
			break;
		}
	}
	
	private GreenfootImage paintMonochromeImage(int sizeX, int sizeY, Color color) {
		GreenfootImage img = new GreenfootImage(sizeX, sizeY);
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				img.setColorAt(x, y, color);
			}
		}
		return img;
	}

	public CellType getCellType() {
		return cellType;
	}
	
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
		chooseImage();
	}
	
	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return gridY;
	}
	
}
