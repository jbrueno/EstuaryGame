
  package pkgMGModelTest;
  
  import static org.junit.Assert.assertTrue;
  
  import java.lang.reflect.Method;
  
  import org.junit.Before; import org.junit.Test;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game; import pkgEnum.GameState; import pkgMGModel.SCModel;
import pkgMover.Seaweed;
import pkgMover.Trash;
  
  public class SCModelTest {
  
  int backgroundWidth = 1280; 
  int backgroundHeight = 768; 
  private SCModel SCModel; 
  private Method addNewItem; 
  private Method changeSpeeds; 
  private Method changeCurrentSpeed; 
  private static String ADD_NEW_ITEM = "addNewItem";
  private static String CHANGE_SPEEDS = "changeSpeeds"; 
  private static String CHANGE_CURRENT_SPEED = "changeCurrentSpeed"; 
  private Class<?>[] parameterTypes;


  @Before 
  public void setUp() throws Exception { 
	  SCModel = new SCModel();

	  parameterTypes = new Class[2]; 
	  parameterTypes[0] = pkgMover.SCMover.class;
	  parameterTypes[1] = javafx.scene.input.MouseEvent.class;

	  addNewItem = SCModel.getClass().getDeclaredMethod(ADD_NEW_ITEM, (Class<?>[]) null); 
	  addNewItem.setAccessible(true);

	  changeSpeeds = SCModel.getClass().getDeclaredMethod(CHANGE_SPEEDS, (Class<?>[]) null); 
	  changeSpeeds.setAccessible(true);

	  changeCurrentSpeed = SCModel.getClass().getDeclaredMethod(CHANGE_CURRENT_SPEED, parameterTypes[0]); 
	  changeCurrentSpeed.setAccessible(true);

  }

  @Test 
  public void SCModelConstructor_test() { 
	  assertTrue(SCModel.getGame() == Game.SIDESCROLLER); 
	  assertTrue(SCModel.getGameState() == GameState.SC_TUTORIAL_FOOD); 
	  assertTrue(SCModel.getTime() == 600);
	  assertTrue(SCModel.getItems().size() == 1);

  }

  @Test 
  public void addNewItem_test() { 
	  SCModel.randSeaweedThreshold = 10;
	  SCModel.addNewItem();
	  assertTrue(SCModel.getItems().size() == 2);
	  SCModel.randSeaweedThreshold = -10; SCModel.randFoodThreshold = 10;
	  SCModel.addNewItem(); 
	  assertTrue(SCModel.getItems().size() == 3);
	  SCModel.randFoodThreshold = -10; 
	  SCModel.addNewItem();
	  assertTrue(SCModel.getItems().size() == 4);

  }
  
  @Test
  public void changeSpeeds_test() {
	  SCModel.setCurrentItemSpeed(10);
	  SCModel.changeSpeeds();
	  assertTrue(SCModel.getItems().get(0).getXIncr() == 10);
	  SCModel.addNewItem();
	  SCModel.setCurrentItemSpeed(100);
	  SCModel.changeSpeeds();
	  assertTrue(SCModel.getItems().get(0).getXIncr() == 100);
	  assertTrue(SCModel.getItems().get(1).getXIncr() == 100);
  }
  
  @Test
  public void changeCurrentSpeed_test() {
	  SCModel.changeCurrentSpeed(SCModel.getItems().get(0));
	  assertTrue(SCModel.getCurrentItemSpeed() == -28);
	  SCModel.changeCurrentSpeed(new Trash(100, 100, 100));
	  assertTrue(SCModel.getCurrentItemSpeed() == -18);
	  SCModel.changeCurrentSpeed(new Seaweed(100, 100, 100));
	  assertTrue(SCModel.getCurrentItemSpeed() == -13);
  }
  
  @Test
  public void update_test() {
	  MouseEvent me = new MouseEvent(MouseEvent.MOUSE_PRESSED, 500, 500, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
	  SCModel.update(me);
	  assertTrue(SCModel.getGameState() == GameState.SC_TUTORIAL_FOOD);
	  MouseEvent click = new MouseEvent(MouseEvent.MOUSE_CLICKED, 500, 500, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
	  SCModel.update(click);
	  assertTrue(SCModel.getGameState() == GameState.SC_TUTORIAL_FOOD);
	  
	  SCModel.getItems().get(0).setX(-1);
	  SCModel.getTerry().setY(1000);
	  SCModel.update(me);
	  assertTrue(SCModel.getItems().size() == 1);
	  
	  SCModel.getItems().get(0).setX(200);
	  SCModel.getTerry().setY(backgroundHeight/2);
	  SCModel.update(me);
	  assertTrue(SCModel.getGameState() == GameState.SC_TUTORIAL_TRASH);
	  
	  SCModel.update(me);
	  SCModel.update(click);
	  SCModel.update(me);
	  
	  SCModel.getItems().get(0).setX(200);
	  SCModel.getTerry().setY(backgroundHeight/2);
	  SCModel.update(me);
	  assertTrue(SCModel.getGameState() == GameState.SC_TUTORIAL_TRASH);
	  
	  SCModel.update(me);
	  SCModel.getItems().get(0).setX(-1);
	  SCModel.getTerry().setY(backgroundHeight/2);
	  SCModel.update(me);
	  assertTrue(SCModel.getGameState() == GameState.SC_TUTORIAL_SEAWEED);
	  
	  SCModel.update(me);
	  SCModel.update(click);
	  SCModel.update(me);
	  
	  SCModel.getItems().get(0).setX(-1);
	  SCModel.update(me);
	  assertTrue(SCModel.getGameState() == GameState.SC_TUTORIAL_BREATH);
	  
	  SCModel.update(me);
	  SCModel.update(click);
	  SCModel.update(me);
	  
	  SCModel.getTerry().setY(0);
	  SCModel.update(me);
	  assertTrue(SCModel.getGameState() == GameState.TUTORIAL);
	  
	  SCModel.update(me);
	  SCModel.update(me);
	  SCModel.update(me);
	  SCModel.update(me);
	  SCModel.update(click);
	  assertTrue(SCModel.getGameState() == GameState.INPROGRESS);
	  
	  SCModel.update(me);
	  SCModel.getTerry().setY(backgroundHeight/2);
	  SCModel.update(me);
	  
	  SCModel.getItems().get(0).setX(-1);
	  SCModel.update(me);
	  
	  SCModel.getItems().add(0, new Trash(100,100,0));
	  SCModel.getItems().get(0).setX(200);
	  SCModel.getItems().get(0).setY(backgroundHeight/2);
	  SCModel.getTerry().setY(backgroundHeight/2);
	  SCModel.update(me);
	  
	  SCModel.getItems().add(0, new Seaweed(100,100,0));
	  SCModel.getItems().get(0).setX(200);
	  SCModel.getItems().get(0).setY(backgroundHeight/2);
	  SCModel.getTerry().setY(backgroundHeight/2);
	  SCModel.update(me);
	  
	  SCModel.getTerry().setAirAmount(-1);
	  SCModel.update(me);
	  assertTrue(SCModel.getGameState() == GameState.FINISHED);
	  
	  SCModel.update(me);
	  
  }
  


  }
