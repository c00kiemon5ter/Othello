/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package core;

public enum Player {

	BLACK(DiskState.BLACK),
	WHITE(DiskState.WHITE);
	private String description;
	private DiskState color;
	private int score;

	private Player(DiskState color) {
		this.description = "The " + (color == DiskState.BLACK
					     ? "black" : "white") + " player";
		this.color = color;
		this.score = 2;
	}

	public DiskState color() {
		return color;
	}

	public String description() {
		return description;
	}

	public int score() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return String.format("%s: %d", this.description, this.score);
	}
}
