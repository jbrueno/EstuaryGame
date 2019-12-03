package pkgMGModel;

import pkgEnum.Game;
import pkgMover.Mover;
import pkgEnum.GameState;
import pkgMover.DataNode;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pkgMover.DataNode;

import javafx.scene.input.MouseEvent;

public abstract class MinigameModel {

	/**
	 * Abstract class representing the minigame model, will be extended to the
	 * models of all of the different views
	 * 
	 */

	Game g;
	int score = 0;  
	int totalTime;
	GameState gs = GameState.START;
	final int backgroundHeight = 768;
	final int backgroundWidth = 1280;
	ArrayList<Mover> movers = new ArrayList<Mover>();
	Random r = new Random();
	ArrayList<DataNode> dns = new ArrayList<DataNode>();
	Timer timer = new Timer();
	int time;

	public abstract void update(MouseEvent me);

	/**
	 * Stops a mover at the edge of a screen (1280x768). Since the mover is
	 * represented visually by an image, account for imageWidth/Height at bottom and
	 * right border
	 * <p>
	 * A <code>Follower</code> will want to override this so that
	 * 
	 * @author Ryan Peters
	 * @param m Mover to be checked for Border Collision
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
		} else if (my > backgroundHeight - my / 2) {
			m.setY(backgroundHeight - my / 2);
		}
	}

	
	/**
	 * Checks to see if the image of a mover has gone off screen and if it has returns true
	 * otherwise false
	 * 
	 * @param m mover to be checked
	 * @return
	 */
	public boolean isOffScreen(Mover m) {
		if(m.getX() + m.getImageWidth()/2 < 0 || m.getX() - m.getImageWidth() > backgroundWidth) {
			return true;
		}else if(m.getY() + m.getImageHeight()/2 < 0 || m.getY() - m.getImageHeight() > backgroundHeight) {
			return true;
		}else {
			return false;
		}
	}

	public ArrayList<Mover> getMovers() {
		return this.movers;
	}

	public Game getGame() {
		return g;
	}

	public ArrayList<DataNode> getDataNodes() {
		return dns;
	}
	
	public GameState getGameState() {
		return gs;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getTime() {
		return time;
	}

	
	public void setUpTimer() {

		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				--time;
				System.out.println("time remaining: " + time);
				if(time == 0) {
					timer.cancel();
					System.out.println("times up");
					gs = gs.FINISHED;
				}

			}

		}, 100, 100);

	}
	
	/**
	 * Determines whether the MouseEvent's location is currently within an ellipse created from the mover's imageWidth and 
	 * imageHeight attributes. If it is, return <code>True</code>, else <code>false</code>
	 * 
	 * @author	Ryan Peters
	 * @param m		Mover to test if the mouse is over it in terms of the view/image
	 * @param me	MouseEvent from <code>View</code> of the current mouse position on screen 
	 * @return
	 */
	public boolean isCollision(Mover m, double x, double y) {
		double xDist = Math.abs(Math.pow(m.getX() - x, 2) / Math.pow(m.getImageWidth() / 2, 2));
		double yDist = Math.abs(Math.pow(m.getY() - y, 2) / Math.pow(m.getImageHeight() / 2, 2));
		double dist = xDist + yDist;
		return dist < 1;
	}
	
	public boolean isCollision(Mover m1, Mover m2) {
		return isCollision(m1, m2.getX(), m2.getY());
	}
	

	public boolean isCollision(Mover m, MouseEvent me) {
		return isCollision(m, me.getX(), me.getY());
	}
	
}

