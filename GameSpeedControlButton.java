import java.util.ArrayList;
import java.util.List;

import greenfoot.GreenfootImage;

/**
 * A special button that is used to change the game speed.
 */
public class GameSpeedControlButton extends Button {

	public static final String[] IDLE_BUTTON_IMAGE_NAMES = { "fastforward-1-button.png", "fastforward-2-button.png" };
	public static final String[] ACTIVE_BUTTON_IMAGE_NAMES = { "fastforward-1-button-active.png", "fastforward-2-button-active.png" };

	private static List<GameSpeedControlButton> speedController = new ArrayList<>();
	private int speed;

	public GameSpeedControlButton(int speed, String idleImageName, String activeImageName) {
		super(new GreenfootImage(idleImageName), new GreenfootImage(activeImageName));
		setSpeed(speed);
		speedController.add(this);
	}

	@Override
	public void clickAction() {
		speedToggle();
	}

	/**
	 * Changes the game speed in different ways when needed.
	 */
	private void speedToggle() {
		GameWorld world = getWorld();
		if(world.isPaused()) { // resume game with custom speed
			world.getPauseResumeButton().pauseResumeToggle();
		}

		if(!world.isDefaultSpeed() && !isActive()) { // switch from another custom speed to this
			for (GameSpeedControlButton gameExecutionController : speedController) {
				gameExecutionController.turnOffSpeedButton();
			}
			turnOnSpeedButton();
		} else if(!world.isDefaultSpeed() && isActive()) { // turn of this custom speed and return to default speed
			turnOffSpeedButton();
			world.setExecutionSpeed(GameWorld.DEFAULT_SPEED);
		} else if(world.isDefaultSpeed() && !isActive()) {
			turnOnSpeedButton();
		}
	}

	private void turnOffSpeedButton() {
		setActive(false);
	}

	private void turnOnSpeedButton() {
		setActive(true);
		getWorld().setExecutionSpeed(speed);
	}

	private void setSpeed(int speed) {
		if(speed <= 0) {
			this.speed = 1;
		} else if(speed > 100) {
			this.speed = 100;
		} else {
			this.speed = speed;
		}
	}

}
