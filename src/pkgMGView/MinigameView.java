package pkgMGView;
//testing branch
//test
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import pkgEnum.Direction;
import pkgEnum.GameState;
import pkgEnum.GameType;
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
	GameType gt;
	
	public abstract void update(ArrayList<DataNode> dns, GameState gs);
	abstract void startTimer(int ms);
	abstract void stopTimer();
	abstract void setUpListeners();
	abstract void draw();
	abstract void importImages();
	
	public Image loadImage(String pkgName, String imageName) {
		Image img = new Image(pkgName + "/" + imageName);
		return img;
	}
	
	public Image loadImage(String pkgName, String imageName, Mover m) {
		Image img = new Image(pkgName + "/" + imageName + "_" + m.toString());
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
	
	public GameType getGameType() {
		return this.gt;
	}
}
