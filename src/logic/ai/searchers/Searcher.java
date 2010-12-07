package logic.ai.searchers;

import core.Board;
import core.Player;
import logic.ai.evaluation.Evaluation;

/**
 * The searcher interface. Provides searching making use of A-B pruning
 * 
 * basic init call is:
 * search(board, player, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, ef)
 *
 * @author c00kiemon5ter
 */
interface Searcher {

	SearchResult search(final Board board, final Player player, int alpha,
			    int beta, final int depth, final Evaluation function);
}
