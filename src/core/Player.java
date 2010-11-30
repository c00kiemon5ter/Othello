package core;

public enum Player {

	BLACK(DiskState.BLACK),
	WHITE(DiskState.WHITE);
	private DiskState color;
	private int score;

	private Player(DiskState color) {
		this.color = color;
	}

	public Player opponent() {
		return this == BLACK ? WHITE : BLACK;
	}

	public DiskState color() {
		return color;
	}

	public int score() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String stats() {
		return String.format("%s: %d", this, this.score);
	}

	public void init() {
		this.score = 2;
	}
}