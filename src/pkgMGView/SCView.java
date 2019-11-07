package pkgMGView;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.DataNode;
import pkgMover.Mover;

public class SCView extends MinigameView {
	
	Button btnReturn;
	
	public SCView(GraphicsContext gc, Group root, Scene scene) {
		g = Game.HSCCOUNT;
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
			g = Game.MAINSCREEN;
		});
		root.getChildren().add(btnReturn);
		
	}

	@Override
	void importImages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void draw(ArrayList<DataNode> dns) {
		// TODO Auto-generated method stub
		
	}
}
