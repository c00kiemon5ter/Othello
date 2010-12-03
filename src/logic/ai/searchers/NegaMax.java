package logic.ai.searchers;

import core.Board;
import core.Player;
import logic.MoveExplorer;
import java.util.Set;
import java.awt.Point;
import logic.ai.evaluation.Evaluation;

/**
 * <p>Negamax search is a slightly variant formulation of minimax search
 * that relies on the zero-sum property of a two-player game.</p>
 * <p>By definition the value of a position to player A in such a game is
 * the negation of the value to player B. Thus, the player on move looks
 * for a move that maximizes the negation of the value of the position
 * resulting from the move: this successor position must by definition
 * have been valued by the opponent. </p>
 * <p>The reasoning of the previous sentence works regardless of whether
 * A or B is on move. This means that a single computation can be used to
 * value all positions</p>
 * <p>This is a coding simplification over minimax, which requires that A
 * select the move with the maximum-valued successor while B selects the
 * move with the minimum-valued successor.</p>
 * 
 * @author c00kiemon5ter
 */
public class NegaMax extends AbstractSearcher implements Searcher, SimpleSearcher {

	@Override
	public int search(Board board, Player player, int alpha, int beta, int depth, Evaluation function) {
		int record = alpha;
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
					int result = -search(subBoard, player.opponent(), -beta, -alpha, depth - 1, function);
					if (result > record) {
						record = result;
						maxMove = nextPossibleMove;
						if (record >= beta) {
							return record;
						}
					}
				}
			} else {
				record = -simpleSearch(subBoard, player, depth - 1, function);
			}
		}
		bestMove = maxMove;
		return record;
	}

	@Override
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
}
