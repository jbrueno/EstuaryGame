package pkgMGView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Timer;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;
import pkgMGModel.AMModel;
import pkgMGModel.AMModel.MatchingAnimal;

public class AMView extends MinigameView{
	final private int clueXBuffer = 300;
	final private int clueYBuffer = 75;
	final private int clueWidth = 250;
	final private int clueHeight = 70;
	final private int clueSpacing = 10;
	private VBox clueBox;
	private HashMap<String, ClueList> clueBank;
	private Button btnHint;
	private Button selectedButton;
	
	
	//bonus quiz attributes
	private boolean isBonusQuizMade = false;
	private HBox optionsBox = new HBox();
	final private int optionYBuffer = 100;
	final private int optionsSpacing = 50;
	final private int optionWidth = 200;
	final private int optionHeight = 30;
	final private int optionBtnWidth = 200;
	final private int optionBtnHeight = 150;
	final private String question = "Who am I?";
	private Mover chosenMover;
	private ArrayList<Button> optionButtons;
	private GridPane bqGP = new GridPane();
	final private int panePadding = 50;
	private boolean bqGuessed = false;
	private boolean buttonsDisabled = false;
	
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
		switch (gs) {
			case INPROGRESS:
				if (!areButtonsMade) {
					storeClues(movers);
					areButtonsMade = true;	
				}
				
				for (Mover m : movers) {
					MatchingAnimal ma = (MatchingAnimal) m;
					try {
						Button b;
						for (Node n : clueBox.getChildren()) {
							b = (Button) n;
							if (ma.getIsMatched() && b.getId().equals(ma.getValue())) {
								disableButton(b);
								b.setText((clueBank.get(b.getId())).getLast());
								b.setBorder(Border.EMPTY);
							}
							if (me.getEventType() != MouseEvent.MOUSE_DRAGGED && me.getEventType() != MouseEvent.DRAG_DETECTED ) {
								if (b.getBorder() != Border.EMPTY) {
									b.setText(clueBank.get(b.getId()).getIterator().next());
									b.setBorder(Border.EMPTY);
								}
							}
						}
					} catch (ClassCastException e) {} catch (NullPointerException e) {}
				}
				createScoreLabel(score);
				draw(movers);				
				break;
			case BONUS:
				if (!isBonusQuizMade) {
					gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
					clearRootExceptCanvas();
					isBonusQuizMade = true;
					setUpBonusQuiz(movers);
				}
				createScoreLabel(score);
				
				if (bqGuessed) {
					if (!buttonsDisabled) {
						for (Node n : root.getChildren()) {
							try {
								VBox vbox = (VBox) n;
								for (Node nn : vbox.getChildren()) {
									Button b = (Button) nn;
									b.setDisable(true);
								}
							} catch (ClassCastException e) {}
						}
						buttonsDisabled = true;
						
						backToMainButton();
					}
					
					drawGameOver();
					
					
				break;	
				}
				
			default:
				break;
		}
		
	}

	@Override
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0);
		for (Mover m : movers) {
			draw(m);
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
			setMatchingButtonStyle(b);
			b.setOnMouseClicked(e -> {selectedButton = b;});
			b.setOnDragDetected(e -> {
				me = e;
				b.setBorder(new Border(new BorderStroke(Color.BLUE, 
			            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
			});
			b.setOnMouseDragReleased(e -> {me = (MouseEvent) e;});
			b.setOnMouseDragged(e -> {me = e;});
			clueBox.getChildren().add(b);
		}
		
		btnHint = new Button("HINT");
		btnHint.setPrefHeight(clueHeight);
		btnHint.setPrefWidth(clueWidth);
		btnHint.setId("hint");
		setMatchingButtonStyle(btnHint);
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
		background = new Image("backgrounds/background_animalmatching.png");
	}
	
	private void setUpBonusQuiz(ArrayList<Mover> movers) {
		//format question
		Label qLabel = new Label(question);
		qLabel.setFont(new Font("Arial", 50));
		qLabel.setTextAlignment(TextAlignment.CENTER);
		GridPane.setHalignment(qLabel, HPos.CENTER);
		bqGP.add(qLabel, 0, 0);
		
		//format image
		optionButtons = chooseOptions(movers);
		ImageView chosenImg = new ImageView(loadImage(chosenMover));
		chosenImg.setFitHeight(400);
		chosenImg.setFitWidth(400);
		chosenImg.setPreserveRatio(true);
		GridPane.setHalignment(chosenImg, HPos.CENTER);
		bqGP.add(chosenImg, 0,1);
		
		//format multiple choice box/buttons
		optionsBox.setAlignment(Pos.CENTER);
		optionsBox.setSpacing(clueSpacing*2);
		optionsBox.getChildren().addAll(optionButtons);
		GridPane.setHalignment(optionsBox, HPos.CENTER);
		bqGP.add(optionsBox, 0, 2);
		
		//format gridpane 
		bqGP.setPrefHeight(backgroundHeight);
		bqGP.setPrefWidth(backgroundWidth);
		ColumnConstraints column = new ColumnConstraints();
	    column.setPercentWidth(100);
	    bqGP.getColumnConstraints().add(column);
	    bqGP.setPadding(new Insets(panePadding, panePadding, panePadding, panePadding));
	    bqGP.setVgap(panePadding/2);
		
		root.getChildren().add(bqGP);		
	}
	
	private ArrayList<Button> chooseOptions(ArrayList<Mover> movers) {
		//first mover is always the correct answer (until shuffle; this way model-view know the same info)
		chosenMover = movers.get(0);
		movers.remove(0);
		
		ArrayList<Button> btns = new ArrayList<Button>();
		for (Mover m : movers) {
			
			Button b = new Button(splitWordOnCaps(m.getValue()));
			b.setId(m.getValue());
			setBQButtonStyle(b);
			b.setOnMouseClicked(e -> {
				me = e;
				System.out.println(me.getEventType() + " " + me.getSource());
				b.setStyle("-fx-background-color: rgba(255,0,0,0.25);");
				bqGuessed = true;
			});
			b.setPrefHeight(optionBtnHeight);
			b.setPrefWidth(optionBtnWidth);
			btns.add(b);
		}
		
		Button bChosen = new Button(splitWordOnCaps(chosenMover.getValue()));
		bChosen.setText(splitWordOnCaps(chosenMover.getValue()));
		bChosen.setId(chosenMover.getValue());
		setBQButtonStyle(bChosen);
		bChosen.setOnMouseClicked(e -> {
			me = e;
			System.out.println(me.getEventType() + " " + me.getSource());
			bChosen.setStyle("-fx-background-color: white; -fx-text-fill: green;-fx-font-weight: bold;fx-font-size: 15");
			bqGuessed = true;
			
		});
		bChosen.setPrefHeight(optionBtnHeight);
		bChosen.setPrefWidth(optionBtnWidth);
		btns.add(bChosen);
		
		Collections.shuffle(btns);
		return btns;
	}
	
	private void setMatchingButtonStyle(Button b) {
		//set background as white and bold the text, increase font size
		b.setStyle("-fx-background-color: #ffffff;-fx-font-weight: bold;-fx-font-size: 13;");
		//keep text in button
		b.setWrapText(true);
		//center align text
		b.setTextAlignment(TextAlignment.CENTER);
		//set padding
		b.setPadding(new Insets(2,2,2,2));
	}
	
	private void setBQButtonStyle(Button b) {
		//set background as white and bold the text, give black border, increase font size
		b.setStyle("-fx-background-color: #ffffff;-fx-font-weight: bold;-fx-font-size: 15;");
		//keep text in button
		b.setWrapText(true);
		//center align text
		b.setTextAlignment(TextAlignment.CENTER);
	}
	
	private String splitWordOnCaps(String v) {
		String[] words = v.split("(?=[A-Z])");
		String word = "";
		for (String w : words) {
			word = word + w + " ";			
		}
		return word.trim();
	}
	
	
	/**
	 * A nested class that holds the clues with an iterator that loops through the list of clues continuously
	 * <p>
	 * In gameplay, we want the player to be able to "scroll" through the clues by clicking them then clicking "Hint" button.
	 * This way, there is less logic to handle for a for-loop or other implementation 
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
		
		public InfiniteIterator getIterator() {return infit;}
		public String getLast() {return this.clues[clues.length - 1];}
		
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



	@Override
	void startTimer(int ms) {}
	@Override
	void stopTimer() {}	
}