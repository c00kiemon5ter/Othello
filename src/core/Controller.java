/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package core;

import java.awt.Point;

public class Controller {

	/**
	 * turn has two values -> boolean
	 * false : if it's black player's turn
	 * true  : if it's white player's turn
	 */
	private boolean turn;
	private Board board;

	private Controller() {
		turn = false;	/* black player plays first */
		board = Board.getInstance();
	}

	private void operate() {
		while (!endOfGame()) {
			// change turn
			turn = !turn;
			// mark next available moves
			// if the player can move
			// let player chose next move
		}
	}

	private void updateScores() {
		int score = 0;
		score = getScore(DiskState.BLACK);
		Player.BLACK.setScore(score);
		score = getScore(DiskState.WHITE);
		Player.WHITE.setScore(score);
	}

	public int getScore(DiskState color) {
		int score = 0;
		Point point = new Point();
		for (Disk[] diskrow : board.getDisks()) {
			for (Disk disk : diskrow) {
				if (disk.getState() == color) {
					score++;
				}
			}
		}
		return score;
	}

	private boolean checkWinner() {
		// TODO: check if we have a winner
		return false;
	}

	/**
	 * Game stops if
	 * 1. we have a winner
	 * 2.
	 */
	private boolean endOfGame() {
		boolean gameEnd = false;
		gameEnd = checkWinner();
		return gameEnd;
	}

	private static class ControllerHolder {

		private static final Controller INSTANCE = new Controller();
	}

	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}
}
