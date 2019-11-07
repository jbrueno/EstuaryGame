package pkgMG;

import javafx.scene.input.MouseEvent;
<<<<<<< HEAD
import pkgEnum.Game;
=======
import pkgEnum.GameType;
import pkgMover.Mover;

public class AMModel extends MinigameModel{
	
	private final GameType gameType = GameType.ANIMALMATCHING;
	Mover[] animals;
	@Override
	void update(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	void handleBorderCollision(Mover m) {
		// TODO Auto-generated method stub
		
	}
	
	
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0

public class AMModel extends MinigameModel {
	
	public AMModel() {
		g = Game.ANIMALMATCHING;
	}


	@Override
	public void update(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}
}
