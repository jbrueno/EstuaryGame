package pkgMGView;


import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.Mover;
import pkgMover.Terrapin;

public class SCView extends MinigameView  {
	private static final long serialVersionUID = 9L;
	//1-tick booleans
	boolean tutorialSet = false;
	boolean okButton = false;
	private boolean setPlay;
	
	//JAVAFX elements
	Button btnReturn;
	Image background;
	Node background2;
	Image Terrapin;
	Button ok;

	int gameLength;
	double breathBarX = backgroundWidth - 155;
	double breathBarY = 50;
	double breathBarHeight = 10;
	int lungCapacity = 100;
	final Game theGame = Game.SIDESCROLLER;
	
	public SCView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.SIDESCROLLER);
		game = theGame;
		this.root = root;
		this.scene = scene; 
		this.gc = gc;
		importImages();
		setUpListeners();
		scene.addEventFilter(MouseEvent.ANY, eventHandler);
		gc.fillRect(breathBarX, breathBarY, lungCapacity, breathBarHeight);
		//createBackgroundAnimation();
		tutorialStep = 0;
	}
	

	@Override

	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		
		
		
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
			createScoreLabel(score);
		}
		
		
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);
		
		
		gc.clearRect(breathBarX, breathBarY, lungCapacity, breathBarHeight);
		
		for (Mover m : movers) {
			if (m instanceof Terrapin) {
				Terrapin t = (Terrapin)m;
				gc.fillRect(breathBarX, breathBarY, t.getAirAmount(), breathBarHeight);
				break;
			}
		}
		
		
		switch(gs) {
		case INPROGRESS:
			updateScoreLabel(score);
			createTimer(time);
			root.getChildren().remove(btnPlay);
			draw(movers);
			break;
		case FINISHED:
			if (!isBackToMainDrawn) {
				backToMainButton();
			}
			drawGameOver();
			break;
		case SC_TUTORIAL_FOOD:
			if (!tutorialSet) {
				setUpTutorial();
				tutorialSet = true;
			}
			
			
			draw(movers);
			drawTutorial(0);
			break;
		case SC_TUTORIAL_TRASH:
			
			draw(movers);
			drawTutorial(1);
			break;
		case SC_TUTORIAL_SEAWEED:
			
			draw(movers);
			drawTutorial(2);
			break;
		case SC_TUTORIAL_BREATH:
			
			draw(movers);
			drawTutorial(3);
			break;
		case TUTORIAL:
			if (!setPlay) {
				drawPlayButton();
				setPlay = true;
				root.getChildren().add(btnPlay);
				root.getChildren().remove(prompt);
			}
			
			if (play) {
				root.getChildren().remove(btnPlay);
			}
			drawTutorial(4);
			break;
		}
	
		
	
		
	}
	
	
	
	public void clearButton(ArrayList<Mover> movers) {
		root.getChildren().removeAll();
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);
		
		
		gc.clearRect(breathBarX, breathBarY, lungCapacity, breathBarHeight);
		
		for (Mover m : movers) {
			if (m instanceof Terrapin) {
				Terrapin t = (Terrapin)m;
				gc.fillRect(breathBarX, breathBarY, t.getAirAmount(), breathBarHeight);
				break;
			}
		}
	}

	@Override
	void importImages() {
		background = new Image("/backgrounds/sidescroller_background.png");
		background2 = new ImageView("/backgrounds/sidescroller_background.png");
		
		Terrapin = new Image("/Mover/Terrapin.png");
	}

	@Override
	public void draw(ArrayList<Mover> movers) {
		for (Mover m : movers) {	
			draw(m);
		}
	}
	
	
	public void makeScoreBox() {
		scoreBox.setCache(true);
		scoreBox.setX(backgroundWidth);
		scoreBox.setY(backgroundHeight);
		scoreBox.setFill(Color.RED);
		scoreBox.setText("0");
		scoreBox.setFont(Font.font(null, FontWeight.BOLD, 32));
	}

	@Override
	void drawTutorial(int step) {
		// TODO Auto-generated method stub
		switch(step) {
		case 0:
			prompt.setText("Click when ready: Follow the mouse, and eat food!");
			break;
		case 1:
			prompt.setText("Click when ready: Avoid trash!");
			break;
		case 2:
			prompt.setText("Click when ready: Seaweed slows you down!");
			break;
		case 3:
			prompt.setText("Click when ready: Put your head above water to breathe before you run out of air!");
			break;
		case 4:
			tutorialLabel.setVisible(false);
		}
		
	}
	
	@Override
	void updateTutorialStep(MouseEvent me) {
		
		
	}
	
	public void drawOKButton() {
		okButton = false;
		ok = new Button("Ok!");
		ok.setLayoutX(backgroundWidth/2);
		ok.setLayoutY(backgroundHeight/2);
		ok.setOnMousePressed(e -> {
			System.out.println(okButton);
			System.out.println("Ok clicked");
			okButton = true;
			ok.setVisible(false);
			System.out.println(okButton);
		});
	}
	
	@Override
	public void drawPlayButton() {
		play = false;
		btnPlay=new Button();
		btnPlay.setText("Let's play!");
		btnPlay.setLayoutX(backgroundWidth/2);
		btnPlay.setLayoutY(backgroundHeight/2);
		btnPlay.setOnMouseClicked(e -> {
			play=true;
		});
		
	}	
}