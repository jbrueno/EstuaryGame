package pkgMGModelTest;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMGModel.AMModel;
import pkgMGModel.AMModel.MatchingAnimal;
import pkgMover.Mover;


public class AMModelTest {

	int backgroundWidth = 1280;
	int backgroundHeight = 768;

	private AMModel AMModel;
	private MatchingAnimal ma;
	Class<? extends AMModel> c;
	
	private Method reduceUntil;
	private Method inProgressUpdate;
	private Method bonusUpdate;
	private Method tutorialUpdate;
	private Method transition1Update;
	private Method createAnimals;
	
	private static String REDUCE_UNTIL = "reduceUntil";
	private static String IN_PROGRESS_UPDATE = "inProgressUpdate";
	private static String BONUS_UPDATE = "bonusUpdate";
	private static String CREATE_ANIMALS = "createAnimals";
	private static String TRANSITION1_UPDATE = "transition1Update";
	private static String TUTORIAL_UPDATE = "tutorialUpdate";
	private Class<?>[] parameterTypes;
	

	
	@Before
	public void setUp() throws Exception {
		AMModel = new AMModel();
		c = AMModel.getClass();
		ma = AMModel.new MatchingAnimal(100, 100, 5, 5, "generic", new String[]{"Clue 1", "Clue 2", "Clue 3"});

		parameterTypes = new Class[4];
		parameterTypes[0] = javafx.scene.input.MouseEvent.class;
		parameterTypes[1] = java.util.ArrayList.class;
		parameterTypes[2] = int.class;

		reduceUntil = AMModel.getClass().getDeclaredMethod(REDUCE_UNTIL, parameterTypes[1], parameterTypes[2]);
		reduceUntil.setAccessible(true);

		inProgressUpdate = AMModel.getClass().getDeclaredMethod(IN_PROGRESS_UPDATE, parameterTypes[0]);
		inProgressUpdate.setAccessible(true);

		bonusUpdate = AMModel.getClass().getDeclaredMethod(BONUS_UPDATE, parameterTypes[0]);
		bonusUpdate.setAccessible(true);

		tutorialUpdate = AMModel.getClass().getDeclaredMethod(TUTORIAL_UPDATE, parameterTypes[0]);
		tutorialUpdate.setAccessible(true);
		
		transition1Update = AMModel.getClass().getDeclaredMethod(TRANSITION1_UPDATE, parameterTypes[0]);
		transition1Update.setAccessible(true);
		
		createAnimals = AMModel.getClass().getDeclaredMethod(CREATE_ANIMALS, (Class<?>[]) null);
		createAnimals.setAccessible(true);
	}
	
	@Test
	public void AMModelConstructor_test() {
		assertTrue(AMModel.getGame() == Game.ANIMALMATCHING);
		assertTrue(AMModel.getGameState() == GameState.TUTORIAL);
	}
	
	@Test
	public void MAConstructor_test() {
		assertTrue(ma.getX() == 100);
		assertTrue(ma.getY() == 100);
		assertTrue(ma.getXIncr() == 0);
		assertTrue(ma.getYIncr() == 0);
		assertTrue(ma.getImageWidth() == 5);
		assertTrue(ma.getImageHeight() == 5);
		String[] ss = {"Clue 1", "Clue 2", "Clue 3"};
		for (int i = 0; i < ss.length; i++) {
			assertTrue(ss[i] == ma.getClues()[i]);
		}
	}
	
	@Test 
	public void MAmatch_test() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field f = c.getDeclaredField("btnSourceID");
		f.setAccessible(true);
		f.set(AMModel, new String("generic"));
		
		assertTrue(ma.isMatch());
		assertTrue(ma.getIsMatched());
		
		f.set(AMModel,  new String("not correct"));
		assertFalse(ma.isMatch());
		assertFalse(ma.getIsMatched());
	}
	
	@Test
	public void MAunMatch_test() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ma.unMatch();
		assertFalse(ma.getIsMatched());
		
		Field f = c.getDeclaredField("btnSourceID");
		f.setAccessible(true);
		f.set(AMModel, new String("generic"));
		
		ma.isMatch();
		ma.unMatch();
		assertFalse(ma.getIsMatched());
	}
	
	@Test
	public void reduceUntil_test() throws Exception{
		ArrayList<Mover> mvrs = new ArrayList<Mover>();
		mvrs = (ArrayList<Mover>) reduceUntil.invoke(AMModel, mvrs, 0);
		assertEquals(mvrs.size(), 0);
		
		mvrs.add(AMModel.new MatchingAnimal(1,1,0,0,"a",new String[] {"a"}));
		mvrs.add(AMModel.new MatchingAnimal(1,1,0,0,"b",new String[] {"b"}));
		mvrs.add(AMModel.new MatchingAnimal(1,1,0,0,"c",new String[] {"c"}));
		mvrs.add(AMModel.new MatchingAnimal(1,1,0,0,"d",new String[] {"d"}));
		ArrayList<Mover> mvrs2 = (ArrayList<Mover>) reduceUntil.invoke(AMModel, mvrs, 2);
		assertEquals(mvrs2.size(), 2);
	}
	
	@Test
	public void createAnimals_test() throws Exception{
		Field f = c.getDeclaredField("animals");
		f.setAccessible(true);
		f.set(AMModel, new ArrayList<MatchingAnimal>());
		
		createAnimals.invoke(AMModel, (Object[]) null);
		ArrayList<MatchingAnimal> anmls = (ArrayList<MatchingAnimal>) f.get(AMModel);
		assertEquals(9, anmls.size());
		assertTrue(((MatchingAnimal)anmls.get(0)).getIsMatched() == false);
	}
	
	@Test 
	public void inProgressUpdate_test() throws Exception {
		/*
		Button b = new Button();
		b.setId("source");
		MouseEvent me = new MouseEvent(MouseEvent.MOUSE_DRAGGED, 500, 500, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
		Field f = c.getDeclaredField("btnSourceID");
		f.setAccessible(true);
		
		inProgressUpdate.invoke(AMModel, me);
		String sourceID = (String) f.get(AMModel);
		assertTrue((sourceID.equals("source")));
		
		Button b2 = new Button();
		b2.setId("not set");
		MouseEvent me2 = new MouseEvent(b2, null, MouseEvent.MOUSE_CLICKED, 500, 500, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
		inProgressUpdate.invoke(AMModel,  me2);
		sourceID = (String) f.get(AMModel);
		assertFalse(sourceID.equals("not set"));
		
		
		Field f2 = c.getDeclaredField("movers");
		f.setAccessible(true);
		f2.set(AMModel, (new ArrayList<Mover>()).add(ma));
		b.setId("generic");
		MouseEvent me3 = new MouseEvent(b, null, MouseEvent.MOUSE_ENTERED_TARGET, 500, 500, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
		inProgressUpdate.invoke(AMModel, me3);
		assertTrue(((MatchingAnimal) AMModel.getMovers().get(0)).getIsMatched());
		
		f2.set(AMModel, (new ArrayList<Mover>()).add(ma));
		MouseEvent me4 = new MouseEvent(b, null, MouseEvent.MOUSE_ENTERED_TARGET, 400, 400, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
		inProgressUpdate.invoke(AMModel, me4);
		assertFalse(((MatchingAnimal) AMModel.getMovers().get(0)).getIsMatched());
		*/
	}

}


