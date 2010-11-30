package logic.ai.searchers;

import core.Board;
import core.Player;
import logic.MoveExplorer;
import java.util.Set;
import java.awt.Point;
import logic.ai.evaluation.Evaluation;

public class NegaMax extends AbstractSearcher implements Searcher, SimpleSearcher {

	@Override
	public int search(Board board, Player player, int alpha, int beta, int depth, Evaluation function) {
		Point maxMove = null;
		if (depth <= 0 || isEndState(board)) {
			alpha = function.evaluate(board, player);
		} else {
			Board subBoard = board.clone();
			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			if (!possibleMoves.isEmpty()) {
				for (Point nextPossibleMove : possibleMoves) {
					subBoard = board.clone();
					subBoard.makeMove(nextPossibleMove, player.color());
					int value = -search(subBoard, player.opponent(), depth - 1, -beta, -alpha, function);
//					record = max(record, -simpleSearch(subBoard, player.opponent(), depth - 1, function));
					if (value >= beta) {
						maxMove = nextPossibleMove;
						return beta;
					}
					if (value > alpha) {
						maxMove = nextPossibleMove;
						alpha = value;
					}
				}
			} else {
				int value = -search(subBoard, player, depth - 1, -beta, -alpha, function);
				if (value >= beta) {
					return beta;
				}
				if (value > alpha) {
					alpha = value;
				}
			}
		}
		bestMove = maxMove;
		return alpha;
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
