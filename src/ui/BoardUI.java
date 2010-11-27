/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package ui;

import core.Board;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardUI extends JFrame {

	private Board board = Board.getInstance();
	private static final Image rows = Toolkit.getDefaultToolkit().getImage("src/ui/images/rows.png");
	private static final Image cols = Toolkit.getDefaultToolkit().getImage("src/ui/images/cols.png");
	private List<ImageComponent> disks;
	private JPanel clickableBoard;

	private void initComponents(Container pane) {
		pane.setLayout(new GridBagLayout());
		clickableBoard = new JPanel(new GridLayout(Board.BOARD_LENGTH, Board.BOARD_WIDTH));
		GridBagConstraints constrains = new GridBagConstraints();
		DiskComponentFactory diskFactory = new DiskComponentFactory();

		ImageComponent colsCeil = new ImageComponent(cols);
		constrains.anchor = GridBagConstraints.PAGE_START;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.gridwidth = 3;
		constrains.gridx = 0;
		constrains.gridy = 0;
		pane.add(colsCeil, constrains);
		constrains.gridwidth = 0;

		ImageComponent colsBotm = new ImageComponent(cols);
		constrains.anchor = GridBagConstraints.PAGE_START;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.gridwidth = 3;
		constrains.gridx = 0;
		constrains.gridy = 2;
		pane.add(colsBotm, constrains);
		constrains.gridwidth = 0;

		ImageComponent rowsLeft = new ImageComponent(rows);
		constrains.anchor = GridBagConstraints.LINE_START;
		constrains.fill = GridBagConstraints.VERTICAL;
		constrains.gridx = 0;
		constrains.gridy = 1;
		pane.add(rowsLeft, constrains);

		ImageComponent rowsRight = new ImageComponent(rows);
		constrains.anchor = GridBagConstraints.LINE_END;
		constrains.fill = GridBagConstraints.VERTICAL;
		constrains.gridx = 2;
		constrains.gridy = 1;
		pane.add(rowsRight, constrains);

		for (int row = 0; row < Board.BOARD_LENGTH; row++) {
			for (int col = 0; col < Board.BOARD_WIDTH; col++) {
				ImageComponent emptyDisk = diskFactory.createDisk(DiskComponentFactory.DiskType.EMPTY);
				clickableBoard.add(emptyDisk);
				disks.add(emptyDisk);
			}
		}

		setDisk(new Point(4, 4), diskFactory.createDisk(DiskComponentFactory.DiskType.WHITE));
		setDisk(new Point(4, 5), diskFactory.createDisk(DiskComponentFactory.DiskType.BLACK));
		setDisk(new Point(5, 4), diskFactory.createDisk(DiskComponentFactory.DiskType.BLACK));
		setDisk(new Point(5, 5), diskFactory.createDisk(DiskComponentFactory.DiskType.WHITE));

		constrains.anchor = GridBagConstraints.CENTER;
		constrains.fill = GridBagConstraints.NONE;
		constrains.gridx = 1;
		constrains.gridy = 1;
		pane.add(clickableBoard, constrains);
	}

	private void setDisk(Point point, ImageComponent disk) {
		disks.set(8 * (point.y - 1) + (point.x - 1), disk);
		clickableBoard.remove(8 * (point.y - 1) + (point.x - 1));
		clickableBoard.add(disk, 8 * (point.y - 1) + (point.x - 1));
	}

	private BoardUI() {
		disks = new ArrayList<ImageComponent>(Board.BOARD_LENGTH * Board.BOARD_WIDTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Othello");
		this.setIconImage(new ImageIcon(getClass().getResource("/ui/images/logo.jpg")).getImage());
		initComponents(this.getContentPane());
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new BoardUI().setVisible(true);
			}
		});
	}

	public static BoardUI getInstance() {
		return BoardUIHolder.INSTANCE;
	}

	private static class BoardUIHolder {

		private static final BoardUI INSTANCE = new BoardUI();
	}
}
