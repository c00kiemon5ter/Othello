package logic;

import core.Board;
import core.DiskState;
import core.Player;
import java.awt.Point;
import java.util.Set;
import java.util.Collection;
import logic.ai.evaluation.Evaluation;
import logic.ai.evaluation.ScoreEval;
import logic.ai.searchers.AbstractSearcher;
import logic.ai.searchers.NegaMax;

public final class Controller {

	/**
	 * {@code turn} has two values
	 * <ul>
	 * <li>false : if it the black player plays
	 * <li>true  : if it the white player plays
	 * </ul>
	 */
	private Board board;
	private Player player;
	/* 0: all good , 1: one cant move , 2: none can move */
	private final short CANMOVE = 0, CANNOTMOVE = 2;
	private short canMove = CANMOVE;
	private int depth;

	private Controller() {
		this.board = new Board();
		init();
	}

	public Set<Point> markPossibleMoves() {
		Set<Point> moves = board.markPossibleMoves(player);
		if (moves.isEmpty()) {
			canMove++;
		} else {
			canMove = CANMOVE;
		}
		return moves;
	}

	public void unmarkPossibleMoves(Collection<Point> possibleMoves) {
		for (Point possibleMove : possibleMoves) {
			board.getDisk(possibleMove).setState(DiskState.EMPTY);
		}
	}

	public Set<Point> makeMove(Point move) {
		return board.makeMove(move, player.color());
	}

	public void updateScore() {
		player.setScore(calcScore(player.color()));
		player.opponent().setScore(calcScore(player.color().opposite()));
	}

	private int calcScore(DiskState color) {
		return board.getScore(color);
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
		return board.isFull() || checkZeroScore() || canMove == CANNOTMOVE;
	}

	private boolean checkZeroScore() {
		return Player.BLACK.score() == 0 || Player.WHITE.score() == 0;
	}

	public void changeTurn() {
		player = player.opponent();
	}

	public Player whoPlays() {
		return player;
	}

	public String boardWithTurn() {
		StringBuilder strbuf = new StringBuilder();
		String[] rows = board.boardWithStats().split("\n");
		for (int idx = 0; idx < rows.length; idx++) {
			strbuf.append(rows[idx]);
			if (idx == 7) {
				strbuf.append('\t').append(player).append(" plays");
			}
			strbuf.append('\n');
		}
		return strbuf.toString();
	}

	public String boardWithScore() {
		return board.boardWithStats();
	}

	public void init() {
		board.init();
		Player.BLACK.init();
		Player.WHITE.init();
		player = Player.BLACK;
		depth = 3;
	}

	public Point evalMove() {
		AbstractSearcher searcher = new NegaMax();
		Evaluation evalfunc = new ScoreEval();
		searcher.simpleSearch(board, player, depth, evalfunc);
//		searcher.search(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, evalfunc);
		return searcher.getBestMove();
	}

	private static class ControllerHolder {

		private static final Controller INSTANCE = new Controller();
	}

	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}
}
