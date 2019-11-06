package pkgMG;

import pkgEnum.GameType;
import pkgMover.Mover;
import pkgEnum.GameState;
import javafx.scene.input.MouseEvent;

public abstract class MinigameModel {
	

	/** Abstract class representing the minigame model, will be extended
	 *  to the models of all of the different views
	 * 
	 */

	GameType currGame;
	int score;
	int totalTime;
	GameState gs = GameState.START;
	
	abstract void update(MouseEvent me);
	public abstract void update();
	abstract void handleBorderCollision(Mover m);
	
	public GameType getGameType() {
		return this.currGame;
	}
	
	

}
