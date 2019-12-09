package pkgMGModelTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMGModel.WSModel;
import pkgMGModel.AMModel.MatchingAnimal;
import pkgMGModel.WSModel.pHStrip;
import pkgMGModel.AMModel;
import pkgMGModel.MinigameModel;
import pkgMover.Mover;

public class WSModelTest {
	private WSModel WSModel;

	
	private Method addObjects;
	private Method fillBottle;
	private Method setPH;
	private Method calculateCollectSore;
	private Method dipStrip;
	private Method changeColor;
	private Method calculatePHScore;
	
	private static String ADD_OBJECTS = "addObjects";
	private static String SET_PH = "setPH";
	private static String FILL_BOTTLE = "fillBottle";
	
	private Class<?>[] parameterTypes;
	Class<? extends MinigameModel> c;
	
	
	@Before
	public void setUp() throws Exception {
		WSModel = new WSModel();
		
		c = (Class<? extends MinigameModel>) WSModel.getClass().getSuperclass();
		
		

		
		parameterTypes = new Class[2];
		parameterTypes[0] = pkgMover.Mover.class;
		parameterTypes[1] = javafx.scene.input.MouseEvent.class;
		
		fillBottle = WSModel.getClass().getDeclaredMethod(FILL_BOTTLE, (Class<?>[]) null); 
		fillBottle.setAccessible(true);
		
		setPH= WSModel.getClass().getDeclaredMethod(SET_PH, (Class<?>[]) null); 
		setPH.setAccessible(true);
	}
	
	@Test
	public void WSModelConstructor_test() {
		assertTrue(WSModel.getGame() == Game.WATERSAMPLING);
		assertTrue(WSModel.getGameState() == GameState.WS_COLLECTTUTORIAL);
		assertTrue(WSModel.getMovers().size() == 1);
	}
	
	@Test
	public void addObjects_test() {
		WSModel.addObjects(GameState.WS_COLLECT);
		assertTrue(WSModel.getMovers().size() == 1);
		
		WSModel.addObjects(GameState.WS_PHTUTORIAL);
		assertTrue(WSModel.getMovers().size() == 2);
		
		WSModel.addObjects(GameState.WS_PH);
		assertTrue(WSModel.getMovers().size() == 2);
		
		WSModel.addObjects(GameState.START); // default case, nothing new added
		assertTrue(WSModel.getMovers().size() == 1);
		
	
	}
	
	@Test
	public void fillBottle_test() {
		assertTrue(WSModel.getMovers().get(0).getValue().equals("Bottle"));
		WSModel.fillBottle();
		assertTrue(WSModel.getMovers().get(0).getValue().equals("fullBottle"));
	}
	
	@Test
	public void setPH_test() {
		System.out.println(WSModel.getMovers());
		WSModel.setPH();
		assertTrue(WSModel.getPH() >= 5);
		assertTrue(WSModel.getPH() <= 9);
	}
	
	@Test
	public void update_test() throws Exception{
		Field f = c.getDeclaredField("gs");
		f.setAccessible(true);
		f.set(WSModel, GameState.START);
		
		 WSModel.getMovers().get(0).setY(750);
		 MouseEvent me = new MouseEvent(MouseEvent.MOUSE_PRESSED, 500, 500, 0, 0, null, 0, false, false, false, false,
					true, false, false, false, false, false, null);
		 WSModel.update(me);
		 WSModel.update(me);
	System.out.println(	 WSModel.getMovers().get(0).getValue());
	System.out.println(	 WSModel.getMovers().get(0).getX());
	System.out.println(	 WSModel.getMovers().get(0).getY());
	System.out.println(WSModel.getGameState());
//	assertTrue(WSModel.getGameState() == GameState.WS_COLLECT);
	}
}
