package othello;

import java.awt.EventQueue;

/**
 * Gui Game. All events must happen in the event queue
 * to avoid race conditions and hanging the ui thread.
 *
 * @author c00kiemon5ter
 */
public class GuiGame implements Game {

	@Override
	public void play() {
		EventQueue.invokeLater(new UIGame());
	}
}
