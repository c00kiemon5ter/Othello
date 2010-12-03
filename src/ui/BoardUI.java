package ui;

import core.Board;
import core.Player;
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
import logic.DifficultyLevel;
import othello.Othello;
import utils.Transform;

/**
 * Board graphical representation
 *
 * @author c00kiemon5ter
 */
public final class BoardUI extends JFrame {

	private final Image LOGO = new ImageIcon(getClass().getResource("/ui/images/logo.jpg")).getImage();
	private static final Image rows = Toolkit.getDefaultToolkit().getImage("src/ui/images/rows.png");
	private static final Image cols = Toolkit.getDefaultToolkit().getImage("src/ui/images/cols.png");
	private static boolean vsRobots = true;
	private static Player human = Player.BLACK;
	private List<ImageComponent> squares;
	private JPanel board;
	private JLabel whiteStat;
	private JLabel blackStat;
	private JLabel showTurn;
	private JRadioButtonMenuItem[] diffbuttons;
	private JMenuItem newgame;

	public BoardUI() {
		squares = new ArrayList<ImageComponent>(Board.BOARD_LENGTH * Board.BOARD_WIDTH);
		initComponents(this.getContentPane());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle(Othello.class.getSimpleName());
		this.setIconImage(LOGO);
		this.setResizable(false);
	}

	private void initComponents(Container pane) {
		pane.setLayout(new GridBagLayout());
		GridBagConstraints constrains = new GridBagConstraints();
		ButtonGroup buttongroup = new ButtonGroup();
		JRadioButtonMenuItem radiobutton;

		/* paint the menubar */
		JMenuBar menubar = new JMenuBar();
		/* add menu and items */
		JMenu menu = new JMenu("File");
		menubar.add(menu);
		JMenuItem exit, about;

		newgame = new JMenuItem("New Game");
		menu.add(newgame);

		exit = new JMenuItem("Exit");
		menu.add(exit);
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});


		menu = new JMenu("Edit");
		menubar.add(menu);

		JMenu submenu = new JMenu("Difficulty");
		diffbuttons = new JRadioButtonMenuItem[4];
		diffbuttons[0] = new JRadioButtonMenuItem(DifficultyLevel.EASY.description());
		buttongroup.add(diffbuttons[0]);
		submenu.add(diffbuttons[0]);
		diffbuttons[1] = new JRadioButtonMenuItem(DifficultyLevel.NORMAL.description());
		diffbuttons[1].setSelected(true);
		buttongroup.add(diffbuttons[1]);
		submenu.add(diffbuttons[1]);
		diffbuttons[2] = new JRadioButtonMenuItem(DifficultyLevel.HARD.description());
		buttongroup.add(diffbuttons[2]);
		submenu.add(diffbuttons[2]);
		diffbuttons[3] = new JRadioButtonMenuItem(DifficultyLevel.HEROIC.description());
		buttongroup.add(diffbuttons[3]);
		submenu.add(diffbuttons[3]);
		menu.add(submenu);

		submenu = new JMenu("Player Color");
		buttongroup = new ButtonGroup();
		radiobutton = new JRadioButtonMenuItem(Player.BLACK.toString());
		radiobutton.setSelected(true);
		radiobutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				human = Player.BLACK;
			}
		});
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		radiobutton = new JRadioButtonMenuItem(Player.WHITE.toString());
		radiobutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				human = Player.WHITE;
			}
		});
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		menu.add(submenu);

		submenu = new JMenu("Oppenent Inteligence");
		buttongroup = new ButtonGroup();
		radiobutton = new JRadioButtonMenuItem("Robot Invader");
		radiobutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vsRobots = true;
			}
		});
		radiobutton.setSelected(true);
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		radiobutton = new JRadioButtonMenuItem("Human brain");
		radiobutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vsRobots = false;
			}
		});
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		menu.add(submenu);

		menu = new JMenu("Help");
		menubar.add(menu);

		about = new JMenuItem("About");
		menu.add(about);
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				JOptionPane.showMessageDialog(board, "Othello is a classic board game!",
							      "About Othello", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		constrains.anchor = GridBagConstraints.PAGE_START;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.gridwidth = 3;
		constrains.gridx = 0;
		constrains.gridy = 0;
		pane.add(menubar, constrains);
		constrains.gridwidth = 0;

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
				ImageComponent emptySquare = SquareImgFactory.buildSquare(SquareType.EMPTY);
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

		JPanel statusbar = new JPanel(new GridLayout());
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
		ImageComponent imgcomp = SquareImgFactory.buildSquare(squareType);
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

	public void notifyLostTurn(Player player) {
		JOptionPane.showMessageDialog(this, "No available moves for, " + player.toString()
						    + " turn lost", "Well played!", JOptionPane.INFORMATION_MESSAGE);
	}

	public List<ImageComponent> getSquares() {
		return squares;
	}

	public JMenuItem getNewGameItem() {
		return newgame;
	}

	public JRadioButtonMenuItem[] getDifficulties() {
		return diffbuttons;
	}

	public Player getPlayerSelection() {
		return human;
	}

	public boolean againstRobots() {
		return vsRobots;
	}
}
