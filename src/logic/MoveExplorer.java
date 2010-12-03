package logic;

import core.Board;
import core.SquareState;
import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Explores moves.
 */
public class MoveExplorer {

	/**
	 * Given a starting position - seed - checks if there's possibility of
	 * finding a desired result looking towards some {@code Direction}.
	 *
	 * @param board - the board to search in
	 * @param seed - where we start searching from
	 * @param direction - which direction should we search
	 * @return if search in this direction is likely to have interesting findings
	 */
	private static boolean shouldSearch(final Board board, final Point seed, final Direction direction) {
		Point nextPoint = direction.next(seed);
		return pointIsValid(nextPoint) ? board.getSquareState(nextPoint)
						 == board.getSquareState(seed).opposite() : false;
	}

	/**
	 * Check if the given point a valid point on the board.
	 * Valid means within board limits
	 *
	 * @param point - the point to check
	 * @return if the point is valid
	 */
	private static boolean pointIsValid(Point point) {
		return point.x >= 0 && point.x < Board.BOARD_LENGTH
		       && point.y >= 0 && point.y < Board.BOARD_WIDTH;
	}

	/**
	 * Find all possible points where a player with the given state can move next
	 *
	 * @param board - the board to look into
	 * @param state - the player's color
	 * @return the player's possible moves
	 */
	public static Set<Point> explore(final Board board, final SquareState state) {
		Set<Point> possibleMoves = new HashSet<Point>();
		Set<Point> statePoints = board.getSquares(state);
		for (Point seed : statePoints) {
			for (Direction direction : Direction.values()) {
				if (shouldSearch(board, seed, direction)) {
					Point nextPoint = direction.next(seed);
					nextPoint = direction.next(nextPoint);
					while (pointIsValid(nextPoint)) {
						if (board.getSquareState(nextPoint) == state) {
							break;
						} else if (board.getSquareState(nextPoint) == SquareState.EMPTY) {
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

	/**
	 * Given a starting position - seed - find all points on the board
	 * that must be filled or have their color/state changed.
	 *
	 * @param board - the board to look into
	 * @param seed - the starting position
	 * @return the points that need to change state
	 */
	public static Set<Point> squaresToFill(final Board board, final Point seed) {
		Set<Point> filledlist = new HashSet<Point>();
		SquareState seedState = board.getSquareState(seed);
		for (Direction direction : Direction.values()) {
			if (shouldSearch(board, seed, direction)) {
				Point nextPoint = direction.next(seed);
				LinkedList<Point> templist = new LinkedList<Point>();
				while (pointIsValid(nextPoint)) {
					SquareState nextState = board.getSquareState(nextPoint);
					if (nextState == seedState.opposite()) {
						templist.add(nextPoint);
					} else if (nextState == seedState) {
						filledlist.addAll(templist);
						break;
					} else if (nextState == SquareState.EMPTY) {
						break;
					}
					nextPoint = direction.next(nextPoint);
				}
			}
		}
		return filledlist;
	}
}
