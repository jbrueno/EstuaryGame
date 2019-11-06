package pkgMGView;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class HSCView extends MinigameView{
	
	Image fHSC;
	Image mHSC;

	public HSCView(GraphicsContext gc, Group root, Scene scene) {
		g = Game.HSCCOUNT;
		this.root = root;
		this.scene = scene;
		this.gc = gc;

       
    	setUpListeners();

		importImages();
	}
	
	
	@Override
	public void update(ArrayList<DataNode> dns, GameState gs) {
		if (gs == GameState.INPROGRESS) {
			for (DataNode d : dns) {
				Mover m = (Mover) d;
				System.out.println("drawing");
				draw(m);
			}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	void draw(ArrayList<DataNode> dns) {
		for (DataNode dn : dns) {
			Mover m = (Mover) dn;
			draw(m);
		}
	}

	@Override
	void importImages() {
		fHSC = new Image("Mover/FemaleHSC.png");
		mHSC = new Image("Mover/MaleHSC.png");
	}
	
}
