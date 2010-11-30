package logic.ai.searchers;

import core.Board;
import core.Player;
import java.awt.Point;
import logic.ai.evaluation.Evaluation;

public abstract class AbstractSearcher implements Searcher {

	protected Point bestMove;

	@Override
	public abstract int search(Board board, Player player, int alpha, int beta, int depth, Evaluation function);

	public Point getBestMove() {
		return bestMove;
	}
}
