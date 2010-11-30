package logic.ai.evaluation;

import core.Board;
import core.Player;

/**
 *
 * @author c00kiemon5ter
 */
public class ScoreEval implements Evaluation {

	@Override
	public int evaluate(final Board board, final Player player) {
		return board.getScore(player.color());
	}
}
