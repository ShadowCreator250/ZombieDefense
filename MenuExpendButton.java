import greenfoot.GreenfootImage;

public class MenuExpendButton extends Button {

	public MenuExpendButton() {
		super();
	}

	public MenuExpendButton(String idleImageName, String activeImageName) {
		super(idleImageName, activeImageName);
	}

	public MenuExpendButton(GreenfootImage idleImage, GreenfootImage activeImage) {
		super(idleImage, activeImage);
	}

	@Override
	protected void clickAction() {
		toggleImage();
		toggleMenu();
	}

	private void toggleMenu() {
		// TODO: Build menu and place it
		setActive(!isActive());
	}

}
