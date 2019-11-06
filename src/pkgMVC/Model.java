package pkgMVC;
import pkgEnum.Direction;
import pkgMG.HSCModel;
import pkgMG.MinigameModel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**The Model class for the MVC design pattern
 * 
 * @author Ryan Peters
 *
 */
public class Model {
	private ArrayList<MinigameModel> minigames;
	private MinigameModel currGame;
	
	
	
	// added comment
	
	
	public Model() {
		HSCModel CRABBS = new HSCModel();
		currGame=CRABBS;
	}
	

	public void update() {
		currGame.update();
	}	
	
}
