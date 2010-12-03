package logic.ai.evaluation;

import core.Board;
import core.Player;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds weight to the score if the new state has corners owned by the player
 * 
 * @author c00kiemon5ter
 */
public class ScoreCornerWeightEval implements Evaluation {

	private Set<Point> corners;
	private int weight;

	public ScoreCornerWeightEval(int weight) {
		this.weight = weight;
		this.corners = new HashSet<Point>(4);
		this.corners.add(new Point(0, 0));
		this.corners.add(new Point(Board.BOARD_LENGTH, 0));
		this.corners.add(new Point(0, Board.BOARD_WIDTH));
		this.corners.add(new Point(Board.BOARD_LENGTH, Board.BOARD_WIDTH));
	}

	public ScoreCornerWeightEval() {
		this(1);
	}

	@Override
	public int evaluate(Board board, Player player) {
		int score = board.count(player.color());
		for (Point p : corners) {
			if (board.getSquareState(p) == player.color()) {
				score += weight;
			}
		}
		return score;
	}
}
