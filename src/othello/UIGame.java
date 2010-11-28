package othello;

import core.Board;
import utils.Transform;
import logic.Controller;
import ui.BoardUI;
import ui.ImageComponent;
import ui.DiskComponentFactory.DiskType;
import java.util.Set;
import java.awt.Point;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class UIGame implements Runnable {

	private Controller controller = Controller.getInstance();
	private BoardUI boardUI;
	private Set<Point> possblMoves;

	public UIGame() {
		this.controller.init();
		initBoardUI();
	}

	private void initBoardUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
		} catch (InstantiationException ex) {
		} catch (IllegalAccessException ex) {
		} catch (UnsupportedLookAndFeelException ex) {
		}
		boardUI = new BoardUI();
		boardUI.setVisible(true);
	}

	@Override
	public void run() {
		if (controller.endOfGame()) {
			gameEnd();
		} else {
			possblMoves = markPossibleMoves();
			if (possblMoves.isEmpty()) {
				lostTurn();
				updateStats();
				run();
			}
		}
	}

	private Set<Point> markPossibleMoves() {
		Set<Point> moves = controller.markPossibleMoves();
		System.out.println(controller.getBoardForm());
		controller.unmarkPossibleMoves(moves);
		if (!moves.isEmpty()) {
			DiskType color = controller.whosTurn() ? DiskType.PSSBLWHT : DiskType.PSSBLBLK;
			boardUI.markPossibleMoves(moves, color);
		}
		updateListeners();
		return moves;
	}

	private void updateStats() {
		controller.updateScores();
		boardUI.updateStats(controller.getBlackStats(), controller.getWhiteStats());
	}

	private void changeTurn() {
		controller.changeTurn();
		boardUI.updateTurn(controller.whosTurn());
	}

	private void lostTurn() {
		boardUI.notifyLostTurn();
		changeTurn();
	}

	private void updateListeners() {
		for (ImageComponent imgComp : boardUI.getDiskComps()) {
			if (imgComp.getMouseListeners().length != 0) {
				continue;
			}
			imgComp.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent evt) {
					clickzWasHappend(evt.getComponent());
				}
			});
		}
	}

	private void clickzWasHappend(Component imgComp) {
		int index = boardUI.getDiskComps().indexOf(imgComp);
		Point selectedMove = Transform.indexToPoint(index, Board.BOARD_LENGTH);
		if (possblMoves.contains(selectedMove)) {
			boardUI.unmarkPossibleMoves(possblMoves);
			makeMove(selectedMove);
			updateStats();
			changeTurn();
			run();
		}
	}

	private void makeMove(Point move) {
		DiskType color = controller.whosTurn() ? DiskType.WHITE : DiskType.BLACK;
		Set<Point> disksToChange = controller.makeMove(move);
		disksToChange.add(move);
		boardUI.fill(disksToChange, color);
	}

	private void gameEnd() {
		updateStats();
		boardUI.declareWinner(controller.getWinnerName());
	}
}
