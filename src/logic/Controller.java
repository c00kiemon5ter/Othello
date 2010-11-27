/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package logic;

import core.Board;
import core.Disk;
import core.DiskState;
import core.Player;
import java.awt.Point;
import java.util.Set;
import java.util.Collection;

public final class Controller {

	/**
	 * turn has two values -> boolean
	 * false : if it's black player's turn
	 * true  : if it's white player's turn
	 */
	private Board board;

	;
	private MoveExplorer explorer;
	private boolean turn;

	private Controller() {
		this.board = new Board();
		init();
	}

	public Set<Point> markPossibleMoves() {
		explorer = new MoveExplorer(board, turn ? DiskState.WHITE : DiskState.BLACK);
		Set<Point> moves = explorer.explore();
		for (Point possiblePoint : moves) {
			board.getDisk(possiblePoint).setState(DiskState.PSSBL);
		}
		return moves;
	}

	public void unmarkPossibleMoves(Collection<Point> moves) {
		for (Point possiblePoint : moves) {
			board.getDisk(possiblePoint).setState(DiskState.EMPTY);
		}
	}

	public Set<Point> makeMove(Point move) {
		DiskState color = turn ? DiskState.WHITE : DiskState.BLACK;
		explorer = new MoveExplorer(board, turn ? DiskState.WHITE : DiskState.BLACK);
		board.getDisk(move).setColor(color);
		return explorer.fill(move);
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
	 * <li> none has a valid next move</li>
	 * </ol>
	 *
	 * @return if the game is over
	 */
	public boolean endOfGame() {
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

	public void changeTurn() {
		turn = !turn;
	}

	public boolean turn() {
		return turn;
	}

	public String getBoardForm() {
		return board.toString();
	}

	public void init() {
		Player.BLACK.init();
		Player.WHITE.init();
		board.init();
		turn = false;
	}

	private static class ControllerHolder {

		private static final Controller INSTANCE = new Controller();
	}

	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}
}
