/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package core;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public final class Controller {

	/**
	 * turn has two values -> boolean
	 * false : if it's black player's turn
	 * true  : if it's white player's turn
	 */
	private Board board = Board.getInstance();
	private MoveExplorer explorer;
	private boolean turn;

	private Controller() {
		init();
	}

	List<Point> markPossibleMoves() {
		explorer = turn ? MoveExplorer.WHITEEXPLORER : MoveExplorer.BLACKEXPLORER;
		List<Point> moves = new LinkedList<Point>(explorer.explore());
		for (Point possiblePoint : moves) {
			board.getDisk(possiblePoint).setState(DiskState.PSSBL);
		}
		return moves;
	}

	void unmarkPossibleMoves(List<Point> moves) {
		for (Point possiblePoint : moves) {
			board.getDisk(possiblePoint).setState(DiskState.EMPTY);
		}
	}

	void makeMove(Point move) {
		DiskState color = turn ? DiskState.WHITE : DiskState.BLACK;
		explorer = turn ? MoveExplorer.WHITEEXPLORER : MoveExplorer.BLACKEXPLORER;
		board.getDisk(move).setColor(color);
		explorer.fill(move);
	}

	public void updateScores() {
		int score = 0;
		score = getScore(DiskState.BLACK);
		Player.BLACK.setScore(score);
		score = getScore(DiskState.WHITE);
		Player.WHITE.setScore(score);
	}

	public int getScore(DiskState color) {
		int score = 0;
		for (Disk[] diskrow : board.getDisks()) {
			for (Disk disk : diskrow) {
				if (disk.getState() == color) {
					score++;
				}
			}
		}
		return score;
	}

	/**
	 * Game stops if <br/>
	 * <ol>
	 * <li> board is full</li>
	 * <li> one's score is 0/zero</li>
	 * <li> none has a valid next move (handled by {@code operate()})</li>
	 * </ol>
	 */
	boolean endOfGame() {
		return checkFullBoard() || checkZeroScore();
	}

	private boolean checkFullBoard() {
		for (Disk[] row : board.getDisks()) {
			for (Disk disk : row) {
				if (disk.getState() == DiskState.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkZeroScore() {
		return Player.BLACK.score() == 0 || Player.WHITE.score() == 0;
	}

	void init() {
		Player.BLACK.init();
		Player.WHITE.init();
		board.init();
		turn = false;
	}

	private static class ControllerHolder {

		private static final Controller INSTANCE = new Controller();
	}

	public void changeTurn() {
		turn = !turn;
	}

	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}
}
