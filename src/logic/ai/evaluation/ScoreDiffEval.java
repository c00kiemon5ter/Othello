package logic.ai.evaluation;

import core.Board;
import core.Player;

/**
 * Maximizes the score distance of players
 * 
 * @author c00kiemon5ter
 */
public class ScoreDiffEval implements Evaluation {

	@Override
	public int evaluate(Board board, Player player) {
		return board.count(player.color()) - board.count(player.opponent().color());
	}
}
