package pkgMGView;

import java.util.ArrayList;
<<<<<<< HEAD
=======

import javafx.scene.image.Image;
import pkgEnum.GameState;
import pkgEnum.GameType;
import pkgMover.Mover;

public class HSCView extends MinigameView {
	public Image Terrapin;
	
	
	@Override
	public void update(ArrayList<Mover> ms, GameState gs) {
		
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
		
	}

>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0

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
		fHSC = new Image("Mover/FemaleHSC.gif");
		mHSC = new Image("Mover/MaleHSC.gif");
	}
}
