package pkgMGView;


import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.DataNode;
import pkgMover.Food;
import pkgMover.Mover;
import pkgMover.Seaweed;
import pkgMover.Terrapin;
import pkgMover.Trash;

public class SCView extends MinigameView  {
	
	Button btnReturn;
	final Game theGame = Game.SIDESCROLLER;
	Image background;
	Image terrapin;
	Image trash;
	Image food;
	Image seaweed;
	int itemHeight = 150;
	int itemWidth = 150;
	int foodHeight = 50;
	int foodWidth = 50;
	double mouseX;
	double mouseY;
	
	
	public SCView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.SIDESCROLLER);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		importImages();
		setUpListeners();
		scene.addEventFilter(MouseEvent.ANY, eventHandler);
		
		
		
	}
	

	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score) {
		 updateScoreLabel(score);
		
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
			createScoreLabel(score);
		}
		
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);
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
		
		
		btnReturn = new Button("Return");
		btnReturn.setLayoutX(0);
		btnReturn.setLayoutY(0);
		btnReturn.setOnAction(e -> {
			game = Game.MAINSCREEN;
			removeScoreLabel();
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
	public void draw(ArrayList<Mover> movers) {
		
		for (Mover m : movers) {
			if (m instanceof Terrapin) {
				gc.drawImage(terrapin, m.getX(), m.getY());
			} else if (m instanceof Trash) {
				gc.drawImage(trash, m.getX(), m.getY(), itemWidth, itemHeight);
			} else if (m instanceof Food) {
				gc.drawImage(food, m.getX(), m.getY(), foodWidth, foodHeight);
			} else if (m instanceof Seaweed) {
				gc.drawImage(seaweed, m.getX(), m.getY(), itemWidth, itemHeight);
			}
		}
		 
	}
	
	
	public void makeScoreBox() {
		scoreBox.setCache(true);
		scoreBox.setX(backgroundWidth);
		scoreBox.setY(backgroundHeight);
		scoreBox.setFill(Color.RED);
		scoreBox.setText("0");
		scoreBox.setFont(Font.font(null, FontWeight.BOLD, 32));
		
		System.out.println("score box made");
	}
	
	public double getMouseX() {
		return this.mouseX;
	}
	
	public double getMouseY() {
		return this.mouseY;
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		me = e;
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		me = e;
	}
	
	@Override
	public MouseEvent getMouseEvent() {
		return me;
	}

	
}