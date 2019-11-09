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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.DataNode;
import pkgMover.Mover;

public class SCView extends MinigameView {
	
	Button btnReturn;
	final Game theGame = Game.SIDESCROLLER;
	
	public SCView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.SIDESCROLLER);
		game = theGame;
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
		});
		root.getChildren().add(btnReturn);
		
	}

	@Override
	void importImages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void draw(ArrayList<Mover> movers) {
		// TODO Auto-generated method stub
		 
	}
}
