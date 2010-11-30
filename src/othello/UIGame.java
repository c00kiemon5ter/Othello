package othello;

import core.SquareState;
import java.awt.event.ActionEvent;
import utils.Transform;
import logic.Controller;
import ui.BoardUI;
import ui.ImageComponent;
import ui.SquareImgFactory.SquareType;
import java.util.Set;
import java.awt.Point;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIGame implements Runnable {

	private Controller controller = Controller.getInstance();
	private BoardUI boardUI;
	private Set<Point> possblMoves;

	public UIGame() {
		this.controller.init();
		initBoardUI();
	}

	private void initBoardUI() {
		boardUI = new BoardUI();
		boardUI.setVisible(true);
		boardUI.getNewGameItel().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boardUI.dispose();
				controller.init();
				initBoardUI();
				run();
			}
		});
	}

	@Override
	public void run() {
		if (controller.endOfGame()) {
			gameEnd();
		} else {
			possblMoves = markPossibleMoves();
			if (possblMoves.isEmpty()) {
				pass();
				run();
			}
		}
	}

	private Set<Point> markPossibleMoves() {
		Set<Point> moves = controller.markPossibleMoves();
		controller.unmarkPossibleMoves();
		if (!moves.isEmpty()) {
			SquareType color = controller.who().color() == SquareState.WHITE
					   ? SquareType.PSSBLWHT : SquareType.PSSBLBLK;
			boardUI.markPossibleMoves(moves, color);
		}
		updateListeners();
		return moves;
	}

	private void updateStats() {
		boardUI.updateScore(controller.getBlackScore(), controller.getWhiteScore());
	}

	private void changeTurn() {
		controller.changeTurn();
		boardUI.updateTurn(controller.who().toString());
	}

	private void lostTurn() {
		boardUI.notifyLostTurn();
		changeTurn();
	}

	private void pass() {
		lostTurn();
		updateStats();
	}

	private void updateListeners() {
		for (ImageComponent imgComp : boardUI.getSquares()) {
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
		int index = boardUI.getSquares().indexOf(imgComp);
		Point selectedMove = Transform.indexToPoint(index);
		if (possblMoves.contains(selectedMove)) {
			boardUI.unmarkPossibleMoves(possblMoves);
			makeMove(selectedMove);
			updateStats();
			changeTurn();
			run();
		}
	}

	private void makeMove(Point move) {
		SquareType color = controller.who().color() == SquareState.WHITE
				   ? SquareType.WHITE : SquareType.BLACK;
		Set<Point> squaresToChange = controller.makeMove(move);
		boardUI.fill(squaresToChange, color);
	}

	private void gameEnd() {
		updateStats();
		if (controller.isDraw()) {
			boardUI.declareDraw();
		} else {
			boardUI.declareWinner(controller.getWinner().toString());
		}
	}
}
