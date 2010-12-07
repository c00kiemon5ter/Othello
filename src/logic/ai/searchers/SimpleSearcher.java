package logic.ai.searchers;

import core.Board;
import core.Player;
import logic.ai.evaluation.Evaluation;

/**
 * Simple searcher interface.
 * Provides searching using basic algorithms like minimax and negamax
 *
 * @author c00kiemon5ter
 */
interface SimpleSearcher {

	SearchResult simpleSearch(final Board board, final Player player,
				  final int depth, final Evaluation function);
}
