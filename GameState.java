import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class GameState extends Actor {

	// TODO: place mines/slime, remove tool
	public static final String MOUSE_CURSER_IMAGE_NAME = "mouse-button.png";
	public static final String ARCHER_TOWER_LOGO_IMAGE_NAME = "archer-tower-logo.png";
	public static final String BOMBER_TOWER_LOGO_IMAGE_NAME = "bomb-tower-logo.png";
	public static final String SNIPER_TOWER_LOGO_IMAGE_NAME = "sniper-tower-logo.png";
	public static final String MINE_FIELD_LOGO_IMAGE_NAME = "";
	public static final String SLIME_FIELD_LOGO_IMAGE_NAME = "";

	public enum MouseState {
		NONE(GameState.MOUSE_CURSER_IMAGE_NAME), PLACE_ARCHER_TOWER(GameState.ARCHER_TOWER_LOGO_IMAGE_NAME),
		PLACE_BOMB_TOWER(GameState.BOMBER_TOWER_LOGO_IMAGE_NAME), PLACE_SNIPER_TOWER(GameState.SNIPER_TOWER_LOGO_IMAGE_NAME),
		PLACE_MINE_FIELD(GameState.MINE_FIELD_LOGO_IMAGE_NAME), PLACE_SLIME_FIELD(GameState.SLIME_FIELD_LOGO_IMAGE_NAME);

		private String imageName;

		MouseState(String imageName) {
			this.imageName = imageName;
		}

		public String getImageName() {
			return imageName;
		}
	}

	private MouseState mouseState;

	public GameState() {
		this.setMouseState(MouseState.NONE);
	}

	@Override
	public void act() {
		// TODO: position mouse element
	}

	public MouseState getMouseState() {
		return mouseState;
	}

	public void setMouseState(MouseState mouseState) {
		this.mouseState = mouseState;
		if(mouseState == MouseState.NONE) {
			setImage(new GreenfootImage(2, 2));
		} else {
			setImage(getMouseStateImageName());
		}
	}

	public String getMouseStateImageName() {
		return getMouseState().getImageName();
	}

}
