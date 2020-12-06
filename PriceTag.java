import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;

public class PriceTag extends Actor {

	public static final String COIN_IMAGE_NAME = "coin.png";
	private static final Color transparent = new Color(0, 0, 0, 0);

	public PriceTag(int price) {
		setImage(buildImage(price));
	}

	private GreenfootImage buildImage(int price) {
		GreenfootImage coinImg = new GreenfootImage(COIN_IMAGE_NAME);
		GreenfootImage priceImg = new GreenfootImage(" " + price, 21, Color.WHITE, transparent, Color.WHITE);
		GreenfootImage combined = new GreenfootImage(coinImg.getWidth() + priceImg.getWidth(), Math.max(coinImg.getHeight(), priceImg.getHeight()));
		combined.drawImage(coinImg, 0, combined.getHeight() / 2 - coinImg.getHeight() / 2);
		combined.drawImage(priceImg, coinImg.getWidth(), combined.getHeight() / 2 - priceImg.getHeight() / 2);
		return combined;
	}
}
