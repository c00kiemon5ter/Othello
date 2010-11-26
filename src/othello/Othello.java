/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package othello;

import core.Board;
import core.DiskState;
import core.MoveExplorer;
import core.Player;
import java.awt.Point;
import java.util.Set;

public class Othello {

	public static void main(String[] args) {
		String separator = "-----------------------------------";

		/* print the o-board */
		Board board = Board.getInstance();
		System.out.println(board.toString());

		MoveExplorer blkExplr;
		blkExplr = MoveExplorer.BLACKEXPLORER;
		Set<Point> points;

		/* mark next possible moves */
		points = blkExplr.explore();
		for (Point possiblePoint : points) {
			board.getDisk(possiblePoint).setState(DiskState.PSSBL);
		}
		System.out.println(board.toString());

		/* Players */
		Player.BLACK.setScore(board.getDiskPoints(DiskState.BLACK).size());
		Player.WHITE.setScore(board.getDiskPoints(DiskState.WHITE).size());
		System.out.println(Player.BLACK.toString());
		System.out.println(Player.WHITE.toString());
		System.out.println(separator);

		/* clear marks and add white piece */
		for (Point possiblePoint : points) {
			board.getDisk(possiblePoint).setState(DiskState.EMPTY);
		}
		board.getDisk(new Point(2, 3)).setState(DiskState.WHITE);
		System.out.println(board.toString());

		/* mark again */
		points = blkExplr.explore();
		for (Point possiblePoint : points) {
			board.getDisk(possiblePoint).setState(DiskState.PSSBL);
		}
		System.out.println(board.toString());

		/* Players */
		Player.BLACK.setScore(board.getDiskPoints(DiskState.BLACK).size());
		Player.WHITE.setScore(board.getDiskPoints(DiskState.WHITE).size());
		System.out.println(Player.BLACK.toString());
		System.out.println(Player.WHITE.toString());
		System.out.println(separator);
	}
}
