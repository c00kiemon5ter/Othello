/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package core;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class Board {

	public static final int BOARD_SIZE = 8;
	private Disk[][] disks;

	public static Board getInstance() {
		return BoardHolder.INSTANCE;
	}

	Set<Point> getDiskPoints(DiskState color) {
		Set<Point> points = new HashSet<Point>();
		Point point = new Point();
		for (point.x = 0; point.x < disks.length; point.x++) {
			for (point.y = 0; point.y < disks[point.x].length; point.y++) {
				if (disks[point.x][point.y].getState() == color) {
					points.add(new Point(point));
				}
			}
		}
		return points;
	}

	/* singleton - there is only one board */
	private static class BoardHolder {

		private static final Board INSTANCE = new Board();
	}

	private Board() {
		this(BOARD_SIZE);
	}

	/**
	 * game starts with four pieces, two black, two white, placed diagonally
	 *
	 * @param boardSize the size of the board
	 */
	private Board(int boardSize) {
		disks = new Disk[boardSize][boardSize];
		Point point = new Point();
		for (point.x = 0; point.x < disks.length; point.x++) {
			for (point.y = 0; point.y < disks[point.x].length; point.y++) {
				disks[point.x][point.y] = new Disk(new Point(point), DiskState.EMPTY);
			}
		}
		disks[3][3] = new Disk(new Point(point), DiskState.WHITE);
		disks[3][4] = new Disk(new Point(point), DiskState.BLACK);
		disks[4][3] = new Disk(new Point(point), DiskState.BLACK);
		disks[4][4] = new Disk(new Point(point), DiskState.WHITE);
	}

	public Disk getDisk(Point point) {
		return disks[point.x][point.y];
	}

	public int getScore(DiskState color) {
		int score = 0;
		Point point = new Point();
		for (point.x = 0; point.x < disks.length; point.x++) {
			for (point.y = 0; point.y < disks[point.x].length; point.y++) {
				if (disks[point.x][point.y].getState() == DiskState.WHITE) {
					score++;
				}
			}
		}
		return score;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Point point = new Point();
		builder.append("  A B C D E F G H\n");
		for (point.x = 0; point.x < disks.length; point.x++) {
			builder.append(point.x + 1);
			for (point.y = 0; point.y < disks[point.x].length; point.y++) {
				builder.append(' ');
				builder.append(disks[point.x][point.y].getState().getSymbol());
			}
			builder.append('\n');
		}
		return builder.toString();
	}
}
