package logic.ai.searchers;

import core.Board;
import core.Player;
import logic.ai.evaluation.Evaluation;
import java.util.Set;
import java.awt.Point;

public class Random implements Searcher {

	public Random() {
	}

	@Override
	public int search(Board board, Player player, int alpha, int beta, int depth, Evaluation function) {
		Set<Point> possibleMoves = board.getPossibleMoves(player);
		return (int) Math.random() * possibleMoves.size();
	}
}
