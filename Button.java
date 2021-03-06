import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

/**
 * Defines the plan for the buttons, but needs to be specialized as one of the
 * subclasses.
 */
public abstract class Button extends Actor {

	public static final GreenfootImage TEMP_IDLE_IMG = generateTempIdleImage(32);
	public static final GreenfootImage TEMP_ACTIVE_IMG = generateTempActiveImage(32);

	private boolean active;
	private GreenfootImage idleImage;
	private GreenfootImage activeImage;

	public Button() {
		this(TEMP_IDLE_IMG, TEMP_ACTIVE_IMG);
	}

	public Button(String idleImageName, String activeImageName) {
		this(new GreenfootImage(idleImageName), new GreenfootImage(activeImageName));
	}

	public Button(GreenfootImage idleImage, GreenfootImage activeImage) {
		this.idleImage = idleImage;
		this.activeImage = activeImage;
		setImage(idleImage);
	}

	/**
	 * Presses the button when clicking with the mouse on it.
	 */
	@Override
	public void act() {
		MouseInfo mouse = Greenfoot.getMouseInfo();
		if(Greenfoot.mouseClicked(this) && mouse.getButton() == 1 && mouse != null) {
			clickAction();
		}
	}

	/**
	 * what happens when the button is clicked
	 */
	protected abstract void clickAction();

	/**
	 * updates the image accordingly to the current state
	 */
	public void updateImage() {
		if(active) {
			setImage(getActiveImage());
		} else {
			setImage(getIdleImage());
		}
	}

	/**
	 * Generates the image for an idle button.
	 * 
	 * @param size - the size of the button
	 * @return the image for the button
	 */
	public static GreenfootImage generateTempIdleImage(int size) {
		GreenfootImage img = new GreenfootImage(size, size);
		img.setColor(Color.BLACK);
		img.fillOval(1, 1, img.getWidth() - 2, img.getHeight() - 2);
		img.setColor(Color.RED);
		img.drawOval(1, 1, img.getWidth() - 2, img.getHeight() - 2);
		return img;
	}

	/**
	 * Generates the image for an active button.
	 * 
	 * @param size - the size of the button
	 * @return the image for the button
	 */
	public static GreenfootImage generateTempActiveImage(int size) {
		GreenfootImage img = new GreenfootImage(size, size);
		img.setColor(Color.WHITE);
		img.fillOval(1, 1, img.getWidth() - 2, img.getHeight() - 2);
		img.setColor(Color.GREEN);
		img.drawOval(1, 1, img.getWidth() - 2, img.getHeight() - 2);
		return img;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		updateImage();
	}

	public GreenfootImage getIdleImage() {
		return idleImage;
	}

	public GreenfootImage getActiveImage() {
		return activeImage;
	}

	@Override
	public GameWorld getWorld() {
		return (GameWorld) super.getWorld();
	}

}
