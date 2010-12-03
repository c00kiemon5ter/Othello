package othello;

import core.Player;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import logic.Controller;
import logic.DifficultyLevel;
import utils.Transform;

/**
 * Cli Game handler. Controls the cli menus and game presentation.
 * CliGame speaks to the controller to handle and edit the board state.
 *
 * @author c00kiemon5ter
 */
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
		System.out.print("\nLet the games begin!\n"
				 + "\n1. Play against Robots!"
				 + "\n2. Play against Friend!"
				 + "\n3. Set Difficulty"
				 + "\n4. Choose Color"
				 + "\n0. Exit");
		System.err.print("\n\nSelect action: ");
		while (true) {
			switch (readInt()) {
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
		System.out.print("\n1. " + DifficultyLevel.EASY.description()
				 + "\n2. " + DifficultyLevel.NORMAL.description()
				 + "\n3. " + DifficultyLevel.HARD.description()
				 + "\n4. " + DifficultyLevel.HEROIC.description()
				 + "\n\nSelect difficutly:");
		while (true) {
			switch (readInt()) {
				case 1:
					controller.setDifficulty(DifficultyLevel.EASY);
					return;
				case 2:
					controller.setDifficulty(DifficultyLevel.NORMAL);
					return;
				case 3:
					controller.setDifficulty(DifficultyLevel.HARD);
					return;
				case 4:
					controller.setDifficulty(DifficultyLevel.HEROIC);
					return;
				default:
					System.err.print("Wrong choice. Try again: ");
			}
		}
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
		controller.init();
		if (System.console().readLine().equalsIgnoreCase("y")) {
			startGame();
		}
	}
}
