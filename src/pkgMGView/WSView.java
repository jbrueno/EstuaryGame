package pkgMGView;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pkgEnum.GameState;
import pkgMGModel.WSModel.pHStrip;
import pkgEnum.Game;
import pkgMover.Mover;

public class WSView extends MinigameView{
	private static final long serialVersionUID = 10L;
	// WS_COLLECT
	Image bottle;
	Image background_collect;
	Button btnReturn;
	Button btnFill;
	String btnFillId="Fill";
	final int btnFillX = 500;
	final int btnFillY = 500;
	
	Button btnLab;
	String btnLabId="Lab";
	boolean btnLabAdded=false;
	final double btnLabX = btnFillX;
	final double btnLabY = btnFillY+50;
	
	boolean collectTutorialIsSetUp=false;
	boolean filledIsPressed=false;
	boolean playIsPressed=false;
	String sourceId="";
	
	boolean collectIsSetUp=false;

	// WS_PH
	double pH; // Actual pH of Water
	Image background_lab;
	Image testTube;
	Image phStrip;
	Color phColor;
	
	boolean phTutorialIsSetUp = false;
	boolean boxPressed = false;
	boolean submitPressed = false;
	boolean phIsSetUp=false;
	
	Button phStripBox;
	String phStripBoxId="phStripBox";
	Image phBox;
	ImageView ivphBox;
	int phStripBoxX=0;
	int phStripBoxY=backgroundHeight/5;
	
	Label pHLabel; // Label "Holding" the labels and buttons for guessing the pH
	int pHLabelX = backgroundWidth/2; // x-Loc
	int pHLabelY = backgroundHeight / 2; // y-Loc
	int pHLabelWidth = 300;
	int pHLabelHeight = 200;
	
	Label pHDisplay;
	int pHDisplayX = pHLabelX + 25;
	int pHDisplayY = pHLabelY + 25;
	int pHDisplayWidth = pHLabelWidth * 3/5; // leave room for buttons
	int pHDisplayHeight = pHLabelHeight - 50; 
	double guesspH = 7; // user's guess for what actual pH is, set to 7 as starting point
	
	Button btnIncreasepH;
	String btnIncreasepHId="0.5";
	String btnDecreasepHId="-0.5";
	int btnIncreasepHX = pHDisplayX + pHDisplayWidth + 10;
	int btnIncreasepHY = pHDisplayY;
	
	Button btnDecreasepH;
	int btnDecreasepHX = btnIncreasepHX;
	int btnDecreasepHY = btnIncreasepHY + 50;
	
	Button btnSubmit;
	String btnSubmitId="Submit";
	int btnSubmitX = btnIncreasepHX;
	int btnSubmitY = btnDecreasepHY + 50;
	private final int MAX_PH_SCORE = 500;
	
	Label displaypH;
	
	boolean click;
	
