package core;

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
