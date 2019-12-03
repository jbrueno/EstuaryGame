package pkgMGView;

//import java.awt.event.MouseEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Timer;

//import com.sun.javafx.geom.Rectangle;
//import com.sun.prism.paint.Color;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class HSCView extends MinigameView {

	Image imgHSC;
	Image imgHSCTagged;
	Group lighting = new Group();
	boolean areMade = false;
	private boolean lightingRemoved = false;
	Label info1 = new Label();
	Label info2 = new Label();
	Label info3 = new Label();
	Label info4 = new Label();

	public HSCView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.HSCCOUNT);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;

		root.getChildren().add(lighting); // add lighting Group

		scene.addEventFilter(MouseEvent.ANY, eventHandler);
		setUpListeners();
		importImages();
	}

	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {

		switch (gs) {
		case TUTORIAL:
			createLightFX();
			drawTutorial(1);
			break;
		case TUTORIAL2:
			createLightFX();
			drawTutorial(2);
			break;
		case INPROGRESS:
			removeTutorialLabels();
			createLightFX();
			createScoreLabel(score);
			createTimer(time);
			draw(movers);
			break;
		case FINISHED:
			if (!lightingRemoved) {
				lighting.getChildren().clear();
				root.getChildren().remove(lighting);
				backToMainButton();
				createScoreLabel(score);
				createTimer(time);
				lightingRemoved = true;
			}
			drawGameOver();
			break;

		default:
			break;
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
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);
		for (Mover m : movers) {
			draw(m);
		}
	}

	@Override
	void importImages() {
		background = new Image("backgrounds/TempHSC.png");
		imgHSC = new Image("Mover/HSC.png");
		imgHSCTagged = new Image("Mover/HSCTagged.png");
		gameOver = new Image("numbers/gameOver.png");
	}

	/**
	 * Starts by clearing the 'lighting' Group, and removing then re-adding it to
	 * root in order for the rectangle to be redrawn. Then, creates a rectangle with
	 * a radial gradient fill and adds it to 'lighting' in order for the rectangle
	 * to appear over the HSC's.
	 * 
	 * @author jbrueno
	 */
	public void createLightFX() {

		lighting.getChildren().clear();
		root.getChildren().remove(lighting);
		root.getChildren().add(lighting);

		RadialGradient light = new RadialGradient(0, 0, me.getX() / backgroundWidth, me.getY() / backgroundHeight, 0.2,
				true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.15, Color.TRANSPARENT), new Stop(1, Color.BLACK) });

		Rectangle rect = new Rectangle(backgroundWidth, backgroundHeight, light);
		rect.setOpacity(0.985);

		lighting.getChildren().add(rect);

	}

	@Override
	void drawTutorial(int step) {
		switch (step) {
		case 1:
			gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);
			gc.drawImage(imgHSC, backgroundWidth / 2, backgroundHeight / 2, 200, 136);

			info1.setLayoutX(220);
			info1.setLayoutY(10);
			info1.setFont(new Font("Arial", 30));
			info1.setTextFill(Color.PEACHPUFF);
			info1.setText("Move your flashlight around to find the horseshoe crab!");
			info1.setStyle("-fx-font-weight: bold");
			root.getChildren().remove(info1);
			root.getChildren().add(info1);

			info2.setLayoutX(backgroundWidth / 3.15);
			info2.setLayoutY(50);
			info2.setFont(new Font("Arial", 30));
			info2.setTextFill(Color.PEACHPUFF);
			info2.setText("Click the horseshoe crab to tag it!");
			info2.setStyle("-fx-font-weight: bold");
			root.getChildren().remove(info2);
			root.getChildren().add(info2);

			break;
		case 2:
			root.getChildren().remove(info1);
			root.getChildren().remove(info2);
			
			gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);
			gc.drawImage(imgHSCTagged, backgroundWidth / 2, backgroundHeight / 2, 200, 136);

			info3.setLayoutX(150);
			info3.setLayoutY(20);
			info3.setFont(new Font("Arial", 30));
			info3.setTextFill(Color.PEACHPUFF);
			info3.setText("You have 30 seconds to tag as many horseshoe crabs as possible!");
			info3.setStyle("-fx-font-weight: bold");
			root.getChildren().remove(info3);
			root.getChildren().add(info3);
			
			info4.setLayoutX(450);
			info4.setLayoutY(60);
			info4.setFont(new Font("Arial", 30));
			info4.setTextFill(Color.PEACHPUFF);
			info4.setText("Click anywhere to start!");
			info4.setStyle("-fx-font-weight: bold");
			root.getChildren().remove(info4);
			root.getChildren().add(info4);

			break;
		default:
			break;
		}
	}
	
	void removeTutorialLabels() {
		root.getChildren().remove(info3);
		root.getChildren().remove(info4);
	}

	@Override
	void updateTutorialStep(MouseEvent me) {
		/*if(me.getEventType() == MouseEvent.MOUSE_CLICKED) { // me.getEventType() == MouseEvent.MOUSE_PRESSED || 
			if(me.getX() <= (backgroundWidth/2 + 200) && me.getX() > backgroundWidth/2
					&& me.getY() >= backgroundHeight/2 && me.getY() <= (backgroundHeight/2 + 136)) {
				step++;
			}
		}*/
	}

}
