package logic.ai.searchers;

import core.Board;
import core.Player;
import logic.ai.evaluation.Evaluation;
import java.awt.Point;

/**
 * Simple searcher interface.
 * Provides searching using basic algorithms like minimax and negamax 
 * @author c00kiemon5ter
 */
interface SimpleSearcher {

	int simpleSearch(Board board, Player player, int depth, Evaluation function);

	Point getBestMove();
}
