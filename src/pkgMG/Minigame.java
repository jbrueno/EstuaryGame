package pkgMG;

import pkgEnum.GameType;
import pkgMover.Mover;
import pkgEnum.GameState;
import javafx.scene.input.MouseEvent;

public abstract class Minigame {

	GameType currGame;
	int score;
	int totalTime;
	GameState gs = GameState.START;
	
	abstract void update(MouseEvent me);
	abstract void handleBorderCollision(Mover m);
	
	
	public GameType getGameType() {
		return this.currGame;
	}
}
