package pkgMG;

import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class MainScreenModel extends MinigameModel{
	
	public MainScreenModel() {
		movers = new ArrayList<Mover>();
		g = Game.MAINSCREEN;
	}

	@Override
	public void update(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}


}
