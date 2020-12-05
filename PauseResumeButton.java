public class PauseResumeButton extends Button {

	public static final String RESUME_BUTTON_IMAGE_NAME = "resume-button.png";
	public static final String PAUSE_BUTTON_IMAGE_NAME = "pause-button.png";

	public PauseResumeButton() {
		super(RESUME_BUTTON_IMAGE_NAME, PAUSE_BUTTON_IMAGE_NAME);
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
