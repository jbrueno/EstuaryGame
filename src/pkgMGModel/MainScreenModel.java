package pkgMGModel;

import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgMVC.Model;
import pkgMover.Mover;

public class MainScreenModel extends MinigameModel{
	private static final long serialVersionUID = 15L;
	public MainScreenModel() {
		movers = new ArrayList<Mover>();
		g = Game.MAINSCREEN;
	} 

	@Override
	public void update(MouseEvent me) {		
		//no updates
	}


}