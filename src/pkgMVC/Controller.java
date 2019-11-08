package pkgMVC;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pkgEnum.GameState;
import java.util.ArrayList;

//beans




import javafx.animation.AnimationTimer;

import pkgMG.*;
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
<<<<<<< HEAD
=======
			//	System.out.println(view.getGame());
 
>>>>>>> branch 'Ryan' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
				model.update(view.getGame(), view.getMouseEvent());
				view.update(model.getMovers(), GameState.INPROGRESS);
				
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
