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
	
	
	// WS_PH
	float pH; // Actual pH of Water
	Image background_lab;
	Image testTube;
	Image phStrip;
	Color phColor;
	boolean labIsSetUp = false;
	// pHScale Image dimensions & location
	Image pHScale;
	int pHScaleX = 300;
	int pHScaleY = 0;
	int pHScaleWidth = backgroundWidth - (pHScaleX * 2);
	int pHScaleHeight = backgroundHeight / 5;

	Label pHLabel; // Label "Holding" the labels and buttons for guessing the pH
	int pHLabelX = pHScaleX + pHScaleWidth; // x-Loc
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
	
	
	
	public WSView(GraphicsContext gc, Group root, Scene scene) {
		super(Game.WATERSAMPLING);
		game = theGame;
		this.root = root;
		this.scene = scene;
		this.gc = gc;
		
		scene.addEventFilter(MouseEvent.ANY, eventHandler);
		importImages();
		setUpListeners();
		
	} 
	
	@Override
	public void update(ArrayList<Mover> movers, GameState gs, int score, int time) {
		if (!areButtonsMade) {
			setUpListeners();
			areButtonsMade = true;
			createScoreLabel(score);
		}
		updateScoreLabel(score);
		
		System.out.println("gs: " + gs);
		
		switch (gs) {
		case WS_COLLECT :
			background = background_collect;
			break;
		case WS_PH :
			if(!labIsSetUp) {
				background = background_lab;
				drawpHLabel();
				drawpHDisplay();
				drawpHButtons();
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
			root.getChildren().remove(pHLabel);
			root.getChildren().remove(pHDisplay);
			root.getChildren().remove(btnIncreasepH);
			root.getChildren().remove(btnDecreasepH);
			root.getChildren().remove(btnSubmit);
			
		});
		root.getChildren().add(btnReturn);
	}

	@Override
	void draw(ArrayList<Mover> movers) {
		gc.clearRect(0, 0, backgroundWidth, backgroundHeight);
		gc.drawImage(background, 0, 0, backgroundWidth, backgroundHeight);

		for (Mover m : movers) {
				draw(m);
		}
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
		System.out.println("guesspH: " + guesspH);
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
	


	@Override
	void importImages() {
		background_collect = new Image("backgrounds/WaterSample.png");
		bottle = new Image("Mover/Bottle.png");
		background_lab = new Image("backgrounds/lab_background.png");
		testTube = new Image("Mover/testtube.png");
		phStrip = new Image("Mover/pHStrip.png");
	}
}