/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package core;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Controller {

	/**
	 * turn has two values -> boolean
	 * false : if it's black player's turn
	 * true  : if it's white player's turn
	 */
	private boolean turn;
	private Board board;
	private MoveExplorer explorer;
	private Scanner scan;

	private Controller() {
		turn = false;	/* black player plays first */
		board = Board.getInstance();
		scan = new Scanner(System.in);
	}

	public void operate() {
		List<Point> moves;
		Point move;
		while (!endOfGame()) {
			moves = markPossibleMoves();
			System.out.println(Player.BLACK.stats() + '\t' + Player.WHITE.stats());
			System.out.println(board.toString());
			if (!moves.isEmpty()) {
				move = selectMove(moves);
				moves.remove(move);
				makeMove(move);
				updateScores();
				for (Point possiblePoint : moves) {
					board.getDisk(possiblePoint).setState(DiskState.EMPTY);
				}
			}
			/* change turn */
			turn = !turn;
		}
		System.out.println(board.toString());
		System.out.println(Player.BLACK.stats() + '\t' + Player.WHITE.stats());
	}

	private List<Point> markPossibleMoves() {
		explorer = turn ? MoveExplorer.WHITEEXPLORER : MoveExplorer.BLACKEXPLORER;
		List<Point> moves = new LinkedList<Point>(explorer.explore());
		for (Point possiblePoint : moves) {
			board.getDisk(possiblePoint).setState(DiskState.PSSBL);
		}
		return moves;
	}

	private Point selectMove(List<Point> moves) {
		String line;
		int moveIdx = 0;
		for (Point point : moves) {
			System.out.printf("%d: %s\t", ++moveIdx, coordTransform(point));
		}
		System.out.print("\nSelect move: ");
		while (true) {
			line = scan.nextLine();
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

	private String coordTransform(Point point) {
		return String.format("%d%c", point.x + 1, point.y + 65);
	}

	private void makeMove(Point move) {
		board.getDisk(move).setState(turn ? DiskState.WHITE : DiskState.BLACK);
		// TODO: expand - change affected disks
	}

	private void updateScores() {
		int score = 0;
		score = getScore(DiskState.BLACK);
		Player.BLACK.setScore(score);
		score = getScore(DiskState.WHITE);
		Player.WHITE.setScore(score);
	}

	public int getScore(DiskState color) {
		int score = 0;
		for (Disk[] diskrow : board.getDisks()) {
			for (Disk disk : diskrow) {
				if (disk.getState() == color) {
					score++;
				}
			}
		}
		return score;
	}

	/**
	 * Game stops if <br/>
	 * <ol>
	 * <li> board is full</li>
	 * <li> one's score is 0/zero</li>
	 * <li> none has a valid next move (handled by {@code operate()})</li>
	 * </ol>
	 */
	private boolean endOfGame() {
		return checkFullBoard() || checkZeroScore();
	}

	private boolean checkFullBoard() {
		for (Disk[] row : board.getDisks()) {
			for (Disk disk : row) {
				if (disk.getState() == DiskState.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkZeroScore() {
		return Player.BLACK.score() == 0 || Player.WHITE.score() == 0;
	}

	private static class ControllerHolder {

		private static final Controller INSTANCE = new Controller();
	}

	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}
}
