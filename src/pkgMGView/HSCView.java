package pkgMGView;

//import java.awt.event.MouseEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

//import com.sun.javafx.geom.Rectangle;
//import com.sun.prism.paint.Color;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class HSCView extends MinigameView { 

	Image imgHSC;
	Button btnReturn;
	Group lighting = new Group();

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
	
	//Rectangle light = lighting();

	@Override
	public void update(ArrayList<Mover> movers, GameState gs) {
		createLightFX();
		
		setUpListeners();
		
		if (gs == GameState.INPROGRESS) {
			draw(movers);
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

	void setUpListeners() {
		btnReturn = new Button("Return");
		btnReturn.setLayoutX(0);
		btnReturn.setLayoutY(0);
		btnReturn.setOnAction(e -> {
			game = game.MAINSCREEN;
		});
		lighting.getChildren().add(btnReturn);
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
		background= new Image("backgrounds/TempHSC.png");
		imgHSC = new Image("Mover/HSC.gif");
	}
	
	
	/**
	 * Starts by clearing the 'lighting' Group, removing, and re-adding it to root in order for the rectangle to be redrawn.
	 * Then, creates a rectangle with a radial gradient fill and adds it to 'lighting' in order for the rectangle to appear
	 * over the HSC's.
	 * 
	 * @author jbrueno
	 */
	public void createLightFX() {
		lighting.getChildren().clear();
		root.getChildren().remove(lighting);
		root.getChildren().add(lighting);
		
		RadialGradient light = new RadialGradient(0, 0, me.getX()/backgroundWidth, me.getY()/backgroundHeight, 0.2, true, CycleMethod.NO_CYCLE,  new Stop[] {
		        new Stop(0.15, Color.TRANSPARENT),
		        new Stop(1, Color.BLACK)
		    });

		Rectangle rect = new Rectangle(backgroundWidth, backgroundHeight, light);
		rect.setOpacity(0.985);

		lighting.getChildren().add(rect);
	}

	
	

}
