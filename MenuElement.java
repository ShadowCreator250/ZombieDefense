import greenfoot.GreenfootImage;

public class MenuElement extends Button {

	public static final String IDLE_BUTTON_BACKGROUND_IMAGE_NAME = "menu-element-idle-bg.png";
	public static final String ACTIVE_BUTTON_BACKGROUND_IMAGE_NAME = "menu-element-active-bg.png";
	public static final int BUTTON_IMAGE_SIZE = 40;

	private Menu parentMenu;
	private int xOffset;
	private int yOffset;
	private GameState.MouseState mouseState;

	public MenuElement(Menu parentMenu, int xOffset, int yOffset, GameState.MouseState mouseState) {
		super(generateIdleButtonImage(mouseState), generateActiveButtonImage(mouseState));
		this.parentMenu = parentMenu;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.mouseState = mouseState;
		parentMenu.addToList(this);
	}

	private static GreenfootImage generateIdleButtonImage(GameState.MouseState mouseState) {
		GreenfootImage bg = new GreenfootImage(IDLE_BUTTON_BACKGROUND_IMAGE_NAME);
		GreenfootImage logo = new GreenfootImage(mouseState.getImageName());
		bg.drawImage(logo, 4, 4);
		return bg;
	}

	private static GreenfootImage generateActiveButtonImage(GameState.MouseState mouseState) {
		GreenfootImage bg = new GreenfootImage(ACTIVE_BUTTON_BACKGROUND_IMAGE_NAME);
		GreenfootImage logo = new GreenfootImage(mouseState.getImageName());
		bg.drawImage(logo, 4, 4);
		return bg;
	}

	@Override
	protected void clickAction() {
		toggleMouseState();
	}

	private void toggleMouseState() {
		if(!isActive()) { // turn on this
			parentMenu.turnAllMenuElementsIdle();
			getWorld().getGameState().setMouseState(mouseState);
			setActive(true);
		} else { // turn this off and the mouse one on
			setActive(false);
			getWorld().getGameState().setMouseState(GameState.MouseState.NONE);
			parentMenu.getMouseSelector().setActive(true);
		}
	}

	public int getyOffset() {
		return yOffset;
	}

	public int getxOffset() {
		return xOffset;
	}

	public GameState.MouseState getMouseState() {
		return mouseState;
	}

}
