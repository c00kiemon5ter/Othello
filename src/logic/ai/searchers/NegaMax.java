package logic.ai.searchers;

import core.Board;
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
		if (depth <= 0 || super.isEndState(board)) {
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

	private int max(int a, int b) {
		return a < b ? b : a;
	}
}
