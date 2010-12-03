package logic.ai.evaluation;

import core.Board;
import core.Player;

/**
 * Evaluation Function interface. Each evaluation method must implement this
 * 
 * @author c00kiemon5ter
 */
public interface Evaluation {

	public int evaluate(final Board board, final Player player);
}
