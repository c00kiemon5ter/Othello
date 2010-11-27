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
import java.util.Collection;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ui.DiskComponentFactory.DiskType;

public final class BoardUI extends JFrame {

	private static final Image rows = Toolkit.getDefaultToolkit().getImage("src/ui/images/rows.png");
	private static final Image cols = Toolkit.getDefaultToolkit().getImage("src/ui/images/cols.png");
	private List<ImageComponent> diskComps;
	private JPanel clickableBoard;
	private DiskComponentFactory diskFactory;

	private void initComponents(Container pane) {
		pane.setLayout(new GridBagLayout());
		clickableBoard = new JPanel(new GridLayout(Board.BOARD_LENGTH, Board.BOARD_WIDTH));
		GridBagConstraints constrains = new GridBagConstraints();

		/* paint the cols */
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

		/* paint the rows */
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

		/* paint the disks */
		for (int row = 0; row < Board.BOARD_LENGTH; row++) {
			for (int col = 0; col < Board.BOARD_WIDTH; col++) {
				ImageComponent emptyDisk = diskFactory.createDisk(DiskComponentFactory.DiskType.EMPTY);
				clickableBoard.add(emptyDisk);
				diskComps.add(emptyDisk);
			}
		}
		/* paint the start points */
		setDisk(new Point(3, 3), DiskType.WHITE);
		setDisk(new Point(3, 4), DiskType.BLACK);
		setDisk(new Point(4, 3), DiskType.BLACK);
		setDisk(new Point(4, 4), DiskType.WHITE);

		constrains.anchor = GridBagConstraints.CENTER;
		constrains.fill = GridBagConstraints.NONE;
		constrains.gridx = 1;
		constrains.gridy = 1;
		pane.add(clickableBoard, constrains);
	}

	private void setDisk(Point point, DiskType disk) {
		ImageComponent imgcomp = diskFactory.createDisk(disk);
		int index = Board.BOARD_LENGTH * point.x + point.y;
		diskComps.set(index, imgcomp);
		clickableBoard.remove(index);
		clickableBoard.add(imgcomp, index);
	}

	public void markPossibleMoves(Collection<Point> pssbleMoves, DiskType color) {
		for (Point pssblPoint : pssbleMoves) {
			setDisk(pssblPoint, color);
		}
		clickableBoard.revalidate();
	}

	public void unmarkPossibleMoves(Collection<Point> pssbleMoves) {
		for (Point pssblPoint : pssbleMoves) {
			setDisk(pssblPoint, DiskType.EMPTY);
		}
		clickableBoard.revalidate();
	}

	public void fill(Collection<Point> filledpoints, DiskType color) {
		for (Point toFill : filledpoints) {
			setDisk(toFill, color);
		}
		clickableBoard.revalidate();
	}

	public List<ImageComponent> getDiskComps() {
		return diskComps;
	}

	public void init() {
		diskComps = new ArrayList<ImageComponent>(Board.BOARD_LENGTH * Board.BOARD_WIDTH);
		diskFactory = new DiskComponentFactory();
		initComponents(this.getContentPane());
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public BoardUI() {
		init();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Othello");
		this.setIconImage(new ImageIcon(getClass().getResource("/ui/images/logo.jpg")).getImage());
		this.setResizable(false);
	}
}
