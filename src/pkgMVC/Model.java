package pkgMVC;
import pkgEnum.Direction;
import pkgMG.HSCModel;
import pkgMG.MinigameModel;
import pkgMG.SCModel;

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
    int canvasWidth;
    int canvasHeight;
	
	
	
	// added comment
	
	
	
	public Model() {
		SCModel SC = new SCModel(1300, 800, 0, 0, 1000);
		currGame = SC;
	}
	

	public void update() {
		currGame.update();
	}	
	
}
