package pkgMVC;
import pkgEnum.Direction;
import pkgEnum.GameType;
import pkgMG.*;
import pkgMGView.AMView;
import pkgMGView.HSCView;
import pkgMGView.LeaderboardView;
import pkgMGView.MainScreenView;
import pkgMGView.MinigameView;
import pkgMGView.SCView;
import pkgMGView.WSView;
import pkgMover.DataNode;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.input.MouseEvent;

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
		createModels();
		currGame = minigames.get(0);
	}
	

	public void update(GameType gt, MouseEvent me) {
		System.out.println(gt + " " + currGame.getGameType());
		if (gt != GameType.MAINSCREEN) {
			if (!isCurrGame(gt)) {
				//currGame = minigames.get(gt.ordinal());
				currGame = minigames.get(0);
			}
			
			currGame.update(me);
		}
		
		
		
	}	
	
	private boolean isCurrGame(GameType gt) {
		return currGame.getGameType() == gt;
	}
	
	public ArrayList<DataNode> getDataNodes() {
		if (currGame.getGameType() == GameType.MAINSCREEN) {
			return new ArrayList<DataNode>();
		}
		return currGame.getDataNodes();
	}
	
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
