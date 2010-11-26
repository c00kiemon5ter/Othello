package core;

import java.awt.Point;
import java.util.List;

public class Game {

	Controller controller = Controller.getInstance();
	Board board = Board.getInstance();

	public Game() {
		controller.init();
	}

	public void start() {
		List<Point> moves;
		Point move;
		while (!controller.endOfGame()) {
			moves = controller.markPossibleMoves();
			printBoard();
			controller.unmarkPossibleMoves(moves);
			if (!moves.isEmpty()) {
				move = selectMove(moves);
				controller.makeMove(move);
				controller.updateScores();
			}
			controller.changeTurn();
		}
		whoWon();
	}

	Point selectMove(List<Point> moves) {
		String line;
		int moveIdx = 0;
		for (Point point : moves) {
			System.out.printf("%d: %s\t", ++moveIdx, transformNotation(point));
		}
		System.out.print("\nSelect move: ");
		while (true) {
			line = System.console().readLine();
			try {
				moveIdx = Integer.parseInt(line);
			} catch (NumberFormatException nfe) {
				System.err.print("Wrong choice. Try again: ");
				continue;
			}
			if (moveIdx <= 0 || moveIdx > moves.size()) {
				System.err.print("Wrong choice. Try again: ");
				continue;
			}
			break;
		}
		return moves.get(moveIdx - 1);
	}

	private String transformNotation(Point point) {
		return String.format("%d%c", point.x + 1, point.y + 65);
	}

	private void printBoard() {
		System.out.printf("%s\t%s\n", Player.BLACK.stats(), Player.WHITE.stats());
		System.out.println(board.toString());
	}

	private void whoWon() {
		Player winner = Player.BLACK.score() < Player.WHITE.score()
				? Player.WHITE : Player.BLACK;
		System.out.println("\n--- We got a winner! ---");
		System.out.println(board.toString());
		System.out.println(Player.BLACK.stats() + '\t' + Player.WHITE.stats());
		System.out.printf("%s wins with %d", winner, winner.score());
	}
}
