package logic.ai.searchers;

import core.Board;
import core.Player;
import core.SquareState;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

public abstract class AbstractSearcher implements Searcher, SimpleSearcher {

	protected Point bestMove;

	protected int max(int a, int b) {
		return a < b ? b : a;
	}

	protected int min(int a, int b) {
		return a > b ? b : a;
	}

	protected int randomChoice(Board board, Player player) {
		List<Point> possibleMoves = new ArrayList<Point>(board.getPossibleMoves(player));
		int record = (int) Math.random() * (possibleMoves.size() - 1);
		bestMove = possibleMoves.get(record);
		return record;
	}

	/**
	 * Game stops if <br/>
	 * <ol>
	 * <li> board is full</li>
	 * <li> one's score is 0/zero</li>
	 * <li> none has a valid next move - not handled here</li>
	 * </ol>
	 *
	 * @param board the board to examine
	 * @return if the game is over
	 */
	protected boolean isEndState(final Board board) {
		return board.isFull()
		       || board.count(SquareState.BLACK) == 0
		       || board.count(SquareState.WHITE) == 0;
	}

	@Override
	public Point getBestMove() {
		return bestMove;
	}
}
