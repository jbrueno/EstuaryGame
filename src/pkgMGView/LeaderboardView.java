package pkgMGView;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class LeaderboardView extends MinigameView{
	

	public LeaderboardView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.LEADERBOARD);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;

    	setUpListeners();

		importImages();
	}
	
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		// TODO Auto-generated method stub
		 
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
	void importImages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void draw(ArrayList<Mover> movers) {
		// TODO Auto-generated method stub
		
	}

}
