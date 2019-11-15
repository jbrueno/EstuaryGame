package pkgMGView;

//import java.awt.event.MouseEvent;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class HSCView extends MinigameView{
	
	Image imgHSC;
	Button btnReturn;

	public HSCView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.HSCCOUNT);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		
		scene.addEventFilter(MouseEvent.ANY, eventHandler);
		setUpListeners();
		importImages();
	}
	
	
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score) {
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
		}
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
		root.getChildren().add(btnReturn);
	}
	
	@Override
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		for (Mover m : movers) {
			draw(m);
		}
	}

	@Override
	void importImages() {
		imgHSC = new Image("Mover/HSC.png");
	}
}
