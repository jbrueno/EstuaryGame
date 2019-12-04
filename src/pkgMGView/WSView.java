package pkgMGView;

import java.math.BigDecimal;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import pkgEnum.GameState;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;

public class WSView extends MinigameView{
	
	// WS_COLLECT
	Image bottle;
	Image background; // used to switch between different backgrounds
	Image background_collect;
	Button btnReturn;
	Button btnFill;
	String btnFillId="Fill";
	final int btnFillX = backgroundWidth*9/10;
	final int btnFillY = backgroundHeight/3;
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
	float pH; // Actual pH of Water
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
	
	public WSView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.WATERSAMPLING);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		
		scene.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler);
		importImages();
		
	} 
	
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
			createScoreLabel(score);
		}
		updateScoreLabel(score);
		setSourceId();
		switch (gs) {
			case WS_COLLECTTUTORIAL:
				
				if(!collectTutorialIsSetUp) {
					background = background_collect;
					drawFillButton();
					addButtons(buttonList);
					setUpTutorial();
					collectTutorialIsSetUp=true;
				}
				
				if(!filledIsPressed && me.getEventType()==MouseEvent.MOUSE_PRESSED &&
						sourceId == btnFillId) {
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
					prompt.setLayoutX(0);
					prompt.setLayoutY(phStripBoxY+200);
					
					phTutorialIsSetUp = true;
				}
				
				if(!boxPressed && me.getEventType()==MouseEvent.MOUSE_PRESSED &&
						sourceId == phStripBoxId) {
					prompt.setText("Now move your mouse to dip the strip in the water.");
					prompt.setLayoutX(backgroundWidth*1/3);
					prompt.setLayoutY(backgroundHeight/8);
					boxPressed=true;
				}
				
				//fix this, maybe water should be a label
				if(boxPressed && me.getX()>= 365 &&
					me.getX() <= 485 &&
					me.getY() >= 425 &&
					me.getY() <= 680) {
						prompt.setText("Great! Use the pH scale above to find the ph."
								+ "\nEnter your guess and press submit!");
						prompt.setLayoutX(backgroundWidth*1/3);
						prompt.setLayoutY(backgroundHeight/3);	
				}
				
				if(!submitPressed && sourceId==btnSubmitId) {
					drawPlayButton();
					submitPressed=true;
				}
				updatepHDisplay();
				break;
			case WS_PH :
				
				if(!phIsSetUp) {
					root.getChildren().remove(btnPlay);
					root.getChildren().remove(tutorialLabel);
					root.getChildren().remove(prompt);
					phIsSetUp=false;
				}
				
				updatepHDisplay();
				break;
			case WS_TEMP :
				background = background_lab;
				break;	
			default:
				break;
		}
		
		draw(movers);
	//	System.out.println(gs);
	}
	
	void setSourceId() {
		if (me.getEventType()==MouseEvent.MOUSE_PRESSED) {
			try {
				sourceId = ((Button) me.getSource()).getId();
			} catch (ClassCastException e) {}
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
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);

		for (Mover m : movers) {
				draw(m);
				
		}
		
	}

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
	
	public void setUpTutorial() {
		super.setUpTutorial();
		prompt.setText("We need to test the pH of the estuary's water!"
				+ "\nPress Fill to fill up your Van Dorn bottle at the right depth!");
	}
	
	public void updateTutorialStep(MouseEvent m) {
		//System.out.println(tutorialStep);
		/*switch(tutorialStep) {
		case 0: if(m.getEventType()==MouseEvent.MOUSE_PRESSED) {
					tutorialStep=1;
				}
				break;
		case 1: if (m.getEventType()==MouseEvent.MOUSE_CLICKED) {
			tutorialStep=2;
				}
			break;
		case 2: if (m.getEventType()==MouseEvent.MOUSE_PRESSED) {
			tutorialStep=3;
		}
		case 3: //TODO FIX MAGIC NUMBERS
				if(m.getX() >= 365 &&
				m.getX() <= 485 &&
				m.getY() >= 425 &&
				m.getY() <= 680) {
				tutorialStep=4;
			}
			break;
		case 4: 
			break;
		}*/
	}
	
	public void drawTutorial(int step) {
/*
		switch(step) {
		case 0: prompt.setText("We need to collect water to test it! \nClick the fill button to fill your Van Dorn bottle!");
				break;
		case 1: if(!btnPlayAdded) {
					drawPlayButton();
					btnPlayAdded=true;
				}
				break;
		case 2: prompt.setText("Click box to get pH testing strip!");
				btnPlayAdded=false;
				break;
		case 3: prompt.setText("Move mouse to dip strip in water!");
				break;
		case 4: prompt.setText("Match pH with scale \nand enter your guess!");
		
				if(!btnPlayAdded) {
					drawPlayButton();
					btnPlayAdded=true;
				}
			}
			*/
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
				btnIncreasepH.setOnMouseClicked(e -> {
					me=e;
				});
				btnIncreasepH.setOnMouseReleased(e -> {
					me=e;
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