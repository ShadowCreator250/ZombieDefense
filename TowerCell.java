import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 * A special cell where towers can only be placed on.
 */
public class TowerCell extends Cell {

	private static final String CELL_BACKGROUND_OVERLAY_IMAGE_NAME = "tower-cell-overlay.png";

	/**
	 * Creates an tower cell object and sets its image.
	 * 
	 * @param gridX - number of the cell in horizontal direction
	 * @param gridY - number of the cell in vertical direction
	 */
	public TowerCell(int gridX, int gridY) {
		super(gridX, gridY);
		constructImage();
	}

	/**
	 * Adds an overlay to the image of the tower cell.
	 */
	private void constructImage() {
		GreenfootImage img = getImage();
		img.drawImage(new GreenfootImage(CELL_BACKGROUND_OVERLAY_IMAGE_NAME), 0, 0);
		setImage(img);
	}

	/**
	 * @see checkAddTowerClick()
	 */
	@Override
	public void act() {
		checkAddTowerClick();
	}

	/**
	 * Checks which type of tower is going to be placed on the tower cell and adds
	 * the tower onto itself.
	 */
	private void checkAddTowerClick() {
		if(Greenfoot.mouseClicked(this) && Greenfoot.getMouseInfo().getButton() == 1) {
			if(!(getWorld().getObjectsAt(getX(), getY(), Tower.class).size() > 0)) {
				CursorImage.MouseState mouseState = getWorld().getCursorImage().getMouseState();
				if(mouseState == CursorImage.MouseState.PLACE_ARCHER_TOWER && getWorld().haveEnoughCoins(ArcherTower.PRICE)) {
					getWorld().addObject(new ArcherTower(), getX(), getY());
					getWorld().getCoinsCounter().add(-ArcherTower.PRICE);
				} else if(mouseState == CursorImage.MouseState.PLACE_BOMB_TOWER && getWorld().haveEnoughCoins(BombTower.PRICE)) {
					getWorld().addObject(new BombTower(), getX(), getY());
					getWorld().getCoinsCounter().add(-BombTower.PRICE);
				} else if(mouseState == CursorImage.MouseState.PLACE_SNIPER_TOWER && getWorld().haveEnoughCoins(SniperTower.PRICE)) {
					getWorld().addObject(new SniperTower(), getX(), getY());
					getWorld().getCoinsCounter().add(-SniperTower.PRICE);
				}
			}
		}
	}

}
