package logic.ai.searchers;

import core.Board;
import core.DiskState;
import core.Player;
import logic.MoveExplorer;
import java.util.Set;
import java.awt.Point;
import logic.ai.evaluation.Evaluation;

public class NegaMax extends AbstractSearcher {

	/* search( board , player , Integer.MIN_VALUE , Integer.MAX_VALUE , depth , ef ) */
	@Override
	public int search(Board board, Player player, int alpha, int beta, int depth, Evaluation function) {
		int record = Integer.MIN_VALUE;
		if (depth <= 0 || isEndState(board)) {
			record = function.evaluate(board, player);
		} else {
			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			if (!possibleMoves.isEmpty()) {
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = new Board(board);
					subBoard.makeMove(nextPossibleMove, player.color());
					record = max(Integer.MIN_VALUE, -search(subBoard, player.opponent(), alpha, beta, depth - 1, function));
				}
//			} else {
//				possibleMoves = MoveExplorer.explore(board, player.color().opposite());
//				if (possibleMoves.isEmpty()) {
//				} else {
//					record = max(Integer.MIN_VALUE, search(board, player, depth - 2, -ev_sign, function));
//				}
			}
		}
		return record;
	}

	public int searchSimple(Board board, Player player, int depth, Evaluation function) {
		int record = Integer.MIN_VALUE;
		Point maxMove = null;
		if (depth <= 0 || isEndState(board)) {
			record = function.evaluate(board, player);
		} else {
			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			if (!possibleMoves.isEmpty()) {
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = new Board(board);
					subBoard.makeMove(nextPossibleMove, player.color());
					int result = -searchSimple(subBoard, player.opponent(), depth - 1, function);
//					record = max(record, -searchSimple(subBoard, player.opponent(), depth - 1, function));
					if (result > record) {
						record = result;
						maxMove = nextPossibleMove;
					}
				}
			}
		}
		bestMove = maxMove;
		return record;
	}

	private int max(int a, int b) {
		return a < b ? b : a;
	}

	/**
	 * Game stops if <br/>
	 * <ol>
	 * <li> board is full</li>
	 * <li> one's score is 0/zero</li>
	 * <li> none has a valid next move - not handled here</li>
	 * </ol>
	 *
	 * @return if the game is over
	 */
	private boolean isEndState(final Board board) {
		return board.isFull()
		       || board.getScore(DiskState.BLACK) == 0
		       || board.getScore(DiskState.WHITE) == 0;
	}
}
