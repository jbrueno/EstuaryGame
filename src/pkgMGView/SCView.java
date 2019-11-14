package pkgMGView;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.DataNode;
import pkgMover.Mover;

public class SCView extends MinigameView {
	
	Button btnReturn;
	final Game theGame = Game.SIDESCROLLER;
	Image background;
	Image terrapin;
	Image trash;
	Image food;
	Image seaweed;
	
	public SCView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.SIDESCROLLER);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;

		importImages();
	}
	

	@Override
	public void update(ArrayList<Mover> movers, GameState gs) {
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
		}
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
		EventHandler<MouseEvent> eh = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println(event.getX());
				
			}
			
		};
		
		btnReturn = new Button("Return");
		btnReturn.setLayoutX(0);
		btnReturn.setLayoutY(0);
		btnReturn.setOnAction(e -> {
			game = Game.MAINSCREEN;
		});
		root.getChildren().add(btnReturn);
		
	}

	@Override
	void importImages() {
		background = new Image("/backgrounds/underwater2.png");
		
		terrapin = new Image("/Mover/bogturtle_right_0.png");
		
		
	}

	@Override
	void draw(ArrayList<Mover> movers) {
		// TODO Auto-generated method stub
		 
	}
}
