import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;

/**
 * Displays the cost of the towers and obstacles in the menu.
 */
public class PriceTag extends Actor {

	public static final String COIN_IMAGE_NAME = "coin.png";
	private static final Color transparent = new Color(0, 0, 0, 0);

	/**
	 * Creates an price tag object and sets its image.
	 * 
	 * @param price - the price of the tower/obstacle
	 */
	public PriceTag(int price) {
		setImage(buildImage(price));
	}

	/**
	 * Builds the image that is used as the price tag.
	 * 
	 * @param price - the price of the tower/obstacle
	 * 
	 * @return the image of the price tag
	 */
	private GreenfootImage buildImage(int price) {
		GreenfootImage coinImg = new GreenfootImage(COIN_IMAGE_NAME);
		GreenfootImage priceImg = new GreenfootImage(" " + price, 21, Color.WHITE, transparent, Color.WHITE);
		GreenfootImage combined = new GreenfootImage(coinImg.getWidth() + priceImg.getWidth(), Math.max(coinImg.getHeight(), priceImg.getHeight()));
		combined.drawImage(coinImg, 0, combined.getHeight() / 2 - coinImg.getHeight() / 2);
		combined.drawImage(priceImg, coinImg.getWidth(), combined.getHeight() / 2 - priceImg.getHeight() / 2);
		return combined;
	}
}
