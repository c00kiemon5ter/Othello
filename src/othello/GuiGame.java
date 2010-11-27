package othello;

import core.Board;
import ui.BoardUI;
import java.awt.Point;
import java.util.Set;
import java.awt.Component;
import ui.DiskComponentFactory.DiskType;
import ui.ImageComponent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import logic.Controller;

public class GuiGame implements Game {

	private Controller controller = Controller.getInstance();
	private BoardUI boardUI;
	private Set<Point> possblMoves;

	public GuiGame() {
		controller.init();
		boardUI = new BoardUI();
		boardUI.setVisible(true);
	}

	@Override
	public void start() {
		if (!controller.endOfGame()) {
			possblMoves = controller.markPossibleMoves();
			controller.unmarkPossibleMoves(possblMoves);
			if (!possblMoves.isEmpty()) {
				DiskType color = controller.turn() ? DiskType.PSSBLWHT : DiskType.PSSBLBLK;
				boardUI.markPossibleMoves(possblMoves, color);
			}
			/*else {
			 *	controller.changeTurn();
			 *	start(); // hmmm mm
			}*/
		}
		grabListeners();
	}

	private void grabListeners() {
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
		Point selectedMove = indexToPoint(index);
		if (possblMoves.contains(selectedMove)) {
			boardUI.unmarkPossibleMoves(possblMoves);
			makeMove(selectedMove);
		}
	}

	private void makeMove(Point move) {
		DiskType color = controller.turn() ? DiskType.WHITE : DiskType.BLACK;
		Set<Point> affectedPoints = controller.makeMove(move);
		affectedPoints.add(move);
		boardUI.fill(affectedPoints, color);
		controller.updateScores();
		controller.changeTurn();
		start();
	}

	private Point indexToPoint(int idx) {
		return new Point(idx / Board.BOARD_LENGTH, idx % Board.BOARD_LENGTH);
	}
}
