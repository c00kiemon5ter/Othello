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
			possblMoves = controller.markPossibleMoves();
			printBoard();
			controller.unmarkPossibleMoves(possblMoves);
			if (!possblMoves.isEmpty()) {
				move = selectMove(possblMoves);
				controller.makeMove(move);
				controller.updateScores();
			}
			controller.changeTurn();
		}
		whoWon();
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

	private void printBoard() {
		System.out.println(controller.getBoardForm());
	}

	private void whoWon() {
		System.out.println(controller.getBoardForm().toString());
		if (controller.isDraw()) {
			System.out.println("\n:: We haz a draw!?");
			System.out.println("\n==> No party for you :( ");
		} else {
			System.out.println("\n:: We haz a winnarz!");
			System.out.printf("\n==> %s wins\n", controller.getWinnerName());
		}
	}
}
