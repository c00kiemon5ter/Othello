package logic.ai;

import core.Board;
import core.Player;

interface Searcher {

	Node search(final Board board, final Player player, final int depth, final Evaluation function);
}
