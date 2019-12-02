package pkgMGView;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import pkgEnum.Direction;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class MainScreenView extends MinigameView {
	
	private Button btnSC; //sidescroller
	private Button btnHSCC; //horseshoe crab count
	private Button btnAM; //animal matching
	private Button btnWS; //water sampling 
	private int btnSize = 45;
	
	public MainScreenView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.MAINSCREEN);
		game = theGame;
		System.out.println(game + " " + theGame);
		this.root = root;
		this.scene = scene;
		this.gc = gc;

		importImages();
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
	public void setUpListeners() {
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent e) {
    //			System.out.println(e.getSceneX() + " " + e.getSceneY());
    		}
    	});
		
		btnSC.setOnAction(e -> {
				game = Game.SIDESCROLLER;
				disableButton(btnSC);
		});
		
		btnHSCC.setOnAction(e -> {
				game = Game.HSCCOUNT;
				disableButton(btnHSCC);
		});
		
		btnAM.setOnAction(e -> {
			game = Game.ANIMALMATCHING;
			disableButton(btnAM);
		});
		
		btnWS.setOnAction(e -> {
			game = Game.WATERSAMPLING;
			disableButton(btnWS);
		});
		
	}
	
	private void setUpButtons() {
		btnSC = new Button("",new ImageView(loadButtonImage("btnSC")));
		setButtonBackgroundWhite(btnSC);
		formatCircleButton(btnSC, 909.0, 482.0);
		
		btnHSCC = new Button("", new ImageView(loadButtonImage("btnHSCC")));
		setButtonBackgroundWhite(btnHSCC);
		formatCircleButton(btnHSCC, 417.0, 587.0);
		
		btnAM = new Button("", new ImageView(loadButtonImage("btnAM")));
		setButtonBackgroundWhite(btnAM);
		formatCircleButton(btnAM, 852.0, 202.0);
		
		btnWS = new Button("",new ImageView(loadButtonImage("btnWS")));
		setButtonBackgroundWhite(btnWS);
		formatCircleButton(btnWS, 498.0, 85.0);
		
		root.getChildren().add(btnSC);
		root.getChildren().add(btnHSCC);
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
	void draw(ArrayList<Mover> movers) {
		//dns is always empty; don't do anything with it
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);
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
		background = new Image("backgrounds/MainScreen.png");
	}


	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		if (!areButtonsMade) {
			setUpButtons();
			setUpListeners();
			createScoreLabel(score);
			areButtonsMade = true;
		}
		
		if (gs == GameState.FINISHED) {
			game = Game.LEADERBOARD;
		} else {
			draw(movers);
		}
	}
	
	private void setButtonBackgroundWhite(Button b) {
		b.setStyle("-fx-background-color: white");
	}

	@Override
	void drawTutorial(int step) {
		// TODO Auto-generated method stub
		
	}

}
