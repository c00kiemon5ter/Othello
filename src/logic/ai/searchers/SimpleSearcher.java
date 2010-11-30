package logic.ai.searchers;

import core.Board;
import core.Player;
import logic.ai.evaluation.Evaluation;
import java.awt.Point;

interface SimpleSearcher {

	int simpleSearch(Board board, Player player, int depth, Evaluation function);

	Point getBestMove();
}
