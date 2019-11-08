package pkgMGView;
//testing branch
//test
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import pkgEnum.Direction;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public abstract class MinigameView {
	Image background;
	int backgroundWidth = 1280;
	int backgroundHeight = 768;
	MouseEvent me;
	//Timer timer;
	Canvas c;
	GraphicsContext gc;
	Group root;
	Scene scene;
	Game game;
	final Game theGame; //final in each subclass
	boolean areButtonsMade = false;
	
	
	public abstract void update(ArrayList<DataNode> dns, GameState gs);
	abstract void startTimer(int ms);
	abstract void stopTimer();
	abstract void setUpListeners();
	abstract void draw(ArrayList<DataNode> dns);
	abstract void importImages();
	
	public MinigameView(Game theGame) {
		this.theGame = theGame;
	}
	
	
	// need to find a way to differentiate whether image is .png or .gif
	// only movers are .gif at the moment so it has been changed
	public Image loadImage(String pkgName, Mover m) {
		Image img = new Image(pkgName + "/" + m.getValue() + ".gif"); // changed from .png to .gif
		return img;
	}
	
	public Image loadImage(String pkgName, String imgName) {
		Image img = new Image(pkgName + "/" + imgName + ".gif");
		return img;
	}

	
	
	public double getAngle(Direction d) {
		double angle = 0;
		switch (d) {
	        case NORTH: {
	            angle = -60.0;
	            break;
	        } case NORTHEAST: {
	        	angle = -30.0;
	            break;
	        } case EAST: {
	        	angle = 0.0;
	            break;
	        } case SOUTHEAST: {
	        	angle = 30.0;
	            break;
	        } case SOUTH: {
	        	angle = 60.0;
	            break;
	        } case SOUTHWEST: {
	           // isFlipped = true;
	            angle = -30.0;
	            break;
	        } case WEST: {
	            //isFlipped = true;
	            break;
	        } case NORTHWEST: {
	            //isFlipped = true;
	            angle = 30.0;
	            break;
	        }
		}
		return angle;
	}
	
	public Group getRoot() {
		return root;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public MouseEvent getMouseEvent() {
		return me;
	}
	
	/**
	 * Clears the current FX being displayed.
	 * <p> 
	 * GC is wiped and root is cleared of all children (Buttons, etc) which includes canvas to which gc draws upon.
	 * This is handled in <code>View</code> class under <code>getGame()</code>
	 * 
	 * @author Ryan Peters
	 * @see View
	 * @see View.getGame()
	 */
	public void clearFX() {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		root.getChildren().clear();
		areButtonsMade = false;
	}
	
	public void draw(Mover m) {
		gc.drawImage(loadImage("Mover", m.getValue()),
				m.getX(), m.getY(), m.getImageWidth(), m.getImageWidth());
	}
	
	public GraphicsContext getGC() {
		return this.gc;
	}
	
	public Game getTheGame() {
		return theGame;
	}
	
	public void resetGameAttribute() {
		game = theGame;
	}
	
	public Game getGame() {
		return this.game;
	}
}
