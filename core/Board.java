/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package core;

import java.awt.Point;

public class Board {

	private static final int BOARD_SIZE = 8;
	private static final boolean BLACK = false;
	private static final boolean WHITE = true;
	private Disk[][] disks;

	public static Board getInstance() {
		return BoardHolder.INSTANCE;
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
		Point position = new Point();
		position.x = 4;
		position.y = 4;
		disks[position.x][position.y] = new Disk(position, BLACK);
		position.x = 4;
		position.y = 5;
		disks[position.x][position.y] = new Disk(position, WHITE);
		position.x = 5;
		position.y = 4;
		disks[position.x][position.y] = new Disk(position, WHITE);
		position.x = 5;
		position.y = 5;
		disks[position.x][position.y] = new Disk(position, BLACK);
	}

	public String getScore() {
		int whiteScore = 0, blackScore = 0;
		Point position = new Point();
		for (position.x = 0; position.x < disks.length; position.x++) {
			for (position.y = 0; position.y < disks[position.x].length; position.y++) {
				if (disks[position.x][position.y].getColor()) {
					whiteScore++;
				} else {
					blackScore++;
				}
			}
		}
		return String.format("White: %d \tBlack: %d ", whiteScore, blackScore);
	}

	public String getScore(boolean color) {
		int whiteScore = 0, blackScore = 0;
		Point position = new Point();
		for (position.x = 0; position.x < disks.length; position.x++) {
			for (position.y = 0; position.y < disks[position.x].length; position.y++) {
				if (disks[position.x][position.y].getColor()) {
					whiteScore++;
				} else {
					blackScore++;
				}
			}
		}
		return color ? String.format("White: %d ", whiteScore)
			: String.format("Black: %d ", blackScore);
	}
}
