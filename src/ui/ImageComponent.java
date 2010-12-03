package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.JComponent;

/**
 * An image container, given images component attributes.
 *
 */
public class ImageComponent extends JComponent {

	Image image;
	Dimension size;

	public ImageComponent(Image image) {
		this.image = image;
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(image, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
		}
		this.size = new Dimension(image.getWidth(null), image.getHeight(null));
		this.setSize(size);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}
}
