
  package pkgMGModelTest;
  
  import static org.junit.Assert.assertTrue;
  
  import java.lang.reflect.Method;
  
  import org.junit.Before; import org.junit.Test;
  
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
  


  }
