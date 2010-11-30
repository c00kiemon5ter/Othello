package othello;

import core.Player;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import logic.Controller;
import logic.DifficultyLevel;
import utils.Transform;

public class CliGame implements Game {

	Controller controller = Controller.getInstance();
	Player human;

	public CliGame() {
		controller.init();
		human = Player.BLACK;
	}

	@Override
	public void play() {
		while (true) {
			switch (menu()) {
				case 0:
					System.exit(0);
				case 1:
					run();
					break;
				case 2:
					setDifficulty();
					break;
				case 3:
					chooseColor();
					break;
				default:
					System.err.print("Wrong choice. Try again\n");
					break;
			}
		}
	}

	public void run() {
		Set<Point> possblMoves;
		Point move;
		while (!controller.endOfGame()) {
			possblMoves = controller.markPossibleMoves();
			System.out.println(controller.boardWithTurn());
			controller.unmarkPossibleMoves();
			if (controller.currentPlayer() == human) {
				if (!possblMoves.isEmpty()) {
					move = selectMove(possblMoves);
					controller.makeMove(move);
				}
			} else if (controller.currentPlayer() == human.opponent()) {
				if (!possblMoves.isEmpty()) {
					Point bestMove = controller.evalMove();
					controller.makeMove(bestMove);
				}
			}
			controller.changeTurn();
		}
		declareWinnarz();
		rematch();
	}

	Point selectMove(Set<Point> moves) {
		List<Point> select = new ArrayList<Point>(moves);
		int moveIdx = 0;
		for (Point point : select) {
			System.out.printf("%d: %s\t", ++moveIdx, Transform.toBoardNotation(point));
		}
		System.out.print("\nSelect move: ");
		moveIdx = readInt();
		while (moveIdx <= 0 || moveIdx > select.size()) {
			System.err.print("Wrong choice. Try again: ");
			moveIdx = readInt();
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

	public void rematch() {
		System.out.print("\nReady for another game? [y/]");
		if (System.console().readLine().equalsIgnoreCase("y")) {
			controller.init();
			run();
		}
	}

	private int menu() {
		System.out.print("\nLet the games begin!\n"
				 + "\n1. Play!"
				 + "\n2. Set Difficulty"
				 + "\n3. Choose Color"
				 + "\n0. Exit");
		System.err.print("\n\nSelect action: ");
		return readInt();
	}

	private int readInt() {
		int choice;
		while (true) {
			String line = System.console().readLine();
			try {
				choice = Integer.parseInt(line);
			} catch (NumberFormatException nfe) {
				System.err.print("Wrong choice. Try again: ");
				continue;
			}
			break;
		}
		return choice;
	}

	private void setDifficulty() {
		int level = 0;
		System.out.print("\n1. " + DifficultyLevel.EASY.description()
				 + "\n2. " + DifficultyLevel.NORMAL.description()
				 + "\n3. " + DifficultyLevel.HARD.description()
				 + "\n4. " + DifficultyLevel.HEROIC.description()
				 + "\n\nSelect difficutly:");
		while (level == 0) {
			switch (readInt()) {
				case 1:
					level = DifficultyLevel.EASY.level();
					break;
				case 2:
					level = DifficultyLevel.NORMAL.level();
					break;
				case 3:
					level = DifficultyLevel.HARD.level();
					break;
				case 4:
					level = DifficultyLevel.HEROIC.level();
					break;
				default:
					System.err.print("Wrong choice. Try again: ");
			}
		}
		controller.setDifficulty(level);
	}

	private void chooseColor() {
		System.out.print("\n1. " + Player.BLACK
				 + "\n2. " + Player.WHITE
				 + "\n\nSelect color:");
		while (true) {
			switch (readInt()) {
				case 1:
					human = Player.BLACK;
					return;
				case 2:
					human = Player.WHITE;
					return;
				default:
					System.err.print("Wrong choice. Try again: ");
			}
		}
	}
}
