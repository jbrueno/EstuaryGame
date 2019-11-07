package pkgMGView;

import java.util.ArrayList;
<<<<<<< HEAD
=======

import pkgEnum.GameState;
import pkgMover.Mover;

public class AMView extends MinigameView {

	@Override
	public void update(ArrayList<Mover> ms, GameState gs) {
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
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;

public class AMView extends MinigameView{

	public AMView(GraphicsContext gc, Group root, Scene scene) {
		g = Game.ANIMALMATCHING;
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
	void draw(ArrayList<DataNode> dns) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void importImages() {
		// TODO Auto-generated method stub
		
	}
}
