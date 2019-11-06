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
		// TODO Auto-generated method stub
		
	}

	@Override
	void draw(ArrayList<DataNode> dns) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		for (DataNode dn : dns) {
			Mover m = (Mover) dn;
			draw(m);
			System.out.println("drawing");
		}
	}

	@Override
	void importImages() {
		fHSC = new Image("Mover/FemaleHSC.gif");
		mHSC = new Image("Mover/MaleHSC.gif");
	}
	
}
