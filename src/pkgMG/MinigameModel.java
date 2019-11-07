package pkgMG;

import pkgEnum.Game;
import pkgMover.Mover;
import pkgEnum.GameState;
import pkgMover.DataNode;
import java.util.ArrayList;
import java.util.Random;
import pkgMover.DataNode;

import javafx.scene.input.MouseEvent;

public abstract class MinigameModel {
	

	/** Abstract class representing the minigame model, will be extended
	 *  to the models of all of the different views
	 * 
	 */
	
	Game g;
	int score;
	int totalTime;
	GameState gs = GameState.START;
	final int backgroundHeight = 768;
	final int backgroundWidth = 1280;
	ArrayList<DataNode> dns = new ArrayList<DataNode>();
	Random r = new Random();
	
<<<<<<< HEAD
	public abstract void update(MouseEvent me);
	
	/**
	 * Stops a mover at the edge of a screen (1280x768). 
	 * Since the mover is represented visually by an image, account for imageWidth/Height at bottom and right border
	 * <p>
	 * A <code>Follower</code> will want to override this so that 
	 * 
	 * @author Ryan Peters
	 * @param m	Mover to be checked for Border Collision
	 */
	public void handleBorderCollision(Mover m) {
		double mx = m.getX();
		double my = m.getY();
		if (mx < 0) {
			m.setX(0);
		} else if (mx > backgroundWidth - m.getImageWidth()) {
			m.setX(backgroundWidth - m.getImageWidth());
		}
		
		if (my < 0) {
			m.setY(0);
		} else if (my > backgroundHeight - my/2) {
			m.setY(backgroundHeight - my/2);
		}
	}
=======
	abstract void update(MouseEvent me);
	public abstract void update();
	abstract void handleBorderCollision(Mover m);
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	
<<<<<<< HEAD
	
	public ArrayList<DataNode> getDataNodes() {
		return this.dns;
	}
	
	public Game getGame() {
		return g;
=======
	public GameType getGameType() {
		return this.currGame;
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	}
	
	

}
