package pkgMGView;

import java.util.ArrayList;
<<<<<<< HEAD
=======

import javafx.scene.image.Image;
import pkgEnum.GameState;
import pkgMover.Mover;

public class SCView extends MinigameView {
	
	int canvasWidth = 1380;
	int canvasHeight = 940;
	
	int terrapinImageWidth = 100;
	int terrapinImageHeight = 100;
	
	
	double terryX;
	double terryY;
	

	@Override
	public void update(ArrayList<Mover> ms, GameState gs) {
		
	}
	
	public void update(double turtleX, double turtleY, ArrayList<Mover> movers) {
		terryX = turtleX;
		terryY = turtleY;
		
			
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

public class SCView extends MinigameView{

	public SCView(GraphicsContext gc, Group root, Scene scene) {
		g = Game.SIDESCROLLER;
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
