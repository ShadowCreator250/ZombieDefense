import java.util.ArrayList;
import java.util.List;

import greenfoot.GreenfootImage;

public class GameSpeedControlButton extends Button {

	private static List<GameSpeedControlButton> speedController = new ArrayList<>();
	private int speed;

	public GameSpeedControlButton(int speed) {
		this(speed, TEMP_IDLE_IMG, TEMP_ACTIVE_IMG);
	}

	public GameSpeedControlButton(int speed, String idleImageName, String activeImageName) {
		this(speed, new GreenfootImage(idleImageName), new GreenfootImage(activeImageName));
	}

	public GameSpeedControlButton(int speed, GreenfootImage idleImage, GreenfootImage activeImage) {
		super(idleImage, activeImage);
		setSpeed(speed);
		speedController.add(this);
	}

	@Override
	public void clickAction() {
		speedToggle();
	}

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
		setImage(getIdleImage());
	}

	private void turnOnSpeedButton() {
		setActive(true);
		getWorld().setExecutionSpeed(speed);
		setImage(getActiveImage());
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
