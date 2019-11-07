package pkgMGView;

import java.util.ArrayList;

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
	
	Image fHSC;
	Image mHSC;
	Button btnReturn;

	public HSCView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.HSCCOUNT);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;

		importImages();
	}
	
	
	@Override
	public void update(ArrayList<DataNode> dns, GameState gs) {
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
		}
		if (gs == GameState.INPROGRESS) {
			draw(dns);
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
	void draw(ArrayList<DataNode> dns) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		for (DataNode dn : dns) {
			Mover m = (Mover) dn;
			draw(m);
		}
	}

	@Override
	void importImages() {
		fHSC = new Image("Mover/FemaleHSC.gif");
		mHSC = new Image("Mover/MaleHSC.gif");
	}	
}
