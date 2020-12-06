public class MenuExpendButton extends Button {

	public static final String IDLE_IMAGE_NAME = "menu-expand-button.png";
	public static final String ACTIVE_IMAGE_NAME = "menu-expand-button-active.png";

	public MenuExpendButton() {
		super(IDLE_IMAGE_NAME, ACTIVE_IMAGE_NAME);
	}

	@Override
	protected void clickAction() {
		toggleMenu();
	}

	private void toggleMenu() {
		setActive(!isActive());
		Menu menu = new Menu();
		if(isActive()) { // expand menu
			setLocation(getX(), getY() - menu.getImage().getHeight());
			getWorld().addObject(menu, getWorld().getWidth() / 2, getWorld().getHeight() - menu.getImage().getHeight() / 2 - 2);
			menu.buildMenu();
		} else { // close menu
			menu = getWorld().getObjectsAt(getX(), getY() + menu.getImage().getHeight(), Menu.class).get(0);
			setLocation(getX(), getWorld().getHeight() - (getImage().getHeight() / 2 + 4));
			menu.removeYourself();
		}
	}

}
