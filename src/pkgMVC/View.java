package pkgMVC;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMGView.*;
import pkgMover.DataNode;
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
	
	private Stage stage;
	private Scene scene;
	private Group root;
	private GraphicsContext gc;
	private int canvasWidth = 1280;
	private int canvasHeight = 768;
	private Game game;
	private Canvas canvas;
	
	
	public View(Stage theStage) {	
		this.stage = theStage;
	    this.root = new Group();
        this.scene = new Scene(root);
        stage.setScene(scene);

        canvas = new Canvas(canvasWidth, canvasHeight);
        root.getChildren().add(canvas); 
        
        gc = canvas.getGraphicsContext2D();
        
		createViews();
	}
	
	/**
	 * Main function for View that takes <code>dns</code> and <code>gs</code> from the Model and updates accordingly.
	 * Passes both parameters in to <code>currGame</code>'s update() method
	 * 
	 * @author Ryan Peters
	 * @param dns
	 * @param gs
	 * @see MinigameView.update()
	 */
	public void update(ArrayList<DataNode> dns, GameState gs) {
		System.out.println(currGame.getGame()); //testing current Game
		currGame.update(dns, gs);
		
	}
	
	/**
	 * Initializes and creates the attribute <code>mgvs</code>, a list of MinigameViews, to be indexed by Game.ordinal() in order to load 
	 * the correct MinigameView.
	 * <p>
	 * By default, sets the <code>currGame</code> to MainScreenView since that is the first MinigameView to be loaded on start.
	 * 
	 * @author Ryan Peters
	 */
	private void createViews() {
		mgvs = new ArrayList<MinigameView>();
		
		mgvs.add(new MainScreenView(gc, root, scene));
		mgvs.add(new AMView(gc, root, scene));
		mgvs.add(new HSCView(gc, root, scene));
		mgvs.add(new SCView(gc, root, scene));
		mgvs.add(new WSView(gc, root, scene));
		mgvs.add(new LeaderboardView(gc, root, scene));
		
		currGame = mgvs.get(0); //default start for Game
        this.game = Game.MAINSCREEN;
	}
	
	public MouseEvent getMouseEvent() {
		return currGame.getMouseEvent();
	}
	
	/**
	 * Returns the currGame determined by the current MinigameView. Since MinigameView's can change the Game
	 * (MainScreenView by Minigame Buttons, other MinigameViews by Return/Exit/Next/etc Button), it needs to update 
	 * <code>currGame</code> so that it is the correct MinigameView in case it has been changed
	 * <p>
	 * By clearing the children of the root, we clear canvas and the GraphicsContext can no longer draw to the stage.
	 * Therefore, we must add canvas back to root as a child.
	 * 
	 * @author Ryan Peters
	 * @return GameType the GameType that the view is currently showing
	 * @see retrieveMGV()
	 * @see getGame()
	 * 
	 */
	public Game getGame() {
		Game g = currGame.getGame();
		if (currGame.getGame() != g) {//loading new game (since getGame() resets the Game attribute if different)
			currGame.clearFX();
			root.getChildren().add(canvas);
		}
		currGame = retrieveMGV(g);
		return g;
	}
	
	/**
	 * Returns the correct MinigameView from the list of MinigameViews <code>mgvs</code> ordered by the natural order of the
	 * Game Enum
	 * 
	 * @author Ryan Peters
	 * @param g
	 * @return	MinigameView	MGV at position of the Game enum parameter
	 * @see	Game
	 */
	public MinigameView retrieveMGV(Game g) {
		return mgvs.get(g.ordinal());
	}	
	
	
}
