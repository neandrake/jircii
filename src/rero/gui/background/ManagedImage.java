/** not used in jirc anymore, at least I don't think it is... */
package rero.gui.background;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class ManagedImage {
	protected VolatileImage hwImage;
	protected BufferedImage swImage;
	protected Component source;

	private void createImage() {
		hwImage = source.getGraphicsConfiguration().createCompatibleVolatileImage(getWidth(), getHeight());
	}

	private void renderImage() {
		Graphics2D g = hwImage.createGraphics();
		g.drawImage(swImage, 0, 0, null);
		g.dispose();
	}

	public ManagedImage(BufferedImage _image, Component c) {
		source = c;
		swImage = _image;

		createImage();
		renderImage();
	}

	public int getWidth() {
		return swImage.getWidth();
	}

	public int getHeight() {
		return swImage.getHeight();
	}

	public Image getDrawableImage() {
		int state = hwImage.validate(source.getGraphicsConfiguration());
		if (state == VolatileImage.IMAGE_RESTORED) {
			renderImage();
		} else if (state == VolatileImage.IMAGE_INCOMPATIBLE) {
			createImage();
			renderImage();
		}

		if (hwImage.contentsLost()) {
			System.out.println("Resorting to software image...  worthless peice of crap");
			return swImage;
		}

		System.out.println("hwImage: " + hwImage.getCapabilities());

		return hwImage;
	}
}
