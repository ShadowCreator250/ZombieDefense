/**
 * A special button that is used either to pause or to resume the game.
 */
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

	/**
	 * Sets the correct image for pausing or resuming.
	 */
	public void updatePauseResumeButton() {
		if(getWorld().isPaused()) {
			setImage(getIdleImage());
			setActive(false);
		} else {
			setImage(getActiveImage());
			setActive(true);
		}
	}

	/**
	 * Pauses or resumes the game when pressing the button
	 */
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
