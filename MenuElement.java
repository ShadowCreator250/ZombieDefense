import greenfoot.GreenfootImage;

public class MenuElement extends Button {

	public static final String IDLE_BUTTON_BACKGROUND_IMAGE_NAME = "menu-element-idle-bg.png";
	public static final String ACTIVE_BUTTON_BACKGROUND_IMAGE_NAME = "menu-element-active-bg.png";
	public static final int BUTTON_IMAGE_SIZE = 40;

	private Menu parentMenu;
	private int xOffset;
	private int yOffset;
	private CursorImage.MouseState mouseState;
	private PriceTag priceTag;
	private int price;

	public MenuElement(Menu parentMenu, int xOffset, int yOffset, CursorImage.MouseState mouseState) {
		this(parentMenu, xOffset, yOffset, mouseState, 0);
	}

	public MenuElement(Menu parentMenu, int xOffset, int yOffset, CursorImage.MouseState mouseState, int price) {
		super(generateIdleButtonImage(mouseState), generateActiveButtonImage(mouseState));
		this.parentMenu = parentMenu;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.mouseState = mouseState;
		this.price = price;
		parentMenu.addToMenuElementsList(this);
	}

	public void tryAddPriceTagToWorld() {
		if(price > 0) {
			this.priceTag = buildPriceTag();
		}
	}

	private PriceTag buildPriceTag() {
		PriceTag tag = new PriceTag(price);
		getWorld().addObject(tag, getX(), getY() + getImage().getHeight() / 2 + 11);
		return tag;
	}

	public void removePriceTagIfExistent() {
		if(priceTag != null) {
			getWorld().removeObject(priceTag);
		}
	}

	private static GreenfootImage generateIdleButtonImage(CursorImage.MouseState mouseState) {
		GreenfootImage bg = new GreenfootImage(IDLE_BUTTON_BACKGROUND_IMAGE_NAME);
		GreenfootImage logo = new GreenfootImage(mouseState.getImageName());
		bg.drawImage(logo, 4, 4);
		return bg;
	}

	private static GreenfootImage generateActiveButtonImage(CursorImage.MouseState mouseState) {
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
			getWorld().getCursorImage().setMouseState(mouseState);
			setActive(true);
		} else { // turn this off and the mouse one on
			setActive(false);
			getWorld().getCursorImage().setMouseState(CursorImage.MouseState.NONE);
			parentMenu.getMouseSelector().setActive(true);
		}
	}

	public int getyOffset() {
		return yOffset;
	}

	public int getxOffset() {
		return xOffset;
	}

	public CursorImage.MouseState getMouseState() {
		return mouseState;
	}

}
