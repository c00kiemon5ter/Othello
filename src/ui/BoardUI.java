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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import ui.SquareImgFactory.SquareType;
import javax.swing.JRadioButtonMenuItem;
import utils.Transform;

public final class BoardUI extends JFrame {

	private final Image LOGO = new ImageIcon(getClass().getResource("/ui/images/logo.jpg")).getImage();
	private static final Image rows = Toolkit.getDefaultToolkit().getImage("src/ui/images/rows.png");
	private static final Image cols = Toolkit.getDefaultToolkit().getImage("src/ui/images/cols.png");
	private List<ImageComponent> squares;
	private JPanel board;
	private JPanel statusbar;
	private JLabel whiteStat;
	private JLabel blackStat;
	private JLabel showTurn;
	private SquareImgFactory squareFactory;
	private JMenuItem newgame, exit, about;
	private JRadioButtonMenuItem[] diffbuttons;

	private void initComponents(Container pane) {
		pane.setLayout(new GridBagLayout());
		GridBagConstraints constrains = new GridBagConstraints();

		/* paint the menubar */
		JMenuBar menubar = new JMenuBar();
		/* add menu and items */
		JMenu menu = new JMenu("File");
		menubar.add(menu);
		newgame = new JMenuItem("New Game");
		menu.add(newgame);
		exit = new JMenuItem("Exit");
		menu.add(exit);
		menu = new JMenu("Edit");
		menubar.add(menu);
		JMenu diffmenu = new JMenu("Difficulty");
		diffbuttons = new JRadioButtonMenuItem[4];
		ButtonGroup diffbuttgroup = new ButtonGroup();
		diffbuttons[0] = new JRadioButtonMenuItem("Easy");
		diffbuttgroup.add(diffbuttons[0]);
		diffmenu.add(diffbuttons[0]);
		diffbuttons[1] = new JRadioButtonMenuItem("Normal");
		diffbuttons[1].setSelected(true);
		diffbuttgroup.add(diffbuttons[1]);
		diffmenu.add(diffbuttons[1]);
		diffbuttons[2] = new JRadioButtonMenuItem("Hard");
		diffbuttgroup.add(diffbuttons[2]);
		diffmenu.add(diffbuttons[2]);
		diffbuttons[3] = new JRadioButtonMenuItem("Heroic");
		diffbuttgroup.add(diffbuttons[3]);
		diffmenu.add(diffbuttons[3]);
		menu.add(diffmenu);
		menu = new JMenu("Help");
		menubar.add(menu);
		about = new JMenuItem("About");
		menu.add(about);

		constrains.anchor = GridBagConstraints.PAGE_START;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.gridwidth = 3;
		constrains.gridx = 0;
		constrains.gridy = 0;
		pane.add(menubar, constrains);
		constrains.gridwidth = 0;

		/* add menu item listeners */
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});

		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				JOptionPane.showMessageDialog(board, "Othello is a classic board game!",
							      "About Othello", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		/* paint the cols */
		ImageComponent colsCeil = new ImageComponent(cols);
		constrains.anchor = GridBagConstraints.PAGE_START;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.gridwidth = 3;
		constrains.gridx = 0;
		constrains.gridy = 1;
		pane.add(colsCeil, constrains);
		constrains.gridwidth = 0;

		ImageComponent colsBotm = new ImageComponent(cols);
		constrains.anchor = GridBagConstraints.PAGE_START;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.gridwidth = 3;
		constrains.gridx = 0;
		constrains.gridy = 3;
		pane.add(colsBotm, constrains);
		constrains.gridwidth = 0;

		/* paint the rows */
		ImageComponent rowsLeft = new ImageComponent(rows);
		constrains.anchor = GridBagConstraints.LINE_START;
		constrains.fill = GridBagConstraints.VERTICAL;
		constrains.gridx = 0;
		constrains.gridy = 2;
		pane.add(rowsLeft, constrains);

		ImageComponent rowsRight = new ImageComponent(rows);
		constrains.anchor = GridBagConstraints.LINE_END;
		constrains.fill = GridBagConstraints.VERTICAL;
		constrains.gridx = 2;
		constrains.gridy = 2;
		pane.add(rowsRight, constrains);

		/* paint the squares */
		board = new JPanel(new GridLayout(Board.BOARD_LENGTH, Board.BOARD_WIDTH));
		for (int row = 0; row < Board.BOARD_LENGTH; row++) {
			for (int col = 0; col < Board.BOARD_WIDTH; col++) {
				ImageComponent emptySquare = squareFactory.buildSquare(SquareType.EMPTY);
				board.add(emptySquare);
				squares.add(emptySquare);
			}
		}
		/* paint the start points */
		setSquare(new Point(3, 3), SquareType.WHITE);
		setSquare(new Point(3, 4), SquareType.BLACK);
		setSquare(new Point(4, 3), SquareType.BLACK);
		setSquare(new Point(4, 4), SquareType.WHITE);

		constrains.anchor = GridBagConstraints.CENTER;
		constrains.fill = GridBagConstraints.NONE;
		constrains.gridx = 1;
		constrains.gridy = 2;
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
		constrains.gridy = 4;
		pane.add(statusbar, constrains);
	}

	private void setSquare(Point point, SquareType squareType) {
		ImageComponent imgcomp = squareFactory.buildSquare(squareType);
		int index = Transform.pointToIndex(point);
		squares.set(index, imgcomp);
		board.remove(index);
		board.add(imgcomp, index);
	}

	public void markPossibleMoves(Collection<Point> possibleMoves, SquareType color) {
		for (Point pssblPoint : possibleMoves) {
			setSquare(pssblPoint, color);
		}
		board.revalidate();
	}

	public void unmarkPossibleMoves(Collection<Point> possibleMoves) {
		for (Point pssblPoint : possibleMoves) {
			setSquare(pssblPoint, SquareType.EMPTY);
		}
		board.revalidate();
	}

	public void fill(Collection<Point> filledpoints, SquareType color) {
		for (Point toFill : filledpoints) {
			setSquare(toFill, color);
		}
		board.revalidate();
	}

	public void updateScore(int blackStats, int whiteStats) {
		this.blackStat.setText("Black: " + blackStats);
		this.whiteStat.setText("White: " + whiteStats);
	}

	public void updateTurn(String player) {
		this.showTurn.setText("GO " + player);
	}

	public void declareDraw() {
		this.showTurn.setFont(showTurn.getFont().deriveFont(Font.BOLD));
		this.showTurn.setText("Draw !?");
	}

	public void declareWinner(String winnerName) {
		this.showTurn.setFont(showTurn.getFont().deriveFont(Font.BOLD));
		this.showTurn.setText(winnerName + "!");
	}

	public void notifyLostTurn() {
		JOptionPane.showMessageDialog(this, "No available moves, turn lost",
					      "Well played!", JOptionPane.INFORMATION_MESSAGE);
	}

	public List<ImageComponent> getSquares() {
		return squares;
	}

	public BoardUI() {
		squares = new ArrayList<ImageComponent>(Board.BOARD_LENGTH * Board.BOARD_WIDTH);
		squareFactory = new SquareImgFactory();
		initComponents(this.getContentPane());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Othello");
		this.setIconImage(LOGO);
		this.setResizable(false);
	}

	public JMenuItem getNewGameItem() {
		return newgame;
	}

	public JRadioButtonMenuItem[] getDifficulties() {
		return diffbuttons;
	}
}
