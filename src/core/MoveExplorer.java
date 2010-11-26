package core;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class MoveExplorer {

	private Board board;
	private DiskState color;

	public MoveExplorer(Board board, DiskState color) {
		this.board = board;
		this.color = color;
	}

	public Set<Point> explore() {
		Set<Point> availablemoves = new HashSet<Point>();
		Set<Point> diskpoints = board.getDiskPoints(color);
		for (Point point : diskpoints) {
			for (Direction direction : Direction.values()) {
				availablemoves.addAll(searchMoves(point, direction));
			}
		}
		return availablemoves;
	}

	private Set<Point> searchMoves(Point seed, Direction direction) {
		Set<Point> points = new HashSet<Point>();
		// TODO: search this direction for available moves
		return points;
	}
}
