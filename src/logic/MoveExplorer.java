package logic;

import core.Board;
import core.DiskState;
import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MoveExplorer {

	/**
	 *
	 * @param board - the board to search in
	 * @param seed - where we start searching from
	 * @param direction - which direction should we search
	 * @return if search in this direction is likely to have interesting findings
	 */
	private static boolean shouldSearch(final Board board, final Point seed, final Direction direction) {
		Point nextPoint = direction.next(seed);
		return pointIsValid(nextPoint)
		       ? board.getDisk(nextPoint).getColor()
			 == board.getDisk(seed).getColor().opposite()
		       : false;
	}

	private static boolean pointIsValid(Point point) {
		return point.x >= 0 && point.x < Board.BOARD_LENGTH
		       && point.y >= 0 && point.y < Board.BOARD_WIDTH;
	}

	public static Set<Point> explore(final Board board, final DiskState color) {
		Set<Point> possibleMoves = new HashSet<Point>();
		Set<Point> diskpoints = board.getDiskPoints(color);
		for (Point seed : diskpoints) {
			for (Direction direction : Direction.values()) {
				if (shouldSearch(board, seed, direction)) {
					Point nextPoint = direction.next(seed);
					nextPoint = direction.next(nextPoint);
					while (pointIsValid(nextPoint)) {
						if (board.getDisk(nextPoint).getColor() == color) {
							break;
						} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
							possibleMoves.add(nextPoint);
							break;
						}
						nextPoint = direction.next(nextPoint);
					}
				}
			}
		}
		return possibleMoves;
	}

	public static Set<Point> pointsToFill(final Board board, final Point seed) {
		Set<Point> filledlist = new HashSet<Point>();
		DiskState seedState = board.getDisk(seed).getColor();
		for (Direction direction : Direction.values()) {
			if (shouldSearch(board, seed, direction)) {
				Point nextPoint = direction.next(seed);
				LinkedList<Point> templist = new LinkedList<Point>();
				while (pointIsValid(nextPoint)) {
					DiskState nextState = board.getDisk(nextPoint).getState();
					if (nextState == seedState.opposite()) {
						templist.add(nextPoint);
					} else if (nextState == seedState) {
						filledlist.addAll(templist);
						break;
					} else if (nextState == DiskState.EMPTY) {
						break;
					}
					nextPoint = direction.next(nextPoint);
				}
			}
		}
		return filledlist;
	}
}
