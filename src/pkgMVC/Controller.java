package pkgMVC;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMGModel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

//beans




import javafx.animation.AnimationTimer;
import pkgMover.Mover;

/**Controller for a MVC Design
 * 
 * @author Ryan Peters
 *
 */
public class Controller extends Application implements Serializable{
	private static final long serialVersionUID = 1L;
	//data fields hold Model and View
	private Model model;
	private View view;    
	private Scene scene;
	
	//paths for serializing
	private final String VIEW_PATH = "Data/SavedView.txt";
	private final String MODEL_PATH = "Data/SavedModel2.txt";
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	/**
	 * Overridden start() method for an Application. Creates the necessary View and Model for an MVC design along with
	 * a general event handler for serializing (saving/loading) the game ("S" to Save, "L" to load).
	 * <p>
	 * The tick handler
	 * 
	 * @author Ryan Peters
	 */
	@Override
	public void start(Stage theStage) {
		theStage.setFullScreen(true);
		view = new View(theStage);
		model = new Model();
		scene = view.getScene();
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.S) {
                	saveGame();
                }
                if (event.getCode() == KeyCode.L) {
                	loadSavedGame();
                }
            }
        });
		
		new AnimationTimer() {
			public void handle(long currentNanoTime) { 
				
				model.update(view.getGame(), view.getMouseEvent());
				view.update(model.getMovers(), model.getGameState(), model.getScore(), model.getTime());
				
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		theStage.show();
	}
	
	/**
	 * Serializes the Model <code>model</code> to MODEL_PATH file. 
	 * 
	 * @author Ryan Peters
	 */
	private void saveGame() {
		try {
			File f = new File(MODEL_PATH);
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(model);
			oos.close();
			
			/*
			File f2 = new File(VIEW_PATH);
			FileOutputStream fos2 = new FileOutputStream(f2);
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
			oos2.writeObject(view);
			oos2.close();*/
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Loads the Model <code>model</code> from MODEL_PATH file to the current model.
	 * <p>
	 * Due to late implementation of serialization into the structure of the code, a previous model will only be 
	 * loaded in when you are in the same MinigameModel as the model. This is due to the fast that in our design, View 
	 * was used to mainly handle the GameState and model the Game. Since we are "unable" to serialize the View (JavaFX unserializable
	 * and not enough time to solve that problem), the model obtains the current Game from View even when you load in the saved model, and 
	 * the model immediately switches to that game as the first call in the tick.
	 * 
	 * @author Ryan Peters
	 */
	private void loadSavedGame() {
		try {
			FileInputStream fis = new FileInputStream(new File(MODEL_PATH));
			ObjectInputStream ois = new ObjectInputStream(fis);
			model = (Model) ois.readObject();
			ois.close();
			
			/*
			FileInputStream fis2 = new FileInputStream(new File(VIEW_PATH));
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			this.view = (View) ois2.readObject();
			ois2.close();*/
		} catch (IOException e) {} catch (ClassCastException e) {} catch (ClassNotFoundException e) {}
	}
}