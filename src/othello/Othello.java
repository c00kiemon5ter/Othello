/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package othello;

import core.Board;
import core.DiskState;
import core.MoveExplorer;
import core.Player;
import java.awt.Point;

public class Othello {

	public static void main(String[] args) {

		/* print the o-board */
		Board board = Board.getInstance();
		System.out.println(board.toString());

//		board.getDisk(new Point(2, 3)).setState(DiskState.PSSBL);
//		board.getDisk(new Point(3, 2)).setState(DiskState.PSSBL);
//		board.getDisk(new Point(4, 5)).setState(DiskState.PSSBL);
//		board.getDisk(new Point(5, 4)).setState(DiskState.PSSBL);

		/* mark valid moves */
		MoveExplorer blkExplr = MoveExplorer.BLACKEXPLORER;
		for (Point possiblePoint : blkExplr.explore()) {
			board.getDisk(possiblePoint).setState(DiskState.PSSBL);
		}
		System.out.println(board.toString());

		/* Players */
		System.out.println(Player.BLACK.toString());
		System.out.println(Player.WHITE.toString());
	}
}
