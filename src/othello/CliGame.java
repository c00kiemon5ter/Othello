package othello;

import core.Player;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import logic.Controller;

public class CliGame implements Game {

	Controller controller = Controller.getInstance();

	public CliGame() {
		controller.init();
	}

	@Override
	public void start() {
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
			System.out.printf("%d: %s\t", ++moveIdx, transformNotation(point));
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

	private String transformNotation(Point point) {
		return String.format("%d%c", point.x + 1, point.y + 65);
	}

	private void printBoard() {
		System.out.println(controller.getBoardForm().toString());
	}

	private void whoWon() {
		Player winner = Player.BLACK.score() < Player.WHITE.score()
				? Player.WHITE : Player.BLACK;
		System.out.println("\n\n--- We haz a winnarz! ---\n");
		System.out.println(controller.getBoardForm().toString());
		System.out.printf("~~ %s wins ~~\n", winner);
	}
}
