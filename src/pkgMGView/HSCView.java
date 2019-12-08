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

//import com.sun.javafx.geom.Rectangle;
//import com.sun.prism.paint.Color;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.Mover;

public class HSCView extends MinigameView {
	private static final long serialVersionUID = 6L;
	//1-tick boolean
	boolean areMade = false;
	private boolean lightingRemoved = false;
	
	//JAVAFX elements
	Group lighting = new Group(); 
	Label info1 = new Label();
	Label info2 = new Label();
	Label info3 = new Label();
	Label info4 = new Label();
	Image imgHSC;
	Image imgHSCTagged;
	
	/**
	 * Constructor which saves local copies of GraphicsContext, Root, and Scene from <code>View</code> so that
	 * graphics may be loaded upon them.  Adds the <code>lighting</code> Group and event handler.
	 * @param gc	<code>GraphicsContext</code> from <code>View</code>
	 * @param root	<code>root</code> from <code>View</code>
	 * @param scene	<code>scene</code> from <code>View</code>
	 */
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

	/**
	 * Update the view of HSC game based on the current GameState
	 */
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {

		switch (gs) {
		case TUTORIAL:
			createLightFX();
			drawTutorial1();
			break;
		case TUTORIAL2:
			createLightFX();
			drawTutorial2();
			break;
		case INPROGRESS:
			removeTutorialLabels();
			createLightFX();
			createScoreLabel(score);
			createTimer(time);
			draw(movers);
			break;
		case FINISHED:
			removeLighting();
			backToMainButton();
			createScoreLabel(score);
			createTimer(time);
			drawGameOver();
			break;

		default:
			break;
		}
	}
	
	/**
	 * Checks if the Group lighting has been removed from root.  If not, lighting is cleared, then
	 * removed from root and lightingRemoved is set to true.
	 */
	void removeLighting() {
		if (!lightingRemoved) {
			lighting.getChildren().clear();
			root.getChildren().remove(lighting);
			lightingRemoved = true;
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

	}
	
	/**
	 * Draws the HSC to be tagged during the tutorial and creates and adds two
	 * labels prompting the user during the <code>TUTORIAL</code>
	 */
	void drawTutorial1() {
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
	}
	
	/**
	 * Removes the labels <code>info1</code> and <code>info2</code>, draws the 
	 * tagged HSC and adds two labels to direct the user during <code>TUTORIAL1</code>
	 */
	void drawTutorial2() {
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
	}
	
	/**
	 * Removes the labels info1 and info2 from root
	 */
	void removeTutorialLabels() {
		root.getChildren().remove(info3);
		root.getChildren().remove(info4);
	}

	@Override
	void updateTutorialStep(MouseEvent me) {

	}

}
