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

	private Controller controller = Controller.getInstance();
	private Player humanoid;
	private boolean vsAi;

	public CliGame() {
		controller.init();
		humanoid = Player.BLACK;
	}

	@Override
	public void play() {
		while (true) {
			switch (menu()) {
				case 0:
					System.exit(0);
				case 1:
					vsAi = true;
					startGame();
					break;
				case 2:
					vsAi = false;
					startGame();
					break;
				case 3:
					setDifficulty();
					break;
				case 4:
					chooseColor();
					break;
				default:
					System.err.print("Wrong choice. Try again\n");
					break;
			}
		}
	}

	public void startGame() {
		Set<Point> possblMoves;
		Point move;
		while (!controller.endOfGame()) {
			possblMoves = controller.markPossibleMoves();
			System.out.println(controller.boardWithTurn());
			controller.unmarkPossibleMoves();
			if (!possblMoves.isEmpty()) {
				if (controller.currentPlayer() == humanoid) {
					move = selectMove(possblMoves);
					controller.makeMove(move);
				} else if (controller.currentPlayer() == humanoid.opponent()) {
					move = vsAi ? controller.evalMove() : selectMove(possblMoves);
					controller.makeMove(move);
				}
			} else {
				System.out.printf("Whoops! %s lost his turn\n", controller.currentPlayer());
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
			if (vsAi) {
				System.out.printf("\n==> Robots conquered teh worldz\n");
			}
		}
	}

	public void rematch() {
		System.out.print("\nReady for another game? [y/]");
		if (System.console().readLine().equalsIgnoreCase("y")) {
			controller.init();
			startGame();
		}
	}

	private int menu() {
		System.out.print("\nLet the games begin!\n"
				 + "\n1. Play against Robots!"
				 + "\n2. Play against Friend!"
				 + "\n3. Set Difficulty"
				 + "\n4. Choose Color"
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
					humanoid = Player.BLACK;
					return;
				case 2:
					humanoid = Player.WHITE;
					return;
				default:
					System.err.print("Wrong choice. Try again: ");
			}
		}
	}
}
