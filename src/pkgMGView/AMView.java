package pkgMGView;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;
import pkgMover.MatchingAnimal;

public class AMView extends MinigameView{
	Image turtle;
	Button btnReturn;
	Button btnHint;
	Button btnTurtle;
	Button btnDeer;
	Button btnMussel;
	Button btnCrab;
	Button btnClam;
	String guessingThis = "Turtle";
	boolean turtleDrawn = false;
	boolean questionAsked = false;
	
	
	public AMView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.ANIMALMATCHING);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		
		importImages();
		scene.addEventFilter(MouseEvent.ANY, eventHandler);
		
	}
	
	@Override
	public void update(ArrayList<Mover> movers, GameState gs) {
		if(!questionAsked) {
			System.out.println("Which one is the turtle?");
			questionAsked = true;
		}
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
		}
		if (gs == GameState.INPROGRESS) {
			draw(movers);
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
		btnReturn = new Button("Return");
		btnReturn.setLayoutX(0);
		btnReturn.setLayoutY(0);
		btnReturn.setOnAction(e -> {
			game = Game.MAINSCREEN;
		});
		root.getChildren().add(btnReturn);
		
	}

	@Override
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		for (Mover m : movers) {
			//TEMPORARY UNTIL IMAGES ARE GOTTEN!
			gc.strokeOval(m.getX() - m.getImageWidth() / 2, m.getY() - m.getImageHeight() / 2, 
					m.getImageWidth() * 2, m.getImageHeight() * 2);
		}
	}
	
	
	 

	@Override
	void importImages() {
		turtle = new Image("/Mover/bogturtle_left_0.gif");
	}
}
