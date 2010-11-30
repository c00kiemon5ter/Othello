package othello;

import java.awt.EventQueue;

public class GuiGame implements Game {

        private UIGame uigame;

	@Override
	public void play() {
		EventQueue.invokeLater(uigame = new UIGame());
	}

        public boolean rematch() {
            return uigame.rematch();
        }
}
