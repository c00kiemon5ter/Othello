package othello;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import logic.Controller;
import utils.Transform;

public class CliGame implements Game {

	Controller controller = Controller.getInstance();

	public CliGame() {
		controller.init();
	}

	@Override
	public void play() {
		Set<Point> possblMoves;
		Point move;
		while (!controller.endOfGame()) {
			switch (controller.who()) {
				case BLACK:
					possblMoves = controller.markPossibleMoves();
					System.out.println(controller.boardWithTurn());
					controller.unmarkPossibleMoves();
					if (!possblMoves.isEmpty()) {
						move = selectMove(possblMoves);
						controller.makeMove(move);
					}
					break;
				case WHITE: /* computer */
					possblMoves = controller.markPossibleMoves();
					System.out.println(controller.boardWithTurn());
					controller.unmarkPossibleMoves();
					if (!possblMoves.isEmpty()) {
						Point bestMove = controller.evalMove();
						controller.makeMove(bestMove);
					}
					break;
			}
			controller.changeTurn();
		}
		declareWinnarz();
	}

	Point selectMove(Set<Point> moves) {
		List<Point> select = new ArrayList<Point>(moves);
		int moveIdx = 0;
		for (Point point : select) {
			System.out.printf("%d: %s\t", ++moveIdx, Transform.toBoardNotation(point));
		}
		System.out.print("\nSelect move: ");
		while (true) {
			String line = System.console().readLine();
			try {
				moveIdx = Integer.parseInt(line);
			} catch (NumberFormatException nfe) {
				System.err.print("Wrong choice. Try again: ");
				continue;
			}
			if (moveIdx <= 0 || moveIdx > select.size()) {
				System.err.print("Wrong choice. Try again: ");
				continue;
			}
			break;
		}
		return select.get(moveIdx - 1);
	}

	private void declareWinnarz() {
		System.out.println(controller.boardWithTurn());
		if (controller.isDraw()) {
			System.out.println("\n:: We haz a draw!?");
			System.out.println("\n==> No party for you >:(");
		} else {
			System.out.println("\n:: We haz a winnarz!");
			System.out.printf("\n==> %s wins\n", controller.getWinner());
		}
	}
}
