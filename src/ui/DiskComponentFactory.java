package ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DiskComponentFactory {

	private static final Image blackDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/black.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH);
	private static final Image whiteDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/white.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH);
	private static final Image emptyDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/empty.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH);
	private static final Image pssblBlkDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/blackpssbl.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH);
	private static final Image pssblWhtDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/whitepssbl.png").getScaledInstance(22, 22, Image.SCALE_SMOOTH);
	private ImageComponent imgComp;

	protected enum DiskType {

		BLACK,
		WHITE,
		EMPTY,
		PSSBLBLK,
		PSSBLWHT;
	}

	public DiskComponentFactory() {
	}

	public ImageComponent createDisk(DiskType type) {
		switch (type) {
			case BLACK:
				return blackImageComp();
			case WHITE:
				return whiteImageComp();
			case PSSBLBLK:
				return pssblBlkImageComp();
			case PSSBLWHT:
				return pssblWhtImageComp();
			case EMPTY:
				return emptyImageComp();
			default:
				return null;
		}
	}

	private ImageComponent blackImageComp() {
		imgComp = new ImageComponent(blackDiscImg);
		imgComp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				System.out.println("black!");
			}
		});
		return imgComp;
	}

	private ImageComponent whiteImageComp() {
		imgComp = new ImageComponent(whiteDiscImg);
		imgComp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				System.out.println("white!");
			}
		});
		return imgComp;
	}

	private ImageComponent emptyImageComp() {
		imgComp = new ImageComponent(emptyDiscImg);
		imgComp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				System.out.println("empty!");
			}
		});
		return imgComp;
	}

	private ImageComponent pssblBlkImageComp() {
		imgComp = new ImageComponent(pssblBlkDiscImg);
		imgComp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				System.out.println("pssbl black!");
			}
		});
		return imgComp;
	}

	private ImageComponent pssblWhtImageComp() {
		imgComp = new ImageComponent(pssblWhtDiscImg);
		imgComp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				System.out.println("pssbl white!");
			}
		});
		return imgComp;
	}
}
