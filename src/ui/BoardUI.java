package ui;

import core.Board;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import ui.DiskComponentFactory.DiskType;
import utils.Transform;

public final class BoardUI extends JFrame {

	private static final Image rows = Toolkit.getDefaultToolkit().getImage("src/ui/images/rows.png");
	private static final Image cols = Toolkit.getDefaultToolkit().getImage("src/ui/images/cols.png");
	private List<ImageComponent> diskComps;
	private JPanel board;
	private JPanel statusbar;
	private JLabel whiteStat;
	private JLabel blackStat;
	private JLabel showTurn;
	private DiskComponentFactory diskFactory;

	private void initComponents(Container pane) {
		pane.setLayout(new GridBagLayout());
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
		board = new JPanel(new GridLayout(Board.BOARD_LENGTH, Board.BOARD_WIDTH));
		for (int row = 0; row < Board.BOARD_LENGTH; row++) {
			for (int col = 0; col < Board.BOARD_WIDTH; col++) {
				ImageComponent emptyDisk = diskFactory.createDisk(DiskComponentFactory.DiskType.EMPTY);
				board.add(emptyDisk);
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
		pane.add(board, constrains);

		/* paint the status bar */
		whiteStat = new JLabel("Heyo!");
		whiteStat.setBorder(BorderFactory.createEtchedBorder());
		whiteStat.setBackground(Color.WHITE);
		whiteStat.setFont(whiteStat.getFont().deriveFont(Font.PLAIN));
		whiteStat.setHorizontalAlignment(JLabel.LEFT);

		showTurn = new JLabel("Game");
		showTurn.setBorder(BorderFactory.createEtchedBorder());
		showTurn.setBackground(Color.WHITE);
		showTurn.setFont(whiteStat.getFont().deriveFont(Font.PLAIN));
		showTurn.setHorizontalAlignment(JLabel.CENTER);

		blackStat = new JLabel("Started");
		blackStat.setBorder(BorderFactory.createEtchedBorder());
		blackStat.setBackground(Color.WHITE);
		blackStat.setFont(blackStat.getFont().deriveFont(Font.PLAIN));
		blackStat.setHorizontalAlignment(JLabel.RIGHT);

		statusbar = new JPanel(new GridLayout());
		statusbar.add(whiteStat);
		statusbar.add(showTurn);
		statusbar.add(blackStat);
		constrains.anchor = GridBagConstraints.PAGE_END;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.weightx = 1;
		constrains.gridx = 1;
		constrains.gridy = 3;
		pane.add(statusbar, constrains);
	}

	private void setDisk(Point point, DiskType disk) {
		ImageComponent imgcomp = diskFactory.createDisk(disk);
		int index = Transform.pointToIndex(point, Board.BOARD_LENGTH);
		diskComps.set(index, imgcomp);
		board.remove(index);
		board.add(imgcomp, index);
	}

	public void markPossibleMoves(Collection<Point> pssbleMoves, DiskType color) {
		for (Point pssblPoint : pssbleMoves) {
			setDisk(pssblPoint, color);
		}
		board.revalidate();
	}

	public void unmarkPossibleMoves(Collection<Point> pssbleMoves) {
		for (Point pssblPoint : pssbleMoves) {
			setDisk(pssblPoint, DiskType.EMPTY);
		}
		board.revalidate();
	}

	public void fill(Collection<Point> filledpoints, DiskType color) {
		for (Point toFill : filledpoints) {
			setDisk(toFill, color);
		}
		board.revalidate();
	}

	public void updateStats(String blackStats, String whiteStats) {
		this.blackStat.setText(blackStats);
		this.whiteStat.setText(whiteStats);
	}

	public void updateTurn(boolean turn) {
		this.showTurn.setText(turn ? "Go White" : "Go Black");
	}

	public void declareWinner(boolean winner) {
		this.showTurn.setFont(showTurn.getFont().deriveFont(Font.BOLD));
		this.showTurn.setText(winner ? "Whites!" : "Blacks!");
	}

	public void declareWinner(String winnerName) {
		this.showTurn.setFont(showTurn.getFont().deriveFont(Font.BOLD));
		this.showTurn.setText(winnerName + "!");
	}

	public void notifyLostTurn() {
		JOptionPane.showMessageDialog(this, "No available moves, turn lost", "Well played!", JOptionPane.INFORMATION_MESSAGE);
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
