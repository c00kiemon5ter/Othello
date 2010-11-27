package ui;

import java.awt.Image;
import java.awt.Toolkit;

public class DiskComponentFactory {

	private static final Image blackDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/black.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH);
	private static final Image whiteDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/white.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH);
	private static final Image emptyDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/empty.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH);
	private static final Image pssblBlkDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/blackpssbl.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH);
	private static final Image pssblWhtDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/whitepssbl.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH);

	public enum DiskType {

		BLACK,
		WHITE,
		EMPTY,
		PSSBLBLK,
		PSSBLWHT;
	}

	public ImageComponent createDisk(DiskType type) {
		switch (type) {
			case BLACK:
				return new ImageComponent(blackDiscImg);
			case WHITE:
				return new ImageComponent(whiteDiscImg);
			case PSSBLBLK:
				return new ImageComponent(pssblBlkDiscImg);
			case PSSBLWHT:
				return new ImageComponent(pssblWhtDiscImg);
			case EMPTY:
				return new ImageComponent(emptyDiscImg);
			default:
				return null;
		}
	}
}
