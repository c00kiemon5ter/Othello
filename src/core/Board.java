package core;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import logic.MoveExplorer;

public final class Board {

	public static final int BOARD_LENGTH = 8;
	public static final int BOARD_WIDTH = 8;
	private Disk[][] disks;

	public Board() {
		this(BOARD_LENGTH, BOARD_WIDTH);
	}

	private Board(int boardLength, int boardWidth) {
		disks = new Disk[boardLength][boardWidth];
		init();
	}

	/* copy constructor */
	public Board(Board board) {
		disks = new Disk[BOARD_LENGTH][BOARD_WIDTH];
		Point point = new Point();
		for (point.x = 0; point.x < disks.length; point.x++) {
			for (point.y = 0; point.y < disks[point.x].length; point.y++) {
				disks[point.x][point.y] = new Disk(board.getDisk(point));
			}
		}
	}

	/**
	 * game starts with four pieces, two black, two white, placed diagonally
	 */
	public void init() {
		Point point = new Point();
		for (point.x = 0; point.x < disks.length; point.x++) {
			for (point.y = 0; point.y < disks[point.x].length; point.y++) {
				disks[point.x][point.y] = new Disk(point, DiskState.EMPTY);
			}
		}
		disks[3][3] = new Disk(point, DiskState.WHITE);
		disks[3][4] = new Disk(point, DiskState.BLACK);
		disks[4][3] = new Disk(point, DiskState.BLACK);
		disks[4][4] = new Disk(point, DiskState.WHITE);
	}

	public Disk getDisk(Point point) {
		return disks[point.x][point.y];
	}

	public Set<Point> getDiskPoints(DiskState color) {
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

	public int getScore(DiskState color) {
		int score = 0;
		for (Disk[] rowOfDisks : disks) {
			for (Disk disk : rowOfDisks) {
				if (disk.getColor() == color) {
					score++;
				}
			}
		}
		return score;
	}

	public boolean isFull() {
		for (Disk[] rowOfDisks : disks) {
			for (Disk disk : rowOfDisks) {
				if (disk.getState() == DiskState.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	public Set<Point> getPossibleMoves(Player player) {
		return MoveExplorer.explore(this, player.color());
	}

	public Set<Point> markPossibleMoves(Player player) {
		Set<Point> possibleMoves = MoveExplorer.explore(this, player.color());
		for (Point point : possibleMoves) {
			getDisk(point).setColor(DiskState.PSSBL);
		}
		return possibleMoves;
	}

	public void markPossibleMoves(Set<Point> possibleMoves) {
		for (Point point : possibleMoves) {
			getDisk(point).setColor(DiskState.PSSBL);
		}
	}

	public void unmarkPossibleMoves() {
		for (Disk[] rowdisk : disks) {
			for (Disk disk : rowdisk) {
				if (disk.getState() == DiskState.PSSBL) {
					disk.setColor(DiskState.EMPTY);
				}
			}
		}
	}

	public void fill(Set<Point> disksToFill, DiskState color) {
		for (Point point : disksToFill) {
			getDisk(point).setColor(color);
		}
	}

	public Set<Point> makeMove(Point move, DiskState color) {
		getDisk(move).setColor(color);
		Set<Point> fillPoints = MoveExplorer.pointsToFill(this, move);
		fill(fillPoints, color);
		fillPoints.add(move);
		return fillPoints;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Point point = new Point();
		builder.append("  A B C D E F G H\n");
		for (point.x = 0; point.x < disks.length; point.x++) {
			builder.append(point.x + 1);
			for (point.y = 0; point.y < disks[point.x].length; point.y++) {
				builder.append(' ').append(disks[point.x][point.y].getState().getSymbol());
			}
			builder.append('\n');
		}
		return builder.toString();
	}

	public String boardWithStats() {
		Point point = new Point();
		StringBuilder builder = new StringBuilder();
		builder.append("\n  A B C D E F G H\n");
		for (point.x = 0; point.x < disks.length; point.x++) {
			builder.append(point.x + 1);
			for (point.y = 0; point.y < disks[point.x].length; point.y++) {
				builder.append(' ').append(getDisk(point).getState().getSymbol());
			}
			switch (point.x) {
				case 1:
//					builder.append('\t').append(Player.BLACK.stats());
					builder.append('\t').append(Player.BLACK).
						append(": ").
						append(getScore(DiskState.BLACK));
					break;
				case 3:
//					builder.append('\t').append(Player.WHITE.stats());
					builder.append('\t').append(Player.WHITE).
						append(": ").
						append(getScore(DiskState.WHITE));
					break;
			}
			builder.append('\n');
		}
		return builder.toString();
	}
}
