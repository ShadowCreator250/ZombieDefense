import greenfoot.GreenfootImage;

public class PauseResumeButton extends Button {

	public PauseResumeButton() {
		super();
	}

	public PauseResumeButton(String idleImageName, String activeImageName) {
		this(new GreenfootImage(idleImageName), new GreenfootImage(activeImageName));
	}

	public PauseResumeButton(GreenfootImage idleImage, GreenfootImage activeImage) {
		super(idleImage, activeImage);
	}

	@Override
	protected void clickAction() {
		pauseResumeToggle();
	}

	public void updatePauseResumeButten() {
		if(getWorld().isPaused()) {
			setImage(getIdleImage());
			setActive(false);
		} else {
			setImage(getActiveImage());
			setActive(true);
		}
	}

	public void pauseResumeToggle() {
		GameWorld world = getWorld();
		if(isActive()) { // pause game
			world.pause();
			setImage(getIdleImage());
		} else { // resume game
			world.resume();
			setImage(getActiveImage());
		}
		setActive(!isActive());
	}

}
