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
	 * whosTurn has two values -> boolean
	 * false : if it's black player's whosTurn
	 * true  : if it's white player's whosTurn
	 */
	private Board board;
	private MoveExplorer explorer;
	private boolean turn;
	/* 0: all good , 1: one cant move , 2: none can move */
	private final short YES = 0, CANTMOVE = 2;
	private short canMove = YES;

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
		if (moves.isEmpty()) {
			canMove++;
		} else {
			canMove = YES;
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
		score = calcScore(DiskState.BLACK);
		Player.BLACK.setScore(score);
		score = calcScore(DiskState.WHITE);
		Player.WHITE.setScore(score);
	}

	private int calcScore(DiskState color) {
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

	private int getScore(DiskState color) {
		return color == DiskState.WHITE ? Player.WHITE.score() : Player.BLACK.score();
	}

	private String getStats(boolean color) {
		return color ? Player.WHITE.stats() : Player.BLACK.stats();
	}

	public int getBlackScore() {
		return getScore(DiskState.BLACK);
	}

	public int getWhiteScore() {
		return getScore(DiskState.WHITE);
	}

	public String getBlackStats() {
		return getStats(false);
	}

	public String getWhiteStats() {
		return getStats(true);
	}

	public boolean getWinner() {
		return Player.BLACK.score() < Player.WHITE.score();
	}

	public String getWinnerName() {
		return getWinner() ? Player.WHITE.toString() : Player.BLACK.toString();
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
		return checkFullBoard() || checkZeroScore() || canMove == CANTMOVE;
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

	public boolean whosTurn() {
		return turn;
	}

	public String getBoardForm() {
		StringBuilder strbuf = new StringBuilder();
		String[] rows = board.toString().split("\n");
		for (int idx = 0; idx < rows.length; idx++) {
			strbuf.append(rows[idx]);
			if (idx == 2) {
				strbuf.append('\t').append(Player.BLACK.stats());
			} else if (idx == 4) {
				strbuf.append('\t').append(Player.WHITE.stats());
			} else if (idx == 6) {
				strbuf.append('\t').
					append(turn ? Player.WHITE : Player.BLACK).
					append(" plays");
			}
			strbuf.append('\n');
		}
		return strbuf.toString();
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
