package pkgMVC;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pkgEnum.GameState;
import pkgMGModel.*;

import java.util.ArrayList;

//beans




import javafx.animation.AnimationTimer;
import pkgMover.Mover;

/**Controller for a MVC Design
 * 
 * @author Ryan Peters
 *
 */
public class Controller extends Application{
	//data fields hold Model and View
	private Model model;
	private View view;    
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		view = new View(theStage);
		model = new Model();
		
		new AnimationTimer() {
			public void handle(long currentNanoTime) {
				
			//	System.out.println(view.getGame());
				
				model.update(view.getGame(), view.getMouseEvent(), view.getDataNodes());
				view.update(model.getMovers(), GameState.INPROGRESS, model.getDataNodes());
				
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		theStage.show();
	}

}