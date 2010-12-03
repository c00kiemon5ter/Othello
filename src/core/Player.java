package core;

/**
 * Players. There are two players in the game. Black and White.
 *
 * @author c00kiemon5ter
 */
public enum Player {

	BLACK(SquareState.BLACK),
	WHITE(SquareState.WHITE);
	private SquareState color;

	private Player(SquareState color) {
		this.color = color;
	}

	public Player opponent() {
		return this == BLACK ? WHITE : BLACK;
	}

	public SquareState color() {
		return color;
	}
}
