package utils;

import core.Board;
import java.awt.Point;

/**
 * Trasformations.
 * Translating points to indexes and board notations and reverse.
 *
 * @author c00kiemon5ter
 */
public class Transform {

	public static Point indexToPoint(int index) {
		return new Point(index / Board.BOARD_LENGTH, index % Board.BOARD_LENGTH);
	}

	public static Point indexToPoint(int index, int length) {
		return new Point(index / length, index % length);
	}

	public static int pointToIndex(Point point) {
		return Board.BOARD_LENGTH * point.x + point.y;
	}

	public static int pointToIndex(Point point, int length) {
		return length * point.x + point.y;
	}

	public static String toBoardNotation(Point coordinate) {
		return String.format("%d%c", coordinate.x + 1, coordinate.y + 65);
	}

	public static Point fromBoardNotation(String coordinate) {
		return new Point(coordinate.charAt(0) - 1, coordinate.charAt(1) - 65);
	}
}
