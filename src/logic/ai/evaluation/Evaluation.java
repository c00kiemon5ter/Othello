package logic.ai.evaluation;

import core.Board;
import core.Player;

public interface Evaluation {

	public int evaluate(final Board board, final Player player);
}
