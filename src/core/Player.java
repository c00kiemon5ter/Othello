/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package core;

public class Player {

	private String nickname;
	private final boolean color;
	private int score;

	public Player(String nickname, boolean color) {
		this.nickname = nickname;
		this.color = color;
	}

	public void turn() {
		// discover available moves
	}

}
