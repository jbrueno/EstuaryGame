package pkgMVC;
import pkgEnum.Direction;
<<<<<<< HEAD
import pkgEnum.Game;
import pkgMG.*;
import pkgMGView.AMView;
import pkgMGView.HSCView;
import pkgMGView.LeaderboardView;
import pkgMGView.MainScreenView;
import pkgMGView.MinigameView;
import pkgMGView.SCView;
import pkgMGView.WSView;
import pkgMover.DataNode;
=======
import pkgMG.HSCModel;
import pkgMG.MinigameModel;
import pkgMG.SCModel;
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.input.MouseEvent;

/**
 * Class responsible for keeping track of the current MinigameModel <code>currGame</code> and passing in updates from the 
 * View into this <code>currGame</code>.
 * 
 * 
 * @author Ryan Peters
 * @see View
 *
 */
public class Model {
	private ArrayList<MinigameModel> minigames;
<<<<<<< HEAD
	private MinigameModel currGame;	
=======
	private MinigameModel currGame;
    int canvasWidth;
    int canvasHeight;
	
	
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	
<<<<<<< HEAD
	/**
	 * Constructor that creates the list of MinigameModels <code>minigames</code>.
	 * 
	 * @author Ryan Peters
	 * @see createModels()
	 */
=======
	// added comment
	
	
	
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	public Model() {
<<<<<<< HEAD
		createModels();
=======
		SCModel SC = new SCModel(1300, 800, 0, 0, 1000);
		currGame = SC;
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	}
	
<<<<<<< HEAD
	/**
	 * 
	 * @param g
	 * @param me
	 */
	public void update(Game g, MouseEvent me) {
		if (!isCurrGame(g)) {
			currGame = minigames.get(g.ordinal());
		}
		currGame.update(me);
=======

	public void update() {
		currGame.update();
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	}	
	
	/**
	 * Checks is the passed in Game enum matches the <code>currGame</code>'s enum. Returns corresponding boolean
	 * 
	 * @author Ryan Peters
	 * @param g	Game enum to be checked against currGame Game enum
	 * @return	whether the parameter equals the currGame Game enum
	 */
	private boolean isCurrGame(Game g) {
		return currGame.getGame() == g;
	}
	
	/**
	 * Returns the list of DataNodes from the current MinigameModel being loaded (<code>currGame</code>)
	 * 
	 * @author Ryan Peters
	 * @return
	 * @see MinigameModel.getDataNodes()
	 */
	public ArrayList<DataNode> getDataNodes() {
		return currGame.getDataNodes();
	}
	
	/**
	 * Initializes and creates the list of MinigameModels to used in order to load the correct MinigameModel based on 
	 * Game enum.ordinal()
	 * <p>
	 * As default, sets the currGame to MainScreenModel as it is the first to be loaded
	 * <p>
	 * Note: adding must be done in this order so indexing by Game.ordinal() works
	 * 
	 * @author Ryan Peters
	 * @see MinigameModel
	 */
	private void createModels() {
		minigames = new ArrayList<MinigameModel>();
		
		minigames.add(new MainScreenModel());
		minigames.add(new AMModel());
		minigames.add(new HSCModel());
		minigames.add(new SCModel());
		minigames.add(new WSModel());
		minigames.add(new LeaderboardModel());
		
		currGame = minigames.get(0);	
	}
	
}
