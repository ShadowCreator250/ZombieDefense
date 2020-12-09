import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

/**
 * Supports the players UX by displaying what he currently has selected the
 * mouse to do
 */
public class CursorImage extends Actor {

	public static final String MOUSE_CURSER_IMAGE_NAME = "mouse-button.png";
	public static final String ARCHER_TOWER_LOGO_IMAGE_NAME = "archer-tower-logo.png";
	public static final String BOMBER_TOWER_LOGO_IMAGE_NAME = "bomb-tower-logo.png";
	public static final String SNIPER_TOWER_LOGO_IMAGE_NAME = "sniper-tower-logo.png";
	public static final String MINE_FIELD_LOGO_IMAGE_NAME = "land-mine-logo.png";
	public static final String SLIME_FIELD_LOGO_IMAGE_NAME = "slime-ball-logo.png";
	public static final String DELETE_TOOL_IMAGE_NAME = "delete-tool-logo.png";
	private static final GreenfootImage NONE_MOUSE_IMAGE = new GreenfootImage(2, 2);

	/**
	 * in which mode the mouse currently is (Placing, Removing, None)
	 */
	public enum MouseState {
		NONE(CursorImage.MOUSE_CURSER_IMAGE_NAME, 0), PLACE_ARCHER_TOWER(CursorImage.ARCHER_TOWER_LOGO_IMAGE_NAME, ArcherTower.PRICE),
		PLACE_BOMB_TOWER(CursorImage.BOMBER_TOWER_LOGO_IMAGE_NAME, BombTower.PRICE),
		PLACE_SNIPER_TOWER(CursorImage.SNIPER_TOWER_LOGO_IMAGE_NAME, SniperTower.PRICE),
		PLACE_MINE_FIELD(CursorImage.MINE_FIELD_LOGO_IMAGE_NAME, MineField.PRICE),
		PLACE_SLIME_FIELD(CursorImage.SLIME_FIELD_LOGO_IMAGE_NAME, SlimeField.PRICE), DELETE_TOOL(CursorImage.DELETE_TOOL_IMAGE_NAME, 0);

		private String imageName;
		private int toolUseCost;

		MouseState(String imageName, int toolUseCost) {
			this.imageName = imageName;
			this.toolUseCost = toolUseCost;
		}

		public String getImageName() {
			return imageName;
		}

		public int getToolUseCost() {
			return toolUseCost;
		}
	}

	private MouseState mouseState;
	private MouseInfo mouse = Greenfoot.getMouseInfo();

	public CursorImage() {
		this.setMouseState(MouseState.NONE);
	}

	@Override
	public void act() {
		mouse = Greenfoot.getMouseInfo();
		if(mouse != null && mouseState != MouseState.NONE) {
			if(getImage().equals(NONE_MOUSE_IMAGE)) {
				setMouseState(mouseState); // replaces image with right image
			}
			if(isMouseStateImageInsideWorld()) {
				setLocation(calcMouseStateImageLocationX(false), calcMouseStateImageLocationY(false));
			} else {
				setLocation(calcMouseStateImageLocationX(true), calcMouseStateImageLocationY(true));
			}
		} else {
			if(getX() != 0 && getY() != 0) {
				setImage(NONE_MOUSE_IMAGE);
				setLocation(0, 0);
			}
		}
	}

	private boolean isMouseStateImageInsideWorld() {
		boolean result = false;
		result = calcMouseStateImageLocationX(false) < getWorld().getWidth();
		result &= calcMouseStateImageLocationY(false) < getWorld().getHeight();
		return result;
	}

	private int calcMouseStateImageLocationX(boolean above) {
		return mouse.getX() + getFactor(above) * (getImage().getWidth() / 2 + 2);
	}

	private int calcMouseStateImageLocationY(boolean above) {
		return mouse.getY() + getFactor(above) * (getImage().getHeight() / 2 + 2);
	}

	private int getFactor(boolean above) {
		return above ? -1 : 1;
	}

	public MouseState getMouseState() {
		return mouseState;
	}

	public void setMouseState(MouseState mouseState) {
		this.mouseState = mouseState;
		if(mouseState == MouseState.NONE) {
			setImage(NONE_MOUSE_IMAGE);
		} else {
			setImage(getMouseStateImageName());
		}
	}

	public String getMouseStateImageName() {
		return getMouseState().getImageName();
	}

}
