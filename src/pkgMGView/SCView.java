package pkgMGView;

import java.util.ArrayList;

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

	
	

}
