package logic.ai.searchers;

import core.Board;
import core.Player;
import core.SquareState;
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
public class NegaMax { //extends AbstractSearcher implements Searcher, SimpleSearcher {

//	public SearchResult search(Board board, Player player, int alpha, int beta, int depth, Evaluation function) {
//		if (depth <= 0 || isEndState(board)) {
//			record = function.evaluate(board, player);
//		} else {
//			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
//			if (!possibleMoves.isEmpty()) {
//				for (Point nextPossibleMove : possibleMoves) {
//					Board subBoard = board.clone();
//					subBoard.makeMove(nextPossibleMove, player.color());
//					int result = -search(subBoard, player.opponent(), -beta, -alpha, depth - 1, function);
//					if (result > record) {
//						record = result;
//						maxMove = nextPossibleMove;
//						if (record >= beta) {
//							return record;
//						}
//					}
//				}
//			} else {
//				record = -simpleSearch(board.clone(), player, depth - 1, function);
//			}
//		}
//		bestMove = maxMove;
//		return record;
//	}
	public SearchResult simpleSearch(Board board, Player player, int depth, Evaluation evfunction) {
		if (depth <= 0 || isEndState(board)) {
			return new SearchResult(null, evfunction.evaluate(board, player));
		} else { /* there's more to check */
			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			SearchResult best = new SearchResult(null, Integer.MIN_VALUE);
			if (possibleMoves.isEmpty()) { /* turn is lost - check next player */
				possibleMoves = MoveExplorer.explore(board, player.opponent().color());
				if (possibleMoves.isEmpty()) { /* end of game - is there a winner ? */
					switch (Integer.signum(board.count(player.color()) - board.count(player.opponent().color()))) {
						case -1:
							best = new SearchResult(null, Integer.MIN_VALUE);
							break;
						case 0:
							best = new SearchResult(null, 0);
							break;
						case 1:
							best = new SearchResult(null, Integer.MAX_VALUE);
							break;
					}
				} else { /* game continues - nothing to check */
					best = simpleSearch(board, player.opponent(), depth - 1, evfunction).negated();
				}
			} else { /* check the score of each move */
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = board.clone();
					subBoard.makeMove(nextPossibleMove, player.color());
					int score = simpleSearch(subBoard, player.opponent(), depth - 1, evfunction).negated().getScore();
					if (best.getScore() < score) {
						/* store the best score and coresponding move */
						best = new SearchResult(nextPossibleMove, score);
					}
				}
			}
			return best;
		}
	}

	protected boolean isEndState(final Board board) {
		return board.isFull()
		       || board.count(SquareState.BLACK) == 0
		       || board.count(SquareState.WHITE) == 0;
	}
}
