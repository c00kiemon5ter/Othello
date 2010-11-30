package logic.ai.searchers;

import core.Board;
import core.SquareState;
import core.Player;
import java.awt.Point;
import logic.MoveExplorer;
import logic.ai.evaluation.Evaluation;
import java.util.Set;

public abstract class AbstractSearcher implements Searcher {

	protected Point bestMove;

	@Override
	public abstract int search(Board board, Player player, int alpha, int beta, int depth, Evaluation function);

	public Point getBestMove() {
		return bestMove;
	}

	public int simpleSearch(Board board, Player player, int depth, Evaluation function) {
		int record = Integer.MIN_VALUE;
		Point maxMove = null;
		if (depth <= 0 || isEndState(board)) {
			record = function.evaluate(board, player);
		} else {
			Board subBoard = board.clone();
			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			if (!possibleMoves.isEmpty()) {
				for (Point nextPossibleMove : possibleMoves) {
					subBoard = board.clone();
					subBoard.makeMove(nextPossibleMove, player.color());
					int result = -simpleSearch(subBoard, player.opponent(), depth - 1, function);
//					record = max(record, -simpleSearch(subBoard, player.opponent(), depth - 1, function));
					if (result > record) {
						record = result;
						maxMove = nextPossibleMove;
					}
				}
			} else {
				record = -simpleSearch(subBoard, player, depth - 1, function);
			}
		}
		bestMove = maxMove;
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
}