	// Locations of pop-up prompts
	final double PROMPT_TEST_X = 0;
	final double PROMPT_TEST_Y = phStripBoxY + 200;
	final double PROMPT_MOVE_X = backgroundWidth * 1/3;
	final double PROMPT_MOVE_Y = backgroundHeight / 8;
	final double PROMPT_SUBMIT_X = backgroundWidth * 1/3;
	final double PROMPT_SUBMIT_Y = backgroundHeight / 3;
	
	
	/**
	 * Constructor
	 * sets game to current game (WATERSAMPLING), sets up scene, root, graphics context, and imports needed images
	 * @param gc Graphics Context to be used in this view
	 * @param root Group to be used in this view
	 * @param scene Scene to be used in this view
	 */
	public WSView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.WATERSAMPLING);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		
		scene.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler);
		importImages();
		
	} 
	
	
	/**
	 * Draws labels, buttons, Movers, and background if it hasn't been done already
	 * Updates Movers location accordingly
	 * @param movers ArrayList of Mover objects
	 * @param gs current GameState
	 * @param score users current score in this Minigame so far
	 * @param time current amount of time left to be displayed on timer (not used in this game)
	 */
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
			createScoreLabel(score);
		}
		updateScoreLabel(score);
		setSourceId();
		draw(movers);
		switch (gs) {
			case WS_COLLECTTUTORIAL:
				if(!collectTutorialIsSetUp) {
					background = background_collect;
					drawFillButton();
					addButtons(buttonList);
					setUpTutorial();
					background = background_collect;
					collectTutorialIsSetUp=true;
				}
				
				if(!filledIsPressed && me.getEventType()==MouseEvent.MOUSE_PRESSED &&
						sourceId.equals(btnFillId)) {
					prompt.setText("Good job! Let's collect some more water!");
					drawPlayButton();
					filledIsPressed=true;
				}
				
				
				
				break;
			case WS_COLLECT :
				if(!collectIsSetUp) {
					root.getChildren().remove(tutorialLabel);
					root.getChildren().remove(prompt);
					root.getChildren().remove(btnPlay);
					background = background_collect;
					collectIsSetUp=true;
				}
				
				if(!btnLabAdded && me.getEventType()==MouseEvent.MOUSE_PRESSED &&
						sourceId == btnFillId) {
					root.getChildren().add(btnLab);
					btnLabAdded=true;
				}
				
				break;
			case WS_PHTUTORIAL:
				
				if(!phTutorialIsSetUp) {
					root.getChildren().remove(btnFill);
					root.getChildren().remove(btnLab);
					background = background_lab;
					drawpHBox();
					drawpHLabel();
					drawpHDisplay();
					drawpHButtons();
					setUpTutorial();
					
				//TODO create separate function for set text + loc
					prompt.setText("Now we need to test the water's pH."
							+ "\nClick the box to get a pH strip!");
					prompt.setLayoutX(PROMPT_TEST_X);
					prompt.setLayoutY(PROMPT_TEST_Y);
					
					phTutorialIsSetUp = true;
				}
				
				if(!boxPressed && me.getEventType()==MouseEvent.MOUSE_PRESSED &&
						sourceId == phStripBoxId) {
					prompt.setText("Now move your mouse to dip the strip in the water. "
							+ "\nMatch the pH with the scale above and enter your guess.");
					prompt.setLayoutX(PROMPT_MOVE_X);
					prompt.setLayoutY(PROMPT_MOVE_Y);
					boxPressed=true;
				}
				
				//fix this, maybe water should be a label
				if(boxPressed && me.getEventType()==MouseEvent.MOUSE_PRESSED &&
						sourceId.equals("0.5") || sourceId.equals("-0.5")) {
					
						prompt.setText("Great! Now press submit!");
						prompt.setLayoutX(PROMPT_SUBMIT_X);
						prompt.setLayoutY(PROMPT_SUBMIT_Y);	
				}
				
				if(!submitPressed && sourceId==btnSubmitId) {
					
					drawPlayButton();
					submitPressed=true;
				}
				updatepHDisplay();
				break;
			case WS_PH :
				submitPressed=false;
				if(!phIsSetUp) {
					root.getChildren().remove(btnPlay);
					root.getChildren().remove(tutorialLabel);
					root.getChildren().remove(prompt);
					phIsSetUp=false;
				}
				
				if(!submitPressed && sourceId==btnSubmitId) {
					drawCorrectpH(movers);
					disableButton(btnIncreasepH);
					disableButton(btnDecreasepH);
					disableButton(btnSubmit);
					submitPressed=true;
				}
				
				updatepHDisplay();
				break;
			default:
				break;
		}
		
		draw(movers);
	}
	
	
	/**
	 * 
	 * If user has pressed mouse, Set sourceId to the button's source ID (if user has actually clicked a button)
	 */
	void setSourceId() {
		if (me!=null && me.getEventType()==MouseEvent.MOUSE_PRESSED) {
			try {
				sourceId = ((Button) me.getSource()).getId();
			} catch (ClassCastException e) {}
		}
	}

	
	/**
	 * Creates two Buttons: "Fill" and "To the Lab!"
	 * draws Fill button to screen
	 */
	void drawFillButton() {
		btnFill = new Button("Fill");
		btnFill.setId(btnFillId);
		btnFill.setLayoutX(btnFillX);
		btnFill.setLayoutY(btnFillY);
		btnFill.setOnMousePressed(e -> {
			me=e;
		});
		
		buttonList.add(btnFill);
		
		btnLab = new Button("To the Lab!");
		btnLab.setId(btnLabId);
		btnLab.setLayoutX(btnFillX);
		btnLab.setLayoutY(btnFillY+50);
		btnLab.setOnMousePressed(e -> {
			me=e;
		});
		
	}
	
	/**
	 * Updates the text of prompt to inform user
	 * calls super method to set up view for the tutorial
	 */
	public void setUpTutorial() {
		super.setUpTutorial();
		prompt.setText("We need to test the pH of the estuary's water!"
				+ "\nPress Fill to fill up your Van Dorn bottle at the right depth!");
	}
	
	
	/**
	 * implemented from MiniGameView
	 * unused in this view
	 */
	public void updateTutorialStep(MouseEvent m) {
	}
	
	/**
	 * implemented from MiniGameView
	 * unused in this view
	 */
	public void drawTutorial(int step) {
	}
	
	
	// draws label to screen
	// Label "holds" the pHDisplay label and two buttons for user to guess the pH of water
	public void drawpHLabel() {
		pHLabel = new Label();
		pHLabel.setLayoutX(pHLabelX);
		pHLabel.setLayoutY(pHLabelY);
		pHLabel.setMinWidth(pHLabelWidth);
		pHLabel.setMinHeight(pHLabelHeight);
		
		
		pHLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY ,Insets.EMPTY )));
		root.getChildren().add(pHLabel);
	}
	
	// draws label to screen
	// Label displays the user's guess as to what the pH of the water is
	public void drawpHDisplay() {
		pHDisplay = new Label();
		pHDisplay.setLayoutX(pHDisplayX);
		pHDisplay.setLayoutY(pHDisplayY);
		pHDisplay.setMinWidth(pHDisplayWidth);
		pHDisplay.setMinHeight(pHDisplayHeight);
		pHDisplay.setBackground(new Background(new BackgroundFill(Color.PEACHPUFF, CornerRadii.EMPTY ,Insets.EMPTY )));
		pHDisplay.setFont(new Font("Arial", 108));
		//pHDisplay.setText(""+guesspH); // cannot cast float to String.. temp. solution
		root.getChildren().add(pHDisplay);
	}
	
	//updates the pHDisplay label to display most up to date guess
	public void updatepHDisplay() {
	//	System.out.println("guesspH: " + guesspH);
		pHDisplay.setText(""+guesspH);
	}
	
	// user guessing pH button
	public void drawpHButtons() {
		
				btnIncreasepH = new Button("^");
				btnIncreasepH.setId(btnIncreasepHId);
				btnIncreasepH.setLayoutX(btnIncreasepHX);
				btnIncreasepH.setLayoutY(btnIncreasepHY);
				btnIncreasepH.setOnMousePressed(e -> {
					me=e;
					if(guesspH < 14) {
						guesspH += Double.parseDouble(btnIncreasepHId);
					}
				});
				
				 btnIncreasepH.setOnMouseReleased(e -> {
					me=e;
					System.out.println(me.getEventType());
				});
	
				root.getChildren().add(btnIncreasepH);
				
				btnDecreasepH = new Button("v");
				btnDecreasepH.setId(btnDecreasepHId);
				btnDecreasepH.setLayoutX(btnDecreasepHX);
				btnDecreasepH.setLayoutY(btnDecreasepHY);
				btnDecreasepH.setOnMousePressed(e -> {
					me=e;
					if(guesspH > 0) {
						guesspH += Double.parseDouble(btnDecreasepHId);
					}
				});
				
				
				btnDecreasepH.setOnMouseClicked(e -> {
					me=e;
				});
				
			
				root.getChildren().add(btnDecreasepH);
				
				
				btnSubmit = new Button("Submit");
				btnSubmit.setId(btnSubmitId);
				btnSubmit.setLayoutX(btnSubmitX);
				btnSubmit.setLayoutY(btnSubmitY);
				btnSubmit.setOnMousePressed(e -> {
					me=e;
				});
				root.getChildren().add(btnSubmit);

	}
	
	void drawpHBox() {
		
		phStripBox = new Button("", ivphBox);
		phStripBox.setId(phStripBoxId);
		phStripBox.setStyle("-fx-background-color: transparent;");
		phStripBox.setLayoutX(phStripBoxX);
		phStripBox.setLayoutY(phStripBoxY);
		phStripBox.setOnMousePressed(e -> {
			me=e;
			scene.addEventFilter(MouseEvent.MOUSE_MOVED, eventHandler);
		});
		root.getChildren().add(phStripBox);
	}
	
	void drawCorrectpH(ArrayList<Mover> movers) {
		int drawScore;
		for (Mover m : movers) {
			if(m instanceof pHStrip) {
			pHStrip ma = (pHStrip) m;
			pH=(ma.getpH());
			}
		}
		
		if(pH==guesspH) {
			drawScore= MAX_PH_SCORE;
		} else {
			drawScore = (int) (MAX_PH_SCORE - (((Math.abs(pH - guesspH) * 2) * 50)));
		}
		
		displaypH = new Label("Correct pH: "+ pH +"\nYour guess: " + guesspH);

		displaypH.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-font-weight: bold;-fx-font-size: 20;"
				+ "-fx-border-color:black;-fx-border-width:3");
		displaypH.setLayoutX((backgroundWidth*2/3) - 50);
		displaypH.setLayoutY((backgroundHeight/2)-100);
		displaypH.setWrapText(true);
		displaypH.setTextAlignment(TextAlignment.CENTER);
		displaypH.setAlignment(Pos.CENTER);
		root.getChildren().add(displaypH);
	}


	@Override
	void importImages() {
		phBox=new Image("Mover/phBox.png");
		ivphBox=new ImageView(phBox);
		 ivphBox.setFitHeight(150);
		    ivphBox.setFitWidth(150);
		background_collect = new Image("backgrounds/WaterSample.png");
		bottle = new Image("Mover/Bottle.png");
		background_lab = new Image("backgrounds/lab_background.png");
		testTube = new Image("Mover/testtube.png");
		phStrip = new Image("Mover/pHStrip.png");
	}
}