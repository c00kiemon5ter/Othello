/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package ui;

import core.Board;
import javax.swing.JPanel;

public class BoardUI extends JPanel {

	private Board board;

	private void init() {
		/* initialize boardUI components
		 * load images
		 * place first disks
		 */
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public void update() {
		/* update the disksUI on the boardUI 
		 * based on the information given by the board
		 */
		throw new UnsupportedOperationException("Not yet implemented");
	}

	private BoardUI() {
		board = Board.getInstance();
		init();
		this.setVisible(true);
	}

	public static BoardUI getInstance() {
		return BoardUIHolder.INSTANCE;
	}

	private static class BoardUIHolder {

		private static final BoardUI INSTANCE = new BoardUI();
	}
}
