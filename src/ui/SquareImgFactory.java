package ui;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * Square representation. Squares are of type:
 * <ul>
 * <li>Black - the square is black
 * <li>White - the square is white
 * <li>Empty - the square is empty
 * <li>PssblBlk - the square is a possible next move for the black player
 * <li>PssblWht - the square is a possible next move for the white player
 * </ul>
 * This class provides a square factory for easy building of any type of square.
 * 
 * @author c00kiemon5ter
 */
public class SquareImgFactory {

	private static final int imagesize = 22;
	private static final Image blackDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/black.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);
	private static final Image whiteDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/white.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);
	private static final Image emptyDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/empty.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);
	private static final Image pssblBlkDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/blackpssbl.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);
	private static final Image pssblWhtDiscImg = Toolkit.getDefaultToolkit().getImage("src/ui/images/whitepssbl.png").getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);

	public enum SquareType {

		BLACK,
		WHITE,
		EMPTY,
		PSSBLBLK,
		PSSBLWHT;
	}

	public static ImageComponent buildSquare(SquareType type) {
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
