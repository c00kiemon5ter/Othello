/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package othello;

import core.Game;

/**
 *
 * 	  A B C D E F G H
 * 	1 o x o x o x o x
 * 	2 x o x o x o x o
 * 	3 o x o x o x o x
 * 	4 x o x o x o x o
 * 	5 o x o x o x o x
 * 	6 x o x o x o x o
 * 	7 o x o x o x o x
 * 	8 x o x o x o x o
 *
 * 	BLACK: 32        WHITE: 32
 *
 * @author c00kiemon5ter
 */
public class Othello {

	// TODO: MinMax + AlphaBeta
	// TODO: AI Interface
	// TODO: UI and connection
	public static void main(String[] args) {
		Game othello = new Game();
		othello.start();
	}
}
