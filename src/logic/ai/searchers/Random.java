package logic.ai.searchers;

import core.Board;
import core.Player;
import logic.ai.evaluation.Evaluation;
import java.util.LinkedList;
import java.util.Set;
import java.awt.Point;

public class Random extends AbstractSearcher {

	@Override
	public int search(Board board, Player player, int alpha, int beta, int depth, Evaluation function) {
		Set<Point> possibleMoves = board.getPossibleMoves(player);
		int record = (int) Math.random() * (possibleMoves.size() - 1);
		bestMove = new LinkedList<Point>(possibleMoves).get(record);
		return record;
	}
}
