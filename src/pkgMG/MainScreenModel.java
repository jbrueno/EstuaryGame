package pkgMG;

import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgMover.DataNode;

public class MainScreenModel extends MinigameModel{
	
	public MainScreenModel() {
		dns = new ArrayList<DataNode>();
		g = Game.MAINSCREEN;
	}

	@Override
	public void update(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}


}
