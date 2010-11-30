package logic.ai.searchers;

import core.Board;
import core.Player;
import java.awt.Point;
import java.util.Set;
import logic.MoveExplorer;
import logic.ai.evaluation.Evaluation;

public class MiniMax extends AbstractSearcher {

	@Override
	public int search(Board board, Player player, int alpha, int beta, int depth, Evaluation function) {
		int record = Integer.MIN_VALUE;
                int minscore = Integer.MAX_VALUE;
		Point maxMove = null;
		if (depth <= 0 || isEndState(board)) {
			record = function.evaluate(board, player);
		} else {
			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			if (!possibleMoves.isEmpty()) {
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = new Board(board);
					subBoard.makeMove(nextPossibleMove, player.color());
					int result = -search(subBoard, player.opponent(), alpha, beta, depth - 1, function);
					if (result > record) {
						record = result;
						maxMove = nextPossibleMove;
					}
				}
			}
		}
		bestMove = maxMove;
		return record;
	}

        @Override
        public int searchSimple(Board board, Player player, int depth, Evaluation function) {
		int record = Integer.MIN_VALUE;
                int minscore = Integer.MAX_VALUE;
		Point maxMove = null;
		if (depth <= 0 || isEndState(board)) {
			record = function.evaluate(board, player);
		} else {
			Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			if (!possibleMoves.isEmpty()) {
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = new Board(board);
					subBoard.makeMove(nextPossibleMove, player.color());
					int result = -searchSimple(subBoard, player.opponent(), depth - 1, function);
					if (result > record) {
						record = result;
						maxMove = nextPossibleMove;
					}
				}
			}
		}
		bestMove = maxMove;
		return record;
	}

        private int valueMax(Board board, Player player, int depth, Evaluation function,int rcounter) {
		int maxscore = Integer.MIN_VALUE;
		Point maxMove = null;
                if(rcounter == 0)
                {maxscore = function.evaluate(board, player);
                } else {
                    Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			if (!possibleMoves.isEmpty()) {
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = new Board(board);
					subBoard.makeMove(nextPossibleMove, player.color());
					int result = max(maxscore,valueMin(board,player,depth,function,--rcounter));
					if (result > maxscore) {
						maxscore = result;
						maxMove = nextPossibleMove;
					}
				}
			}
                }
                return maxscore;
        }

        private int valueMin(Board board, Player player, int depth, Evaluation function,int rcounter) {
            int minscore = Integer.MAX_VALUE;
		Point maxMove = null;
                if(rcounter == 0)
                {minscore = function.evaluate(board, player);
                } else {
                    Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			if (!possibleMoves.isEmpty()) {
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = new Board(board);
					subBoard.makeMove(nextPossibleMove, player.color());
					int result = min(minscore,valueMax(board,player,depth,function,--rcounter));
					if (result > minscore) {
						minscore = result;
						maxMove = nextPossibleMove;
					}
				}
			}
                }
                return minscore;
        }

        private int valueMax(Board board, Player player, int alpha, int beta, int depth, Evaluation function,int rcounter) {
		int maxscore = alpha;
		Point maxMove = null;
                if(rcounter == 0)
                {maxscore = function.evaluate(board, player);
                } else {
                    Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			if (!possibleMoves.isEmpty()) {
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = new Board(board);
					subBoard.makeMove(nextPossibleMove, player.color());
					int result = max(maxscore,valueMin(board,player,alpha,beta,depth,function,--rcounter));
                                        alpha = maxscore;
					if (alpha >= beta) {
						maxscore = result;
						maxMove = nextPossibleMove;
					}
				}
			}
                }
                return maxscore;
        }

        private int valueMin(Board board, Player player, int alpha, int beta, int depth, Evaluation function,int rcounter) {
            int minscore = beta;
		Point maxMove = null;
                if(rcounter == 0)
                {minscore = function.evaluate(board, player);
                } else {
                    Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());
			if (!possibleMoves.isEmpty()) {
				for (Point nextPossibleMove : possibleMoves) {
					Board subBoard = new Board(board);
					subBoard.makeMove(nextPossibleMove, player.color());
					int result = min(minscore,valueMax(board,player,alpha,beta,depth,function,--rcounter));
					if (alpha >= beta) {
						minscore = result;
						maxMove = nextPossibleMove;
					}
				}
			}
                }
                return minscore;
        }

	private int max(int a, int b) {
		return a < b ? b : a;
	}

        private int min(int a,int b) {
                return a > b ? b : a;
        }
}
