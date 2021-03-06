package pkgMGView;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.Mover;

public class MainScreenView extends MinigameView {
	private static final long serialVersionUID = 8L;
	private Button btnSC; //sidescroller
	private Button btnHSCC; //horseshoe crab count
	private Button btnAM; //animal matching
	private Button btnWS; //water sampling 
	final private double btnSize = 45;
	final private double btnSCx = 909.0;
	final private double btnSCy = 482.0;
	final private double btnHSCCx = 417.0;
	final private double btnHSCCy = 587.0;
	final private double btnAMx = 852.0;
	final private double btnAMy = 202.0;
	final private double btnWSx = 498.0;
	final private double btnWSy = 85.0;
	
	// minigame titles
	private Label labelSC;
	private Label labelHSCC;
	private Label labelAM;
	private Label labelWS;
	private final int labelHeight = 50;
	private final int labelWidth = 400;
	
	//game played booleans
	private boolean amPlayed = false;
	private boolean hsccPlayed = false;
	private boolean scPlayed = false;
	private boolean wsPlayed = false;
	
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
	public void setUpListeners() {
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent e) {
    //			System.out.println(e.getSceneX() + " " + e.getSceneY());
    		}
    	});
		
		btnSC.setOnAction(e -> {
			game = Game.SIDESCROLLER;
			scPlayed = true;
		});
		btnSC.setOnMouseEntered(e -> {
			labelSC.setVisible(true);
		});
		btnSC.setOnMouseExited(e -> {
			labelSC.setVisible(false);
		});
		
		
		btnHSCC.setOnAction(e -> {
			game = Game.HSCCOUNT;
			hsccPlayed = true;
		});
		btnHSCC.setOnMouseEntered(e -> {
			labelHSCC.setVisible(true);
		});
		btnHSCC.setOnMouseExited(e -> {
			labelHSCC.setVisible(false);
		});

		
		btnAM.setOnAction(e -> {
			game = Game.ANIMALMATCHING;
			amPlayed = true;
		});
		btnAM.setOnMouseEntered(e -> {
			labelAM.setVisible(true);
		});
		btnAM.setOnMouseExited(e -> {
			labelAM.setVisible(false);
		});
		
		
		btnWS.setOnAction(e -> {
			game = Game.WATERSAMPLING;
			wsPlayed = true;
		});
		btnWS.setOnMouseEntered(e -> {
			labelWS.setVisible(true);
		});
		btnWS.setOnMouseExited(e -> {
			labelWS.setVisible(false);
		});
		
	}
	
	private void setUpButtons() {
		btnSC = new Button("",new ImageView(loadButtonImage("btnSC")));
		setButtonBackgroundWhite(btnSC);
		btnSC.setDisable(scPlayed);
		formatCircleButton(btnSC, btnSCx, btnSCy);
		
		btnHSCC = new Button("", new ImageView(loadButtonImage("btnHSCC")));
		setButtonBackgroundWhite(btnHSCC);
		btnHSCC.setDisable(hsccPlayed);
		formatCircleButton(btnHSCC, btnHSCCx, btnHSCCy);
		
		btnAM = new Button("", new ImageView(loadButtonImage("btnAM")));
		setButtonBackgroundWhite(btnAM);
		btnAM.setDisable(amPlayed);
		formatCircleButton(btnAM, btnAMx, btnAMy);
		
		
		btnWS = new Button("",new ImageView(loadButtonImage("btnWS")));
		setButtonBackgroundWhite(btnWS);
		btnWS.setDisable(wsPlayed);
		formatCircleButton(btnWS, btnWSx, btnWSy);
		
		root.getChildren().addAll(btnSC, btnHSCC, btnAM, btnWS);
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
	
	
	private void setUpLabels() {
		
		labelSC = new Label("Terrapin Run");
		labelSC.setLayoutX(btnSCx + btnSize - (labelWidth / 2));
		labelSC.setLayoutY(btnSCy - labelHeight);
		formatMainScreenLabel(labelSC);

		
		labelHSCC = new Label("Horshoe Crab Tagging");
		labelHSCC.setLayoutX(btnHSCCx + btnSize - (labelWidth / 2));
		labelHSCC.setLayoutY(btnHSCCy - labelHeight);
		formatMainScreenLabel(labelHSCC);

		
		labelAM = new Label("Animal Matching");
		labelAM.setLayoutX(btnAMx + btnSize - (labelWidth / 2));
		labelAM.setLayoutY(btnAMy - labelHeight);
		formatMainScreenLabel(labelAM);

		
		labelWS = new Label("Water Sampling");
		labelWS.setLayoutX(btnWSx + btnSize - (labelWidth / 2));
		labelWS.setLayoutY(btnWSy - labelHeight);
		formatMainScreenLabel(labelWS);
		
		root.getChildren().addAll(labelWS, labelAM, labelHSCC, labelSC);
	}
	
	/**
	 * Formats the Label that appears above a Minigame Button
	 * 
	 * @author	Andrew Brenner, Ryan Peters
	 * @param l		Label to be formatted
	 */
	private void formatMainScreenLabel(Label l) {
		l.setPrefSize(labelWidth, labelHeight);
		l.setFont(new Font("Arial", 26));
		l.setAlignment(Pos.CENTER);
		l.setTextFill(Color.WHITE);
		l.setVisible(false);
	}
	
	
	

	
	/**
	 * Draws all necessary images to the Mainscreen. Currently just background while in alpha
	 * 
	 * @author Ryan Peters
	 * @see loadImage()
	 */
	@Override
	public void draw(ArrayList<Mover> movers) {
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
			setUpLabels();
			setUpListeners();
			createScoreLabel(score);
			areButtonsMade = true;
		}
		
		if (amPlayed && wsPlayed && scPlayed && hsccPlayed) {
			game = Game.LEADERBOARD;
		} else {
			draw(movers);
		}
	}
	
	private void setButtonBackgroundWhite(Button b) {
		b.setStyle("-fx-background-color: white");
	}

	@Override
	void drawTutorial(int step) {}

	@Override
	void updateTutorialStep(MouseEvent me) {}

}
