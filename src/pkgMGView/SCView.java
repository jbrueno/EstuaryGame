package pkgMGView;


import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.DataNode;
import pkgMover.Food;
import pkgMover.Mover;
import pkgMover.Seaweed;
import pkgMover.Terrapin;
import pkgMover.Trash;

public class SCView extends MinigameView  {
	
	Button btnReturn;
	final Game theGame = Game.SIDESCROLLER;
	Image background;
	Node background2;
	Image Terrapin;
	Image Trash;
	Image Food;
	Image Seaweed;
	int seaweedHeight = 150;
	int seaweedWidth = 150;
	int trashHeight = 100;
	int trashWidth = 50;
	int foodHeight = 50;
	int foodWidth = 50;
	double mouseX;
	double mouseY;
	int gameLength;
	double breathBarX = backgroundWidth - 190;
	double breathBarY = 50;
	double breathBarHeight = 10;
	int lungCapacity = 100;
	boolean tutorialSet = false;
	boolean okButton = false;
	Button ok;
	//private ParallelTransition parallelTransition;

	
	
	public SCView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.SIDESCROLLER);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		importImages();
		setUpListeners();
		scene.addEventFilter(MouseEvent.ANY, eventHandler);
		startTimer(gameLength);
		gc.fillRect(breathBarX, breathBarY, lungCapacity, breathBarHeight);
		//createBackgroundAnimation();
		tutorialStep = 0;
		
		

	}
	

	@Override

	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		
		
		updateScoreLabel(score);
		createTimer(time);
		
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
			draw(movers);
			break;
		case FINISHED:
			if (!isBackToMainDrawn) {
				backToMainButton();
			}
			drawGameOver();
			break;
		case SC_TUTORIAL_FOOD:
			
		case SC_TUTORIAL_TRASH:
		case SC_TUTORIAL_SEAWEED:
		case SC_TUTORIAL_BREATH:
		case TUTORIAL:
		}
		
		if (gs == GameState.INPROGRESS) {
			draw(movers);
		} else if (gs == GameState.FINISHED) {
			if (!isBackToMainDrawn) {
				backToMainButton();
			}
			drawGameOver();
		} else if (gs == GameState.SC_TUTORIAL_FOOD) {
			draw(movers);
			if (movers.size() == 1) {
				tutorialStep++;
			}
			if (!tutorialSet) {
				setUpTutorial();
				drawOKButton();
				root.getChildren().add(ok);
				tutorialSet = true;
			}
			
			drawTutorial(tutorialStep);
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
	void importImages() {
		background = new Image("/backgrounds/sidescroller_background.png");
		background2 = new ImageView("/backgrounds/sidescroller_background.png");
		
		Terrapin = new Image("/Mover/Terrapin.png");
		
		Trash = new Image("/Mover/Trash.png");
		
		Food = new Image("/Mover/Food.png");
		
		Seaweed = new Image("/Mover/Seaweed.png");
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
		
		System.out.println("score box made");
	}
	
	public double getMouseX() {
		return this.mouseX;
	}
	
	public double getMouseY() {
		return this.mouseY;
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		me = e;
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		me = e;
	}
	
	@Override
	public MouseEvent getMouseEvent() {
		return me;
	}


	@Override
	void drawTutorial(int step) {
		// TODO Auto-generated method stub
		switch(step) {
		case 0:
			tutorialLabel.setText("Eat food!");
			if (!okButton) {
				drawOKButton();
				okButton = true;
			}
			ok.setVisible(true);
		case 1:
			tutorialLabel.setText("Avoid trash!");
			ok.setVisible(true);
		case 2:
			tutorialLabel.setText("Seaweed slows you down!");
			ok.setVisible(true);
		case 4:
			tutorialLabel.setText("Don't run out of breath!");
			ok.setVisible(true);
		}
		
	}
	
	@Override
	void updateTutorialStep(MouseEvent me) {
		
	}
	
	public void drawOKButton() {
		ok = new Button("Ok!");
		ok.setLayoutX(backgroundWidth/2);
		ok.setLayoutY(backgroundHeight/2);
		ok.setOnMousePressed(e -> {
			ok.setVisible(false);
		});
	}
	
//	public void createBackgroundAnimation() {
//		TranslateTransition translateTransition = 
//				new TranslateTransition(Duration.millis(10000), background1);
//		translateTransition.setFromX(0);
//		translateTransition.setToX(-1 * backgroundWidth);
//		translateTransition.setInterpolator(Interpolator.LINEAR);
//		
//		TranslateTransition translateTransition2 =
//	           new TranslateTransition(Duration.millis(10000), background2);
//		translateTransition2.setFromX(0);
//		translateTransition2.setToX(-1 * backgroundWidth);
//		translateTransition2.setInterpolator(Interpolator.LINEAR);
//
//		parallelTransition = 
//			new ParallelTransition( translateTransition, translateTransition2 );
//		parallelTransition.setCycleCount(Animation.INDEFINITE);
//		
//		parallelTransition.play();
//	}
	
	
	
}