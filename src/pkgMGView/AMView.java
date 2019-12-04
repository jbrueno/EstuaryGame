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

/**
 * The View for Animal Matching minigame. All handling of this class is done by the <code>View</code> when this View is loaded into 
 * <code>currGame</code> as the current game for updating/playing.
 * 
 * @author Ryan Peters
 * @see MinigameView
 *
 */
public class AMView extends MinigameView{
	final private int clueXBuffer = 300;
	final private int clueYBuffer = 75;
	final private int clueWidth = 250;
	final private int clueHeight = 70;
	final private int clueSpacing = 10;
	private VBox clueBox;
	private HashMap<String, ClueList> clueBank = new HashMap<String, ClueList>();
	private Button btnHint;
	private Button selectedButton = new Button();
	private Button draggingButton;
	
	
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
	
	//tutorial attributes
	/*private int promptYBuffer = 10;
	private int promptWidth = 500;
	private int promptHeight = 10;
	private Label prompt;*/
	
	private boolean transition1SetUp = false;
	
	/**
	 * Constructor which saves local copies of GraphicsContext, Root, and Scene from <code>View</code> so that
	 * graphics may be loaded upon them.
	 * @param gc	<code>GraphicsContext</code> from <code>View</code>
	 * @param root	<code>root</code> from <code>View</code>
	 * @param scene	<code>scene</code> from <code>View</code>
	 */
	public AMView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.ANIMALMATCHING);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		
		importImages();
		scene.addEventFilter(MouseEvent.ANY, eventHandler);
	}
	

	/**
	 * The main method for AMView that calls all other methods used within the class. Takes the GameState <code>gs</code> and runs/loads
	 *  the proper set of GUI for that game including all images dictated by <code>movers</code> from <code>AMModel</code>
	 *  
	 *  @author Ryan Peters
	 *  @param movers	list of Movers for drawing and loading in GUI
	 *  @param gs		Enum used to determine which part of the game should be loaded
	 *  @param score	number of points user has accumulated for this game so far
	 *  @param time		current amount of time left to be displayed on timer (not used in this game)
	 */
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		switch (gs) {
			case TUTORIAL:
				if (!isTutorialSetUp) {
						storeTutorialMoverAsButton(movers);
						setUpTutorial();
						isTutorialSetUp = true;
				}
				
				if (me.getEventType() != MouseEvent.MOUSE_DRAGGED && me.getEventType() != MouseEvent.DRAG_DETECTED) {
					draggingButton.setVisible(false);
				}
				
				for (Mover m : movers) {
					AMModel.MatchingAnimal ma = (AMModel.MatchingAnimal) m;
					for (Node n : clueBox.getChildren()) {
						Button b = (Button) n;
						if (ma.getIsMatched() && b.getId().equals(ma.getValue())) {
							disableButton(b);
							b.setText((clueBank.get(b.getId())).getLast());
							b.setBorder(Border.EMPTY);
						}
					}
				}
				
				
				draw(movers);
				break;
			case TRANSITION1:
				if (!transition1SetUp) {
					prompt.setText("Good job! Let's try some more!");
					drawPlayButton();
					for (Node n : clueBox.getChildren()) {
						try {
							((Button) n).setDisable(true);
						} catch (ClassCastException e) {}
						
					}
					transition1SetUp = true;
				}
				
				if(me.getEventType() != MouseEvent.MOUSE_DRAGGED && me.getEventType() != MouseEvent.DRAG_DETECTED) {
					draggingButton.setVisible(false);
				}
				break;
			case INPROGRESS:
				if (!areButtonsMade) {
					clearRootExceptCanvas();
					storeClues(movers);
					areButtonsMade = true;	
				}
				
				//reorder loops for less calls (later)
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
							if (me.getEventType() == MouseEvent.MOUSE_DRAGGED || me.getEventType() == MouseEvent.DRAG_DETECTED ) {
								if (b.getBorder() != Border.EMPTY) {
									b.setBorder(Border.EMPTY);
								}
							} else {
								draggingButton.setVisible(false);
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
				
				//call optionsBox directly; reorder loops
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
		System.out.println(me.getEventType());
	}

	/**
	 * Clears the <code>Canvas</code> and then draws each Mover.
	 * 
	 * @author Ryan Peters
	 * @param movers	list of Movers to be drawn 
	 * @see MinigameView.update()
	 */
	@Override
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0);
		for (Mover m : movers) {
			draw(m);
		}
	}
	
	/**
	 * On first tick of the game only (handled in <code>update()</code>), store all Movers as Buttons within an <code>VBox</code>
	 * called <code>clueBox</code>. Also, sets up Hint Button <code>btnHint</code> and <code>draggingButton</code>, that button that
	 * follows the mouse when dragging from clue to image.
	 * 
	 * @author Ryan Peters
	 * @param movers	list of Movers for the clues to be 
	 */
	private void storeClues(ArrayList<Mover> movers) {
		clueBox = new VBox();
		clueBox.setAlignment(Pos.CENTER_RIGHT);
		clueBox.setTranslateX(backgroundWidth - clueXBuffer);
		clueBox.setTranslateY(clueYBuffer);
		clueBox.setSpacing(clueSpacing);
		
		makeDraggingButton();
		
		for (Mover m : movers) {
			clueBox.getChildren().add(createMatchingAnimalButton((AMModel.MatchingAnimal) m));
		}
		
		btnHint = createHintButton();
		clueBox.getChildren().add(btnHint);
		root.getChildren().addAll(clueBox);
	}
 
	/**
	 * Loads all predefined/known images initially needed for the game. Others are loaded in during runtime
	 * 
	 * @author Ryan Peters
	 * @see MiniGameView
	 */
	@Override
	void importImages() {
		background = loadBackgroundImage("background_animalmatching");
	}
	
	/**
	 * Creates the Bonus Quiz prompted at the end of the matching. Initializes question <code>Label</code>, question image 
	 * <code>chosenImg</code>, and multiple choice Buttons all within a 1-column <code>GridPane</code> <code>bqGP</code>.
	 * 
	 * @author Ryan Peters
	 * @param movers	list of Movers to be passed in chooseOptions()
	 * @see chooseOptions(ArrayList<Mover> movers)
	 */
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
	
	/**
	 * From the given animals (<code>movers</code>), picks the first <code>Mover</code> as the answer (<code>chosenmover</code>)
	 * by default so that the answer is known AMView-relative. Then, from the other Movers, pick 2 more as wrong answers randomly.
	 * Makes these 3 Movers into buttons that can be selected with the appropriate color set if picked and ID. Finally, randomizes the 
	 * buttons so that the first Button is not always the answer.
	 * <p>
	 * Used in setting up the Bonus Quiz. Each button represents a multiple-choice answer the user must choose from corresponding to the 
	 * image draw center-screen.
	 * 
	 * @author	Ryan Peters
	 * @param movers	list of <code>Mover</code>s to draw Multiple-Choice answers from
	 * @return		list of Multiple-Choice Buttons for the user to choose from
	 * @see	setUpBonusQuiz(ArrayList<Mover> movers)
	 */
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
	
	/**
	 * The default style for a Matching button in the main game, including the Hint Button <code>btnHint</code> and
	 * <code>draggingButton</code>
	 * 
	 * @author Ryan Peters
	 * @param b		Button to be formatted 
	 * @see storeClues(ArrayList<Mover> movers)
	 */
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
	
	/**
	 * The default style for a BQ (Bonus Quiz) button
	 * 
	 * @author Ryan Peters
	 * @param b		Button to be formatted
	 * @see chooseOptions(ArrayList<Mover> movers)
	 */
	private void setBQButtonStyle(Button b) {
		//set background as white and bold the text, give black border, increase font size
		b.setStyle("-fx-background-color: #ffffff;-fx-font-weight: bold;-fx-font-size: 15;");
		//keep text in button
		b.setWrapText(true);
		//center align text
		b.setTextAlignment(TextAlignment.CENTER);
	}
	
	/**
	 * Splits a CamelCase word into its individual words
	 * <p>
	 * Used in parsing the mover's <code>value</code> attribute (normally used to load images) into the animal name in the
	 * Matching Game.
	 * 
	 * @author Ryan Peters
	 * @param v		inputted CamelCase word
	 * @return		the CamelCase word split into its individual words
	 */
	private String splitWordOnCaps(String v) {
		String[] words = v.split("(?=[A-Z])");
		String word = "";
		for (String w : words) {
			word = word + w + " ";			
		}
		return word.trim();
	}
	
	
	/**
	 * Initializes the button copy that follows the mouse when draggin a clue to the animal/mover on screen. The button
	 * is invisible by default until a drag occurs and has the same formatting as a clue button.
	 * 
	 * @author Ryan Peters 
	 * @see storeClues(ArrayList<Mover> movers)
	 */
	private void makeDraggingButton() {
		draggingButton = new Button();
		draggingButton.setVisible(false);
		draggingButton.setPrefHeight(clueHeight);
		draggingButton.setPrefWidth(clueWidth);
		setMatchingButtonStyle(draggingButton);
		draggingButton.setDisable(true);
		root.getChildren().add(draggingButton);
	}
	
	
	private void storeTutorialMoverAsButton(ArrayList<Mover> movers) {
		System.out.println(movers.get(0));
		AMModel.MatchingAnimal ma1 = (AMModel.MatchingAnimal) movers.get(0);
		
		clueBox = new VBox();
		clueBox.setAlignment(Pos.CENTER_RIGHT);
		clueBox.setTranslateX(backgroundWidth - clueXBuffer);
		clueBox.setTranslateY(clueYBuffer);
		clueBox.setSpacing(clueSpacing);
		
		
		makeDraggingButton();
		
		clueBox.getChildren().addAll(createMatchingAnimalButton(ma1), createHintButton());
		root.getChildren().add(clueBox);
	}
	
	
	private Button createMatchingAnimalButton(AMModel.MatchingAnimal ma) {
		clueBank.put(ma.getValue(), new ClueList(ma.getValue(), ma.getClues()));
		Button b = new Button();
		b.setId(ma.getValue());
		b.setText(clueBank.get(b.getId()).getIterator().next());
		b.setPrefHeight(clueHeight);
		b.setPrefWidth(clueWidth);
		setMatchingButtonStyle(b);
		//show selected button
		b.setOnMouseClicked(e -> {
			selectedButton.setBorder(Border.EMPTY);
			selectedButton = b;
			b.setBorder(new Border(new BorderStroke(Color.BLUE, 
		            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
		});
		b.setOnDragDetected(e -> {me = e;});
		b.setOnMouseDragReleased(e -> {me = (MouseEvent) e;});
		//update draggingButton/copy of selectedButton
		b.setOnMouseDragged(e -> {
			me = e;
			draggingButton.setVisible(true);
			draggingButton.setText(b.getText());
			draggingButton.setLayoutX(me.getSceneX() - draggingButton.getPrefWidth()/2);
			draggingButton.setLayoutY(me.getSceneY() - draggingButton.getPrefHeight()/2);
		});
		return b;
	}
	
	private Button createHintButton() {
		Button b = new Button("HINT");
		b.setPrefHeight(clueHeight);
		b.setPrefWidth(clueWidth);
		b.setId("hint");
		setMatchingButtonStyle(b);
		b.setOnMouseClicked(e -> {
			if (selectedButton != null) {
				selectedButton.setText(clueBank.get(selectedButton.getId()).getIterator().next());
			}
		});
		
		return b;
	}
	
	@Override
	public void setUpTutorial() {
		super.setUpTutorial();
		prompt.setText("Drag the Clue to Match it to the Correct Animal");
		
		prompt = new Label("Drag the Clue to Match it to the Correct Animal");
		prompt.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-font-weight: bold;-fx-font-size: 20;"
				+ "-fx-border-color:black;-fx-border-width:3");
		prompt.setLayoutX(backgroundWidth/2 - promptWidth/2);
		prompt.setLayoutY(promptYBuffer);
		prompt.setPrefSize(promptWidth, promptHeight);
		prompt.setWrapText(true);
		prompt.setTextAlignment(TextAlignment.CENTER);
		prompt.setAlignment(Pos.CENTER);
		
		root.getChildren().add(prompt);
		
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
		
		/**
		 * An iterator that loops back to the beginning of the list when it reaches the end so that it always continues.
		 * 
		 * @author Ryan Peters
		 *
		 */
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


	@Override
	void drawTutorial(int step) {
		// TODO Auto-generated method stub
		
	}


	@Override
	void updateTutorialStep(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}	
}