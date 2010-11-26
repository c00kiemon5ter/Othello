package core;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public enum MoveExplorer {

	BLACKEXPLORER(DiskState.BLACK),
	WHITEEXPLORER(DiskState.WHITE);
	private Board board = Board.getInstance();
	private DiskState color;

	MoveExplorer(DiskState color) {
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
