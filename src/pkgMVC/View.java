package pkgMVC;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import pkgEnum.GameState;
import pkgEnum.GameType;
import pkgMGView.*;
import pkgMover.Mover;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

// NOTE: will not be needed once lab is complete
import java.util.Random;


/**The View class for an MVC design.
 * 
 * @author Ryan Peters
 *
 */
public class View {
	private ArrayList<MinigameView> mgvs;
	private MinigameView currGame;
	int score;
	
	private Scene scene;
	private Group root;
	private GraphicsContext gc;
	private int canvasWidth = 1280;
	private int canvasHeight = 768;
	private GameType gameType;
	
	public View(Stage theStage) {		
	    this.root = new Group();
        this.scene = new Scene(root);
        theStage.setScene(scene);
        this.gameType = GameType.MAINSCREEN;

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas); 
        
        gc = canvas.getGraphicsContext2D();
        
		createMinigameViews();
	}
	
	public void update(ArrayList<Mover> ms, GameState gs) {
		currGame.update(ms, gs);
		updateView();
		
	}
	
	private void createMinigameViews() {
		mgvs = new ArrayList<MinigameView>();
		
		mgvs.add(new MainScreenView(gc, root, scene));
		currGame = mgvs.get(0);		
	}
	
	private void updateView() {
		root = currGame.getRoot();
		scene = currGame.getScene();
	}
	
	public MouseEvent getMouseEvent() {
		return currGame.getMouseEvent();
	}
	
	/**
	 * Returns the gameType ENUM that is currently being used in the view
	 * 
	 * @author HM
	 * @return GameType the GameType that the view is currently showing
	 * 
	 */
	public GameType getGameType() {
		return this.gameType;
	}
	
}
