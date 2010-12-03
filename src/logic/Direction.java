package logic;

import java.awt.Point;

/**
 * Direction navigates correctly to the next point in the board
 *
 * @author c00kiemon5ter
 */
public enum Direction {
	/* ↑ */ NORTH(-1, 0),
	/* ↓ */ SOUTH(+1, 0),
	/* ← */ WEST(0, -1),
	/* → */ EAST(0, +1),
	/* ↖ */ NORTHWEST(-1, -1),
	/* ↘ */ SOUTHEAST(+1, +1),
	/* ↙ */ SOUTHWEST(+1, -1),
	/* ↗ */ NORTHEAST(-1, +1);
	private int rowstep;
	private int colstep;

	private Direction(int rowstep, int colstep) {
		this.rowstep = rowstep;
		this.colstep = colstep;
	}

	public Point next(Point point) {
		return new Point(point.x + rowstep, point.y + colstep);
	}
}
