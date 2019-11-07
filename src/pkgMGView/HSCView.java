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
			g = Game.MAINSCREEN;
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
	
	/**
	 * MainScreen Buttons can cause a switch between games. In this case, we want to send this new Game Enum
	 * to View in <code>getGame()</code> but then reset it so that MainScreen can be loaded again in the future.
	 */
	@Override
	public Game getGame() {
		Game gtTemp = g;
		g = Game.HSCCOUNT;
		return gtTemp;
	}
	
}
