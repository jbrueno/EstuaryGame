package pkgMGView;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import pkgEnum.Direction;
import pkgEnum.GameState;
import pkgEnum.GameType;
import pkgMover.Mover;

public class MainScreenView extends MinigameView {
	
	private Button btnSC; //sidescroller
	private Button btnHCC; //horseshoe crab count
	private Button btnAM; //animal matching
	private Button btnWS; //water sampling 
	private int btnSize = 45;
	
	public MainScreenView(GraphicsContext gc, Group root, Scene scene) {
		this.gt = GameType.MAINSCREEN;
		this.root = root;
		this.scene = scene;
		this.gc = gc;

       
    	setUpButtons(); 
    	setUpListeners();

		importImages();
	}
	
	public void update(ArrayList<Mover> ms, GameState gs) {
		//ms should always be empty since the main screen has no moving objects
		if (gs == GameState.FINISHED) {
			//loadLeaderboard
		} else {
			draw();
		}
	}


	@Override
	void startTimer(int ms) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void stopTimer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setUpListeners() {
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent e) {
    			System.out.println(e.getSceneX() + " " + e.getSceneY());
    		}
    	});
		
		btnSC.setOnAction(e -> {
			gt = GameType.SIDESCROLLER;
		});
		
		btnHCC.setOnAction(e -> {
			gt = GameType.HCCOUNT;
		});
		
		btnAM.setOnAction(e -> {
			gt = GameType.ANIMALMATCHING;
		});
		
		btnWS.setOnAction(e -> {
			gt = GameType.WATERSAMPLING;
		});
		
	}
	
	private void setUpButtons() {
		btnSC = new Button();
		formatCircleButton(btnSC, 909.0, 482.0);
		
		btnHCC = new Button();
		formatCircleButton(btnHCC, 417.0, 587.0);
		
		btnAM = new Button();
		formatCircleButton(btnAM, 852.0, 202.0);
		
		btnWS = new Button();
		formatCircleButton(btnWS, 498.0, 85.0);
		
		root.getChildren().add(btnSC);
		root.getChildren().add(btnHCC);
		root.getChildren().add(btnAM);
		root.getChildren().add(btnWS);
		
	}
	
	/**
	 * Formats an existing Button to be of a Circle shape
	 * @param b
	 * @param x
	 * @param y
	 */
	private void formatCircleButton(Button b, double x, double y) {
		b.setLayoutX(x);
		b.setLayoutY(y);
		b.setShape(new Circle(btnSize));
		b.setMinSize(btnSize * 2, btnSize * 2);
		b.setMaxSize(btnSize* 2, btnSize * 2);
	}

	
	/**
	 * Draws all necessary images to the Mainscreen. Currently just background while in alpha
	 * 
	 * @author Ryan Peters
	 * @see loadImage()
	 */
	@Override
	void draw() {
		gc.drawImage(loadImage("backgrounds","background_MainScreen.png"), 0, 0, backgroundWidth, backgroundHeight);
	}
	
	/**
	 * Loads all images to class attributes to prevent unnecessary re-loading of images for faster access time during drawing.
	 * Currently, only background for the alpha code
	 * 
	 * @author Ryan Peters	
	 * @see loadImage()
	 */
	@Override 
	void importImages() {
		background = loadImage("backgrounds","background_MainScreen.png");
	}
}
