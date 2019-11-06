package pkgMGView;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;

public class AMView extends MinigameView{

	public AMView(GraphicsContext gc, Group root, Scene scene) {
		this.gt = Game.ANIMALMATCHING;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		
    	setUpListeners();
		importImages();
	}
	
	@Override
	public void update(ArrayList<DataNode> dns, GameState gs) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void importImages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

}
