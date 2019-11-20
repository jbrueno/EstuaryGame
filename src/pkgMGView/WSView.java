package pkgMGView;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class WSView extends MinigameView{
	Image bottle;
	Image background; // used to switch between different backgrounds
	Image background_collect;
	Image background_lab;
	Image testTube;
	Image pHScale;
	Button btnReturn;
	Rectangle phStripBase;
	Rectangle phStripColor;
	
	// pHScale Image dimensions & location
	int pHScaleX = 300;
	int pHScaleY = 0;
	int pHScaleWidth = backgroundWidth - (pHScaleX * 2);
	int pHScaleHeight = backgroundHeight / 5;
	
	//
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
	public void update(ArrayList<Mover> movers, GameState gs, int score) {
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
			createScoreLabel(score);
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
		gc.drawImage(pHScale, pHScaleX, pHScaleY, pHScaleWidth, pHScaleHeight);
		for (Mover m : movers) {
			if(m.getValue().compareTo("PHStrip") != 0) { // draw all objects except PHStrip (no image)
				draw(m);
			}
		}
	}
	
	/*
	void setUpLab() {
		gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(pHScale, pHScaleX, pHScaleY, pHScaleWidth, pHScaleHeight);
	}
	*/
	

	@Override
	void importImages() {
		background_collect = new Image("backgrounds/WaterSample.png");
		bottle = new Image("Mover/Bottle.png");
		background_lab = new Image("backgrounds/lab_background.png");
		testTube = new Image("Mover/testtube.png");
		pHScale = new Image("Backgrounds/pH_scale.png");
		
	}
}