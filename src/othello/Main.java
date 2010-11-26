/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package othello;

import core.Board;
import core.DiskState;
import java.awt.Point;

public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		Board b = Board.getInstance();
		b.getDisk(new Point(2, 3)).setState(DiskState.PSSBL);
		b.getDisk(new Point(3, 2)).setState(DiskState.PSSBL);
		b.getDisk(new Point(4, 5)).setState(DiskState.PSSBL);
		b.getDisk(new Point(5, 4)).setState(DiskState.PSSBL);
		System.out.println(b.toString());
	}
}
