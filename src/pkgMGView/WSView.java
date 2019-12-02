package pkgMGView;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
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
	final int btnFillX = backgroundWidth*9/10;
	final int btnFillY = backgroundHeight/3;
	Button btnLab;
	final double btnLabX = btnFillX;
	final double btnLabY = btnFillY+50;
	boolean collectIsSetUp=false;
	
	//WS_PHTUTORIAL
	Label pHTutorialLabel;
	int pHTutorialX = 0;
	int pHTutorialY = backgroundHeight*2/5;
	int tutorialStep = 0;
	
	Button btnPlay;
	final double btnPlayX = backgroundWidth*4/5;
	final double btnPlayY = backgroundHeight*4/5;
	boolean btnPlayAdded=false;
	boolean play=false;

	
	// WS_PH
	float pH; // Actual pH of Water
	Image background_lab;
	Image testTube;
	Image phStrip;
	Color phColor;
	boolean labIsSetUp = false;
	
	Button phStripBox;
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
	float guesspH = 7; // user's guess for what actual pH is, set to 7 as starting point
	
	Button btnIncreasepH;
	int btnIncreasepHX = pHDisplayX + pHDisplayWidth + 10;
	int btnIncreasepHY = pHDisplayY;
	
	Button btnDecreasepH;
	int btnDecreasepHX = btnIncreasepHX;
	int btnDecreasepHY = btnIncreasepHY + 50;
	
	Button btnSubmit;
	int btnSubmitX = btnIncreasepHX;
	int btnSubmitY = btnDecreasepHY + 50;

	
	boolean fxcleard=false;
	
	public WSView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.WATERSAMPLING);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		
		scene.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler);
		importImages();
	//	setUpListeners();
		
	} 
	
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
			createScoreLabel(score);
		}
		updateScoreLabel(score);
		
		//System.out.println("gs: " + gs);
		
		switch (gs) {
			case WS_COLLECT :
				if(!collectIsSetUp) {
					background = background_collect;
					drawFillButton();
					addButtons(buttonList);
					collectIsSetUp=true;
				}
				break;
			case WS_PHTUTORIAL:
				if(!labIsSetUp) {
					root.getChildren().remove(btnFill);
					root.getChildren().remove(btnLab);
					background = background_lab;
					drawpHBox();
					drawpHLabel();
					drawpHDisplay();
					drawpHButtons();
					setUpTutorial();
					labIsSetUp = true;
				}
				updateTutorialStep(me);
				drawTutorial(tutorialStep);
				updatepHDisplay();
				if(play) {
					gs=GameState.WS_PH;
				}
				break;
			case WS_PH :
				System.out.println("play: "+play);
				if(play) {
					System.out.println("removing");
					root.getChildren().remove(btnPlay);
					root.getChildren().remove(pHTutorialLabel);
					//play=false;
				}
				System.out.println("label:" + root.getChildren().contains(pHTutorialLabel));
				System.out.println("button:" + root.getChildren().contains(btnPlay));
				if(!labIsSetUp) {
					root.getChildren().remove(btnFill);
					root.getChildren().remove(btnLab);
					background = background_lab;
					drawpHBox();
					drawpHLabel();
					drawpHDisplay();
					drawpHButtons();
					//addButtons(buttonList);
					labIsSetUp = true;
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
		btnFill = new Button("fill");
		btnFill.setLayoutX(btnFillX);
		btnFill.setLayoutY(btnFillY);
		btnFill.setOnMousePressed(e -> {
			me=e;
		});
		
		btnLab = new Button("to the lab");
		btnLab.setLayoutX(btnLabX);
		btnLab.setLayoutY(btnLabY);
		btnLab.setOnMouseClicked(e -> {
			me=e;
		});
		//adds buttons to list
		buttonList.add(btnFill);
		buttonList.add(btnLab);
		
		//draws buttonlist
		//addButtons(buttonList);
		
	}
	public void setUpTutorial() {
		pHTutorialLabel = new Label();
		pHTutorialLabel.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY ,Insets.EMPTY )));
		pHTutorialLabel.setFont(new Font("Arial", 25));
		root.getChildren().add(pHTutorialLabel);
	}
	
	public void updateTutorialStep(MouseEvent m) {
		if (m.getEventType()==MouseEvent.MOUSE_PRESSED) {
			tutorialStep=1;
		} else if (tutorialStep==1 && 
		//TODO FIX MAGIC NUMBERS
			m.getX() >= 365 &&
			m.getX() <= 485 &&
			m.getY() >= 425 &&
			m.getY() <= 680) {
				tutorialStep=2;
		}
	}
	public void drawTutorial(int step) {

		switch(step) {
		case 0:
			pHTutorialLabel.setText("Click box to get pH testing strip!");
			break;
		case 1:
			pHTutorialX=backgroundWidth/2;
			pHTutorialY=backgroundHeight/10;
			pHTutorialLabel.setText("Move mouse to dip strip in water!");
			break;
		case 2:
			pHTutorialX=backgroundWidth*4/5;
			pHTutorialY=backgroundHeight/2;
			pHTutorialLabel.setText("Match pH with scale \nand enter your guess!");
			
			if(!btnPlayAdded) {
				drawPlayButton();
				btnPlayAdded=true;
			}
			break;
		}
		pHTutorialLabel.setLayoutX(pHTutorialX);
		pHTutorialLabel.setLayoutY(pHTutorialY);
	}
	
	public void drawPlayButton() {
		btnPlay=new Button();
		btnPlay.setText("Let's play!");
		btnPlay.setLayoutX(btnPlayX);
		btnPlay.setLayoutY(btnPlayY);
		btnPlay.setOnMouseClicked(e -> {
			me=e;
			play=true;
		});
		root.getChildren().add(btnPlay);
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
				btnIncreasepH.setLayoutX(btnIncreasepHX);
				btnIncreasepH.setLayoutY(btnIncreasepHY);
				btnIncreasepH.setOnAction(e -> {
					if(guesspH < 14) {
						guesspH++;
					}
				});
				root.getChildren().add(btnIncreasepH);

				
				btnDecreasepH = new Button("v");
				btnDecreasepH.setLayoutX(btnDecreasepHX);
				btnDecreasepH.setLayoutY(btnDecreasepHY);
				btnDecreasepH.setOnAction(e -> {
					if(guesspH > 0) {
						guesspH--;
					}
				});
				root.getChildren().add(btnDecreasepH);
				
				
				btnSubmit = new Button("Submit");
				btnSubmit.setLayoutX(btnSubmitX);
				btnSubmit.setLayoutY(btnSubmitY);
				btnSubmit.setOnAction(e -> {
					
				});
				root.getChildren().add(btnSubmit);

	}
	
	void drawpHBox() {
		
		phStripBox = new Button("", ivphBox);
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