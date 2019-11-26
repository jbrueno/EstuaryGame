package pkgMGView;

//testing branch
//test
import java.util.ArrayList;
import java.util.Timer;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

//import java.awt.Font;
import javafx.scene.paint.Color;
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
	Canvas c;
	GraphicsContext gc;
	Group root;
	Scene scene;
	Game game;
	final Game theGame; // final in each subclass
	boolean areButtonsMade = false;
	Text scoreBox = new Text();

	Image gameOver;
	int goX = (backgroundWidth / 2) - 250;
	int goY = (backgroundHeight / 2) - 150;
	int goWidth = 500;
	int goHeight = 300;

	// ** need to modify update() to update total Score by getting sum of MiniGames
	// **
	int currScore; // individual miniGameScore , may be easier to track by making individual attributes in each subclass
	int totalScore; // Overall score, sum of miniGameScores
	Label scoreLabel = new Label();
	final double scoreLabelX = backgroundWidth - 200;
	final double scoreLabelY = 0;

	Label displayTime = new Label();
	int displayTimeX = (backgroundWidth / 2) - 150;
	int displayTimeY = 15;

	ArrayList<DataNode> dns = new ArrayList<DataNode>();

	
	public abstract void update(ArrayList<Mover> movers, GameState gs, int score, int time);
	abstract void startTimer(int ms);
	abstract void stopTimer();
	abstract void setUpListeners();
	abstract void draw(ArrayList<Mover> movers);
	abstract void importImages();

	// EVENTHANDLER!! This sees all mouse events in minigames
	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			me = e;
		}
	};

	public MinigameView(Game theGame) {
		this.theGame = theGame;
	}

	/**
	 * Finds and returns image associated with given Mover object
	 * @param pkgName package to determine where image is located
	 * @param m Mover - used to grab value attribute (name of image)
	 * @return Image img - image associated with that particular Mover object
	 */
	public Image loadImage(String pkgName, Mover m) {
		Image img = new Image(pkgName + "/" + m.getValue() + ".png"); 
		return img;
	}

	/*
	 * public Image loadImage(String pkgName, String imgName) { Image img = new
	 * Image(pkgName + "/" + imgName + ".gif"); return img; }
	 */

	
	/**
	 * Finds and returns image associated with given Mover object
	 * @param m Mover - used to grab value attribute (name of image)
	 * @return Image img - image associated with that particular Mover object
	 */
	public Image loadImage(Mover m) {
		Image img = new Image("Mover/" + m.getValue() + ".png");
		return img;
	}

	public double getAngle(Direction d) {
		double angle = 0;
		switch (d) {
		case NORTH: {
			angle = -60.0;
			break;
		}
		case NORTHEAST: {
			angle = -30.0;
			break;
		}
		case EAST: {
			angle = 0.0;
			break;
		}
		case SOUTHEAST: {
			angle = 30.0;
			break;
		}
		case SOUTH: {
			angle = 60.0;
			break;
		}
		case SOUTHWEST: {
			// isFlipped = true;
			angle = -30.0;
			break;
		}
		case WEST: {
			// isFlipped = true;
			break;
		}
		case NORTHWEST: {
			// isFlipped = true;
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
	 * GC is wiped and root is cleared of all children (Buttons, etc) which includes
	 * canvas to which gc draws upon. This is handled in <code>View</code> class
	 * under <code>getGame()</code>
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
	/*
	 * public void draw(Mover m) { gc.drawImage(loadImage("Mover", m.getValue()),
	 * m.getX(), m.getY(), m.getImageWidth(), m.getImageWidth()); }
	 */

	public void draw(Mover m) {
		gc.drawImage(loadImage(m), m.getTranslatedX(), m.getTranslatedY(), m.getImageWidth(), m.getImageHeight());
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

	public ArrayList<DataNode> getDataNodes() {
		return dns;
	}

	public int getTotalScore() {
		return totalScore;
	}

	// The following Score methods can be used for both total Score and current
	// score for a particular miniGame
	/**
	 * @author Abrenner method to draw up a brand new scoreLabel with score of zero
	 *         for any miniGameView
	 */
	/*
	 * public void createScoreLabel() { scoreLabel.setLayoutX(scoreLabelX);
	 * scoreLabel.setLayoutY(scoreLabelY); scoreLabel.setFont(new Font("Arial",
	 * 30)); scoreLabel.setTextFill(Color.PEACHPUFF); scoreLabel.setText("Score: " +
	 * 0); root.getChildren().add(scoreLabel); }
	 */

	/**
	 * @author Abrenner Overloading createScoreLabel()
	 * @param score int - current score for particular view method will be called
	 *              when score is not assumed to be zero (loading MainScreenView
	 *              with total score)
	 */
	public void createScoreLabel(int score) {
		scoreLabel.setLayoutX(scoreLabelX);
		scoreLabel.setLayoutY(scoreLabelY);
		scoreLabel.setFont(new Font("Arial", 30));
		scoreLabel.setTextFill(Color.PEACHPUFF);
		scoreLabel.setText("Score: " + score);
		root.getChildren().remove(scoreLabel);
		root.getChildren().add(scoreLabel);
	}

	/**
	 * @author Abrenner updates scoreLabel to display the current score
	 */
	public void updateScoreLabel(int score) {
		scoreLabel.setText("Score: " + score);
	}

	/**
	 * @author Abrenner 
   * removes the scoreLabel from the view may not need if we
	 *         include label in method clearFX()
	 * 
	 * removes the scoreLabel from the view
	 */
	public void removeScoreLabel() {
		root.getChildren().remove(scoreLabel);
	}

	/**
	 * Draws the gameOver image on the middle of the view
	 * @author jbrueno
	 */
	public void drawGameOver() {
		gc.drawImage(gameOver, goX, goY, goWidth, goHeight);
	}

	/**
	 * Creates and adds a timer to the view.  Depending on the time remaining, displays the time
	 * in seconds instead of milliseconds by parsing the time int.
	 * 
	 * @author jbrueno
	 * @param time
	 */
	public void createTimer(int time) {
		if (time >= 100) {
			time = Integer.parseInt(("" + time).substring(0, 2));
		} else if (time >= 10) {
			time = Integer.parseInt(("" + time).substring(0, 1));
		} else if(time > 0){
			time = 1;
		} else {
			time = 0;
		}
		displayTime.setLayoutX(displayTimeX);
		displayTime.setLayoutY(displayTimeY);
		displayTime.setFont(new Font("Arial", 30));
		displayTime.setTextFill(Color.WHITE);
		displayTime.setText("Time Remaining: " + time);
		root.getChildren().remove(displayTime);
		root.getChildren().add(displayTime);
	}
	
	/**
	 * Displays a button that returns the user back to the MainScreen once the game is over
	 * 
	 * @author jbrueno
	 */
	public void backToMainButton() {
		Button backToMain = new Button("Back to Main Screen");
		backToMain.setLayoutX(backgroundWidth/2 - 100);
		backToMain.setLayoutY(backgroundHeight/2 + 200);
		backToMain.setOnAction(e -> {
			game = game.MAINSCREEN;
		});
		root.getChildren().add(backToMain);
	}
	
	/*
	 * Prevents a button from being clicked
	 * 
	 * @author Ryan Peters
	 */
	public void disableButton(Button b) {
		b.setDisable(true);
	}


}