package logic.ai.searchers;

import core.Board;
import core.Player;
import logic.ai.evaluation.Evaluation;

/**
 * TODO: clean up - Board Controller NegaMax CliGame
 *
 * TODO: Select Human vs [Human|Computer]
 *	-> 1on1Game + AIGame ? Make SelectMove class
 *
 * TODO: Ask to restart game
 * TODO: Select which color the Computer is
 * TODO: Select difficulty --> increase depth
 *	-> cli menu + gui menubar
 *
 * TODO: Implement Gui Computer Opponent
 * TODO: Chose evaluation function
 * TODO: What happens on lost turns ?
 *
 * TODO: Implement NegaMan-AB MiniMax-AB Negascout
 * TODO: Efficient use of a Global Hash Table <Move, Max>
 */
interface Searcher {

	int search(final Board board, final Player player, final int alpha, final int beta, final int depth, final Evaluation function);
}
