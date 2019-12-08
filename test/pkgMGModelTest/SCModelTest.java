package pkgMGModelTest;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMGModel.SCModel;

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
	}
	
	@Test
	public void addNewMover_test() {
		SCModel.randSeaweedThreshold = 10;
		SCModel.addNewItem();
		assertTrue(SCModel.getItems().size() == 2);
		SCModel.randSeaweedThreshold = -10;
		SCModel.randFoodThreshold = 10;
		SCModel.addNewItem();
		assertTrue(SCModel.getItems().size() == 3);
		SCModel.randFoodThreshold = -10;
		SCModel.addNewItem();
		assertTrue(SCModel.getItems().size() == 4);

	}

}
