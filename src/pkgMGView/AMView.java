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
		
		btnHint = new Button("Hint");
		btnHint.setLayoutX(backgroundWidth - 200);
		btnHint.setLayoutY(backgroundHeight / 2);
		root.getChildren().add(btnHint);
		btnHint.setOnAction(e -> {
			if(guessingThis.compareTo("Turtle") == 0) {
				System.out.println("I have a Shell");
			} else if(guessingThis.compareTo("Deer") == 0) {
				System.out.println("I have brown hair");
			} else if(guessingThis.compareTo("Mussel") == 0) {
				System.out.println("I'm dark and live in the water");
			} else if(guessingThis.compareTo("Crab") == 0) {
				System.out.println("I have a claws");
			} else if(guessingThis.compareTo("Clam") == 0) {
				System.out.println("Happy as a ____");
			}
		});
		
		btnTurtle = new Button("Turtle");
		btnTurtle.setLayoutX(backgroundWidth - 200);
		btnTurtle.setLayoutY((backgroundHeight / 2) + 50);
		root.getChildren().add(btnTurtle);
		btnTurtle.setOnAction(e -> {
			System.out.println("Turtle Selected");
		});
		
		btnDeer = new Button("Deer");
		btnDeer.setLayoutX(backgroundWidth - 200);
		btnDeer.setLayoutY((backgroundHeight / 2) + 100);
		root.getChildren().add(btnDeer);
		btnDeer.setOnAction(e -> {
			System.out.println("Deer Selected");
		});
		
		btnMussel = new Button("Mussel");
		btnMussel.setLayoutX(backgroundWidth - 200);
		btnMussel.setLayoutY((backgroundHeight / 2) - 50);
		root.getChildren().add(btnMussel);
		btnMussel.setOnAction(e -> {
			System.out.println("Mussel Selected");
		});
		
		btnCrab = new Button("Crab");
		btnCrab.setLayoutX(backgroundWidth - 200);
		btnCrab.setLayoutY((backgroundHeight / 2) - 100);
		root.getChildren().add(btnCrab);
		btnCrab.setOnAction(e -> {
			System.out.println("Crab Selected");
		});
		
		btnClam = new Button("Clam");
		btnClam.setLayoutX(backgroundWidth - 200);
		btnHint.setLayoutY((backgroundHeight / 2) - 150);
		root.getChildren().add(btnClam);
		btnClam.setOnAction(e -> {
			System.out.println("Clam Selected");
		});
		
	}

	@Override
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		for (Mover m : movers) {
			draw(m);
		}
	}
	
	
	 

	@Override
	void importImages() {
		turtle = new Image("/Mover/bogturtle_left_0.gif");
	}
}
