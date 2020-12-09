import java.util.ArrayList;
import java.util.List;

import greenfoot.Actor;

/**
 * the menu is for selecting the mouse mode
 */
public class Menu extends Actor {

	public static final String BACKGROUND_IMAGE_NAME = "menu-bg.png";

	private List<MenuElement> menuElements = new ArrayList<>();
	private final MenuElement mouseSelector = new MenuElement(this, calculateXOffset(1), 50, CursorImage.MouseState.NONE);
	private final MenuElement archerTowerSelector = new MenuElement(this, calculateXOffset(2), 50, CursorImage.MouseState.PLACE_ARCHER_TOWER,
			ArcherTower.PRICE);
	private final MenuElement bomberTowerSelector = new MenuElement(this, calculateXOffset(3), 50, CursorImage.MouseState.PLACE_BOMB_TOWER,
			BombTower.PRICE);
	private final MenuElement sniperTowerSelector = new MenuElement(this, calculateXOffset(4), 50, CursorImage.MouseState.PLACE_SNIPER_TOWER,
			SniperTower.PRICE);
	private final MenuElement deleteToolSelector = new MenuElement(this, calculateXOffset(1), 120, CursorImage.MouseState.DELETE_TOOL);
	private final MenuElement mineFieldSelector = new MenuElement(this, calculateXOffset(2), 120, CursorImage.MouseState.PLACE_MINE_FIELD,
			MineField.PRICE);
	private final MenuElement slimeFieldSelector = new MenuElement(this, calculateXOffset(3), 120, CursorImage.MouseState.PLACE_SLIME_FIELD,
			SlimeField.PRICE);

	public Menu() {
		setImage(BACKGROUND_IMAGE_NAME);
	}

	public void buildMenu() {
		for (MenuElement menuElement : menuElements) {
			if(menuElement.getMouseState() == ((GameWorld) getWorld()).getCursorImage().getMouseState()) {
				menuElement.setActive(true);
			}
			int xCoord = this.getX() - this.getImage().getWidth() / 2 + menuElement.getxOffset();
			int yCoord = this.getY() - this.getImage().getHeight() / 2 + menuElement.getyOffset();
			((GameWorld) getWorld()).addObject(menuElement, xCoord, yCoord);
			menuElement.tryAddPriceTagToWorld();
		}
	}

	public void addToMenuElementsList(MenuElement element) {
		menuElements.add(element);
	}

	public void turnAllMenuElementsIdle() {
		for (MenuElement menuElement : menuElements) {
			menuElement.setActive(false);
		}
	}

	public void removeYourself() {
		for (MenuElement menuElement : menuElements) {
			menuElement.removePriceTagIfExistent();
			((GameWorld) getWorld()).removeObject(menuElement);
		}
		((GameWorld) getWorld()).removeObject(this);
	}

	private int calculateXOffset(int imageNumber) {
		return 30 * imageNumber + MenuElement.BUTTON_IMAGE_SIZE * (imageNumber - 1) + MenuElement.BUTTON_IMAGE_SIZE / 2;
	}

	public MenuElement getMouseSelector() {
		return mouseSelector;
	}

}
