package logic.ai.evaluation;

import core.Board;
import core.Player;

public class ScoreDiffEval implements Evaluation {

	@Override
	public int evaluate(Board board, Player player) {
		return board.count(player.color()) - board.count(player.opponent().color());
	}
}
