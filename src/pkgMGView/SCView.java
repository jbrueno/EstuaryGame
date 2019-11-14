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
import pkgMover.Food;
import pkgMover.Mover;
import pkgMover.Seaweed;
import pkgMover.Terrapin;
import pkgMover.Trash;

public class SCView extends MinigameView {
	
	Button btnReturn;
	final Game theGame = Game.SIDESCROLLER;
	Image background;
	Image terrapin;
	Image trash;
	Image food;
	Image seaweed;
	int itemHeight = 150;
	int itemWidth = 150;
	
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
		
		draw(movers);
		
		
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
		
		trash = new Image("/Mover/fullBottle.png");
		
		food = new Image("/Mover/clam_left_2.png");
		
		seaweed = new Image("/Mover/cordgrass.png");
	}

	@Override
	void draw(ArrayList<Mover> movers) {
		for (Mover m : movers) {
			if (m instanceof Terrapin) {
				gc.drawImage(terrapin, m.getX(), m.getY());
			} else if (m instanceof Trash) {
				gc.drawImage(trash, m.getX(), m.getY(), itemWidth, itemHeight);
			} else if (m instanceof Food) {
				gc.drawImage(food, m.getX(), m.getY(), itemWidth, itemHeight);
			} else if (m instanceof Seaweed) {
				gc.drawImage(trash, m.getX(), m.getY(), itemWidth, itemHeight);
			}
		}
		 
	}
}
