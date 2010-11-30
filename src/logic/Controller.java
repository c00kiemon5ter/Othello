package logic;

import core.Board;
import core.SquareState;
import core.Player;
import java.awt.Point;
import java.util.Set;
import logic.ai.evaluation.Evaluation;
import logic.ai.evaluation.ScoreDiffEval;
import logic.ai.evaluation.ScoreEval;
import logic.ai.searchers.AbstractSearcher;
import logic.ai.searchers.MiniMax;
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
	public final int DEFAULT_DEPTH = 3;
	/* 0: all good , 1: one cant move , 2: none can move */
	private final short CANMOVE = 0, CANNOTMOVE = 2;
	private short canMove = CANMOVE;
	private int depth;

	private Controller() {
		this.board = new Board();
		init();
	}

	public Set<Point> markPossibleMoves() {
		Set<Point> moves = board.getPossibleMoves(player);
		board.markPossibleMoves(moves);
		if (moves.isEmpty()) {
			canMove++;
		} else {
			canMove = CANMOVE;
		}
		return moves;
	}

	public void unmarkPossibleMoves() {
		board.unmarkPossibleMoves();
	}

	public Set<Point> makeMove(Point move) {
		return board.makeMove(move, player.color());
	}

	private int calcScore(SquareState state) {
		return board.count(state);
	}

	public int getBlackScore() {
		return board.count(SquareState.BLACK);
	}

	public int getWhiteScore() {
		return board.count(SquareState.WHITE);
	}

	public Player getWinner() {
		return getBlackScore() < getWhiteScore() ? Player.WHITE : Player.BLACK;
	}

	public boolean isDraw() {
		return getBlackScore() == getWhiteScore();
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
		return getBlackScore() == 0 || getWhiteScore() == 0;
	}

	public void changeTurn() {
		player = player.opponent();
	}

	public Player who() {
		return player;
	}

	public String boardWithTurn() {
		return board.toStringWithStatsTurn(player);
	}

	public void init() {
		board.init();
		player = Player.BLACK;
		depth = DEFAULT_DEPTH;
	}

	public void setDifficulty(int level) {
		depth = level;
	}

	public Point evalMove() {
		AbstractSearcher searcher = new NegaMax();
//		AbstractSearcher searcher = new MiniMax();
		Evaluation evalfunc = new ScoreEval();
//		Evaluation evalfunc = new ScoreDiffEval();
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
