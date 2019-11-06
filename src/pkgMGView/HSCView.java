package pkgMGView;

import java.util.ArrayList;

import javafx.scene.image.Image;
import pkgEnum.GameState;
import pkgEnum.GameType;
import pkgMover.Mover;

public class HSCView extends MinigameView {
	public Image HSCImage;
	
	
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
		HSCImage = loadImage("animals", "hcrab");
	}


}
