/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package othello;

public class Controller {

	private Controller() {
		/*
		 * Create two players
		 * Give them names, colors and disks
		 * Create the board
		 * BlackPlayer plays
		 * follow turns
		 * update uis
		 * keep state
		 * Coordination of all objects
		 */
	}

	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}

	private static class ControllerHolder {

		private static final Controller INSTANCE = new Controller();
	}
}
