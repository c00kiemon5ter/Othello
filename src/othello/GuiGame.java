package othello;

import java.awt.EventQueue;

public class GuiGame implements Game {

	@Override
	public void play() {
		EventQueue.invokeLater(new UIGame());
	}
}
