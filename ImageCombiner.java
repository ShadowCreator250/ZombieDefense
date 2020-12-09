import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import greenfoot.GreenfootImage;

/**
 * combines two images from storage to one
 */
public class ImageCombiner {

	private File basePath;
	private String bgImgName;
	private String fgImgName;

	public ImageCombiner(String bgImgName, String fgImgName) {
		this(new File("./images/"), bgImgName, fgImgName);
	}

	/**
	 * @param basePath  path where both images are found
	 * @param bgImgName background image name
	 * @param fgImgName foreground image name
	 */
	public ImageCombiner(File basePath, String bgImgName, String fgImgName) {
		this.basePath = basePath;
		this.bgImgName = bgImgName;
		this.fgImgName = fgImgName;
	}

	/**
	 * combines two images into a {@link BufferedImage}
	 * 
	 * @return the BufferedImage
	 */
	public BufferedImage combine() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(basePath, bgImgName));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedImage overlay = null;
		try {
			overlay = ImageIO.read(new File(basePath, fgImgName));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if(image.getWidth() != overlay.getWidth() && image.getHeight() != overlay.getHeight()) {
			return null;
		}

		// create the new image
		BufferedImage combined = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.drawImage(overlay, 0, 0, null);

		g.dispose();

//		// Save as new image
//		try {
//			ImageIO.write(combined, "PNG", new File(basePath, "combined.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		return combined;

	}

	/**
	 * combines two images into one GreenfootImage image using Javas
	 * {@link BufferedImage}
	 * 
	 * (could be done with only GreenfootImage but I was too lazy and was curious
	 * how to do it with only Java)
	 * 
	 * @return
	 */
	public GreenfootImage combineToGFImg() {
		BufferedImage bufImage = this.combine();

		GreenfootImage gImage = new GreenfootImage(bufImage.getWidth(), bufImage.getHeight());
		BufferedImage gBufImg = gImage.getAwtImage();
		Graphics2D graphics = (Graphics2D) gBufImg.getGraphics();
		graphics.drawImage(bufImage, null, 0, 0);
		return gImage;
	}

}
