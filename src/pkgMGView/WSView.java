package pkgMGView;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class WSView extends MinigameView{
	// WS_COLLECT
	Image bottle;
	Image background; // used to switch between different backgrounds
	Image background_collect;
	Button btnReturn;
	
	
	// WS_PH
	Image background_lab;
	Image testTube;
	Image phStrip;
	Color phColor;
	Rectangle phStripBase;
	Rectangle phStripColor; // just the tip of pH strip should get colored (smaller rect, overtop pHStripBase)
	
	
	
	Image pHStrip;
	// pHScale Image dimensions & location
	Image pHScale;
	int pHScaleX = 300;
	int pHScaleY = 0;
	int pHScaleWidth = backgroundWidth - (pHScaleX * 2);
	int pHScaleHeight = backgroundHeight / 5;

	Rectangle pHGuessBox; // Rectangle "Holding" the labels and buttons for guessing the pH
	int pHGuessBoxX = backgroundHeight / 2;; // x-Loc
	int pHGuessBoxY = backgroundHeight / 2; // y-Loc
	int pHGuessBoxWidth = 300;
	int pHGuessBoxHeight = 500;
	
	
	
	public WSView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.WATERSAMPLING);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		
		scene.addEventFilter(MouseEvent.ANY, eventHandler);
		importImages();
		setUpListeners();
		
		
	} 
	
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
			createScoreLabel(score);
			//drawpHGuessBox();
		}
		updateScoreLabel(score);
		
		System.out.println("gs: " + gs);
		
		switch (gs) {
		case WS_COLLECT :
			background = background_collect;
			break;
		case WS_TEMP :
			background = background_lab;
			break;
		case WS_PH :
			background = background_lab;
			break;
		default:
			break;
		
		}
		
		draw(movers);

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
		
		btnReturn = new Button("Return");
		btnReturn.setLayoutX(0);
		btnReturn.setLayoutY(0);
		btnReturn.setOnAction(e -> {
			game = Game.MAINSCREEN;
			removeScoreLabel();
		});
		root.getChildren().add(btnReturn);
		
	}

	@Override
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);

		for (Mover m : movers) {
				draw(m);
		}
	}
	public void drawpHGuessBox() {
		pHGuessBox = new Rectangle();
		pHGuessBox.setX(pHGuessBoxX);
		pHGuessBox.setY(pHGuessBoxY);
		pHGuessBox.setWidth(pHGuessBoxWidth);
		pHGuessBox.setHeight(pHGuessBoxHeight);
		pHGuessBox.setFill(Color.PEACHPUFF);
		root.getChildren().add(pHGuessBox);
	}
	
	@Override
	void importImages() {
		background_collect = new Image("backgrounds/WaterSample.png");
		bottle = new Image("Mover/Bottle.png");
		background_lab = new Image("backgrounds/lab_background.png");
		testTube = new Image("Mover/testtube.png");
		pHStrip=new Image("Mover/pHStrip.png");
	}
}