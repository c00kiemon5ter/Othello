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
	 * whoPlays has two values -> boolean
	 * false : if it's black player's whoPlays
	 * true  : if it's white player's whoPlays
	 */
	private Board board;
	private MoveExplorer explorer;
	private boolean turn;
	private Player player;
	/* 0: all good , 1: one cant move , 2: none can move */
	private final short CANMOVE = 0, CANNOTMOVE = 2;
	private short canMove = CANMOVE;

	private Controller() {
		this.board = new Board();
		init();
	}

	public Set<Point> markPossibleMoves() {
		explorer = new MoveExplorer(board);
		Set<Point> moves = explorer.explore(player.color());
		for (Point possiblePoint : moves) {
			board.getDisk(possiblePoint).setState(DiskState.PSSBL);
		}
		if (moves.isEmpty()) {
			canMove++;
		} else {
			canMove = CANMOVE;
		}
		return moves;
	}

	public void unmarkPossibleMoves(Collection<Point> moves) {
		for (Point possiblePoint : moves) {
			board.getDisk(possiblePoint).setState(DiskState.EMPTY);
		}
	}

	public Set<Point> makeMove(Point move) {
		board.getDisk(move).setColor(player.color());
		Set<Point> pointsToFill = explorer.pointsToFill(move);
		for (Point pointToFill : pointsToFill) {
			board.getDisk(pointToFill).setColor(player.color());
		}
		pointsToFill.add(move);
		return pointsToFill;
	}

	public void updateScore() {
		player.setScore(calcScore(player));
		player.opponent().setScore(calcScore(player.opponent()));
	}

	private int calcScore(Player player) {
		return calcScore(player.color());
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

	public int getBlackScore() {
		return Player.BLACK.score();
	}

	public int getWhiteScore() {
		return Player.WHITE.score();
	}

	public String getBlackStats() {
		return Player.BLACK.stats();
	}

	public String getWhiteStats() {
		return Player.WHITE.stats();
	}

	public Player getWinner() {
		return Player.BLACK.score() < Player.WHITE.score() ? Player.WHITE : Player.BLACK;
	}

	public String getWinnerName() {
		return getWinner().toString();
	}

	public boolean isDraw() {
		return Player.BLACK.score() == Player.WHITE.score();
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
		return checkFullBoard() || checkZeroScore() || canMove == CANNOTMOVE;
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
		player = player.opponent();
	}

	public Player whoPlays() {
		return player;
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
				strbuf.append('\t').append(player).append(" plays");
			}
			strbuf.append('\n');
		}
		return strbuf.toString();
	}

	public void init() {
		board.init();
		Player.BLACK.init();
		Player.WHITE.init();
		player = Player.BLACK;
		turn = false;
	}

	private static class ControllerHolder {

		private static final Controller INSTANCE = new Controller();
	}

	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}
}
