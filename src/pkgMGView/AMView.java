package pkgMGView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;
import pkgMGModel.AMModel;
import pkgMGModel.AMModel.MatchingAnimal;

public class AMView extends MinigameView{
	Button btnReturn;
	final private int clueXBuffer = 300;
	final private int clueYBuffer = 75;
	final private int clueWidth = 250;
	final private int clueHeight = 70;
	final private int clueSpacing = 10;
	private VBox clueBox;
	private HashMap<String, ClueList> clueBank;
	private Button btnHint;
	private Button selectedButton;
	
	
	public AMView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.ANIMALMATCHING);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		
		importImages();
		scene.addEventFilter(MouseEvent.ANY, eventHandler);
	}
	

	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		if (!areButtonsMade) {
			setUpListeners();
			storeClues(movers);
			areButtonsMade = true;	
		}
		createScoreLabel(score);
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
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		for (Mover m : movers) {
			//TEMPORARY UNTIL IMAGES ARE GOTTEN!
			gc.strokeOval(m.getX() - m.getImageWidth() / 2, m.getY() - m.getImageHeight() / 2, 
					m.getImageWidth() * Math.sqrt(2), m.getImageHeight() * Math.sqrt(2));
		}
	}
	
	private void storeClues(ArrayList<Mover> movers) {
		clueBank = new HashMap<String, ClueList>();
		clueBox = new VBox();
		clueBox.setAlignment(Pos.CENTER_RIGHT);
		clueBox.setTranslateX(backgroundWidth - clueXBuffer);
		clueBox.setTranslateY(clueYBuffer);
		clueBox.setSpacing(clueSpacing);
		
		
		for (Mover m : movers) {
			AMModel.MatchingAnimal ma = (AMModel.MatchingAnimal) m;
			clueBank.put(ma.getValue(), new ClueList(ma.getValue(), ma.getClues()));
			Button b = new Button();
			b.setId(ma.getValue());
			b.setText(clueBank.get(b.getId()).getIterator().next());
			b.setPrefHeight(clueHeight);
			b.setPrefWidth(clueWidth);
			b.setWrapText(true);
			b.setAlignment(Pos.CENTER);
			b.setOnMouseClicked(e -> {selectedButton = b;});
			b.setOnDragDetected(e -> {me = e;});
			b.setOnMouseDragReleased(e -> {me = (MouseEvent) e;});
			clueBox.getChildren().add(b);
		}
		
		btnHint = new Button("HINT");
		btnHint.setPrefHeight(clueHeight);
		btnHint.setPrefWidth(clueWidth);
		btnHint.setOnMouseClicked(e -> {
			if (selectedButton != null) {
				selectedButton.setText(clueBank.get(selectedButton.getId()).getIterator().next());
			}
		});
		clueBox.getChildren().add(btnHint);
		
		root.getChildren().add(clueBox);
	}
 

	@Override
	void importImages() {

	}
	
	/**
	 * A nested class that holds the clues with an iterator that loops through the list of clues continuously
	 * <p>
	 * In gameplay, we want the player to be able to "scroll" through the clues by clicking them then clicking "Hint" button.
	 * This way, there is less logic to handle for a for-loop or other implementation (I hope)
	 * 
	 * @author Ryan Peters
	 *
	 */
	private class ClueList {
		private String[] clues;
		private InfiniteIterator infit = new InfiniteIterator();
		ClueList(String value, String[] clues) {
			this.clues = clues;
		}
		
		public InfiniteIterator getIterator() {
			return infit;
		}
		
		private class InfiniteIterator implements Iterator{
			int cursor;
			InfiniteIterator() {cursor = 0;}

			public String next() {
				cursor = cursor % clues.length;
				return clues[cursor++];
			}

			public boolean hasNext() {return true;}
		}
	}	
}