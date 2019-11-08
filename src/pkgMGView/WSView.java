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

public class WSView extends MinigameView{
	Image bottle;
	Image background;
	Button btnReturn;
	
	public WSView(GraphicsContext gc, Group root, Scene scene) {
		g = Game.WATERSAMPLING;
		this.root = root;
		this.scene = scene;
		this.gc = gc;

		importImages();
	}
	
	@Override
	public void update(ArrayList<Mover> movers, GameState gs) {
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
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);
		for (Mover m : movers) {
			draw(m);
		}
	}

	@Override
	void importImages() {
		background= new Image("backgrounds/WaterSample.png");
		bottle = new Image("Mover/Bottle.gif");
	}
	
	@Override
	public Game getGame() {
		Game gtTemp = g;
		g = Game.WATERSAMPLING;
		return gtTemp;
	}

}
