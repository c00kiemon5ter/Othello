package logic.ai.searchers;

import core.Board;
import core.Player;
import java.awt.Point;
import java.util.Set;
import logic.MoveExplorer;
import logic.ai.evaluation.Evaluation;

/**
 * The classic minimax algorithm implementation.
 *
 * @author c00kiemon5ter
 */
public class MiniMax { // extends AbstractSearcher implements Searcher, SimpleSearcher {

//	/**
//	 * minimax search using A-B pruning
//	 *
//	 * @param board - the current board state
//	 * @param player - the current player
//	 * @param alpha -
//	 * @param beta -
//	 * @param depth - the current depth
//	 * @param function - the evaluation function
//	 * @return the score of chosen move
//	 */
//	public int search(Board board, Player player, int alpha, int beta, int depth, Evaluation function) {
//		int record = Integer.MIN_VALUE;
//		Point maxMove = null;
//		Board subBoard = board.clone();
//		if (depth <= 0 || isEndState(board)) {
//			record = function.evaluate(board, player);
//		} else {
//			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
//			if (!possibleMoves.isEmpty()) {
//				for (Point nextPossibleMove : possibleMoves) {
//					subBoard = board.clone();
//					subBoard.makeMove(nextPossibleMove, player.color());
//					int result = -search(subBoard, player.opponent(), alpha, beta, depth - 1, function);
//					if (result > record) {
//						record = result;
//						maxMove = nextPossibleMove;
//					}
//				}
//			} else {
//				record = -search(subBoard, player, alpha, beta, depth - 1, function);
//			}
//		}
//		bestMove = maxMove;
//		return record;
//	}
//
//	private int valueMax(Board board, Player player, int alpha, int beta, int depth, Evaluation function, int rcounter) {
//		int maxscore = alpha;
//		if (rcounter == 0) {
//			maxscore = function.evaluate(board, player);
//		} else {
//			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
//			if (!possibleMoves.isEmpty()) {
//				for (Point nextPossibleMove : possibleMoves) {
//					Board subBoard = board.clone();
//					subBoard.makeMove(nextPossibleMove, player.color());
//					int result = max(maxscore, valueMin(board, player, alpha, beta, depth, function, --rcounter));
//					alpha = maxscore;
//					if (alpha >= beta) {
//						maxscore = result;
//					}
//				}
//			}
//		}
//		return maxscore;
//	}
//
//	private int valueMin(Board board, Player player, int alpha, int beta, int depth, Evaluation function, int rcounter) {
//		int minscore = beta;
//		if (rcounter == 0) {
//			minscore = function.evaluate(board, player);
//		} else {
//			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
//			if (!possibleMoves.isEmpty()) {
//				for (Point nextPossibleMove : possibleMoves) {
//					Board subBoard = board.clone();
//					subBoard.makeMove(nextPossibleMove, player.color());
//					int result = min(minscore, valueMax(board, player, alpha, beta, depth, function, --rcounter));
//					if (alpha >= beta) {
//						minscore = result;
//					}
//				}
//			}
//		}
//		return minscore;
//	}
//
//	/**
//	 * Simple minimax search
//	 *
//	 * @param board - the current board state
//	 * @param player - the current player
//	 * @param depth - the current depth
//	 * @param function - the evaluation function
//	 * @return the score of chosen move
//	 */
//	public int simpleSearch(Board board, Player player, int depth, Evaluation function) {
//		int record = Integer.MIN_VALUE;
//		Point maxMove = null;
//		Board subBoard = board.clone();
//		if (depth <= 0 || isEndState(board)) {
//			record = function.evaluate(board, player);
//		} else {
//			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
//			if (!possibleMoves.isEmpty()) {
//				for (Point nextPossibleMove : possibleMoves) {
//					subBoard = board.clone();
//					subBoard.makeMove(nextPossibleMove, player.color());
//					int result = -simpleSearch(subBoard, player.opponent(), depth - 1, function);
//					if (result > record) {
//						record = result;
//						maxMove = nextPossibleMove;
//					}
//				}
//			} else {
//				record = -simpleSearch(subBoard, player, depth - 1, function);
//			}
//		}
//		bestMove = maxMove;
//		return record;
//	}
//
//	private int valueMax(Board board, Player player, int depth, Evaluation function, int rcounter) {
//		int maxscore = Integer.MIN_VALUE;
//		if (rcounter == 0) {
//			maxscore = function.evaluate(board, player);
//		} else {
//			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
//			if (!possibleMoves.isEmpty()) {
//				for (Point nextPossibleMove : possibleMoves) {
//					Board subBoard = board.clone();
//					subBoard.makeMove(nextPossibleMove, player.color());
//					int result = max(maxscore, valueMin(board, player, depth, function, --rcounter));
//					if (result > maxscore) {
//						maxscore = result;
//					}
//				}
//			}
//		}
//		return maxscore;
//	}
//
//	private int valueMin(Board board, Player player, int depth, Evaluation function, int rcounter) {
//		int minscore = Integer.MAX_VALUE;
//		if (rcounter == 0) {
//			minscore = function.evaluate(board, player);
//		} else {
//			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
//			if (!possibleMoves.isEmpty()) {
//				for (Point nextPossibleMove : possibleMoves) {
//					Board subBoard = board.clone();
//					subBoard.makeMove(nextPossibleMove, player.color());
//					int result = min(minscore, valueMax(board, player, depth, function, --rcounter));
//					if (result > minscore) {
//						minscore = result;
//					}
//				}
//			}
//		}
//		return minscore;
//	}
//
//	/* Test implementation */
//	public int minimax(Board board, Player player, int depth, Evaluation function) {
//		if (player == Player.WHITE) {	/* White is the maximizing player */
//			return valueMax(board, player, depth, function);
//		} else {			/* Black is the minimizing player */
//			return valueMin(board, player, depth, function);
//		}
//	}
//
//	private int valueMax(Board board, Player player, int depth, Evaluation function) {
//		int best = Integer.MIN_VALUE;
//		if (depth <= 0 || isEndState(board)) {
//			return function.evaluate(board, player);
//		}
//		Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
//		Board subBoard = board.clone();
//		for (Point move : possibleMoves) {
//			subBoard.makeMove(move, player.color());
//			int value = valueMin(board, player.opponent(), depth - 1, function);
//			subBoard = board.clone();
//			if (value > best) {
//				best = value;
//			}
//		}
//		return best;
//	}
//
//	private int valueMin(Board board, Player player, int depth, Evaluation function) {
//		int best = Integer.MAX_VALUE;
//		if (depth <= 0 || isEndState(board)) {
//			return function.evaluate(board, player);
//		}
//		Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
//		Board subBoard = board.clone();
//		for (Point move : possibleMoves) {
//			subBoard.makeMove(move, player.color());
//			int value = valueMax(board, player.opponent(), depth - 1, function);
//			subBoard = board.clone();
//			if (value < best) {
//				best = value;
//			}
//		}
//		return best;
//	}
}
