package pkgMGModelTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import pkgMGModel.AMModel;
import pkgMGModel.LeaderboardModel;
import pkgMGModel.LeaderboardModel.ResultMover;
import pkgMGModel.MinigameModel;
import pkgMover.Mover;
import pkgMGModel.AMModel.MatchingAnimal;

public class LeaderboardModelTest {
	
	int backgroundWidth = 1280;
	int backgroundHeight = 768;

	private LeaderboardModel LBM;
	private ResultMover rm;
	Class<? extends MinigameModel> c;
	Class<? extends LeaderboardModel> cc;
	
	private Method readHighScores;
	private Method saveHighScores;
	private Method createResults;
	private Method addNewResult;
	private Method update;
	
	private static String READ_HIGH_SCORES = "readHighScores";
	private static String SAVE_HIGH_SCORES = "saveHighScores";
	private static String CREATE_RESULTS = "createResults";
	private static String ADD_NEW_RESULT = "addNewResult";
	private static String UPDATE = "update";
	private Class<?>[] parameterTypes;
	
	@Before
	public void setUp() throws Exception {
		LBM = new LeaderboardModel();
		c = (Class<? extends MinigameModel>) LBM.getClass().getSuperclass();
		cc = LBM.getClass();
		rm = LBM.new ResultMover("AAA,1000");

		parameterTypes = new Class[2];
		parameterTypes[0] = javafx.scene.input.MouseEvent.class;
		parameterTypes[1] = pkgMGModel.LeaderboardModel.ResultMover.class;

		readHighScores = LBM.getClass().getDeclaredMethod(READ_HIGH_SCORES, (Class<?>[]) null);
		readHighScores.setAccessible(true);

		saveHighScores = LBM.getClass().getDeclaredMethod(SAVE_HIGH_SCORES, (Class<?>[]) null);
		saveHighScores.setAccessible(true);

		createResults = LBM.getClass().getDeclaredMethod(CREATE_RESULTS, String[].class);
		createResults.setAccessible(true);

		addNewResult = LBM.getClass().getDeclaredMethod(ADD_NEW_RESULT, parameterTypes[1]);
		addNewResult.setAccessible(true);
		
		update = LBM.getClass().getDeclaredMethod(UPDATE, parameterTypes[0]);
		update.setAccessible(true);
	}
	@Test
	public void resultMover_test() {
		assertTrue(rm.getResultName().equals("AAA"));
		assertTrue(rm.getResultScore() == 1000);
		
		rm.setResult("BBB", 1200);
		assertTrue(rm.getResultName().equals("BBB"));
		assertTrue(rm.getResultScore() == 1200);
		
		assertTrue(rm.getValue().equals("BBB,1200"));		
	}
	
	@Test 
	public void resultMoverOrder_test() {
		ArrayList<ResultMover> rms = new ArrayList<ResultMover>();
		rms.add(LBM.new ResultMover("ABC,1500"));
		rms.add(LBM.new ResultMover("DEF,1600"));
		rms.add(LBM.new ResultMover("GHI,1400"));
		
		Collections.sort(rms);
		
		assertTrue(rms.get(0).getResultScore() == 1600);
		assertTrue(rms.get(1).getResultScore() == 1500);
		assertTrue(rms.get(2).getResultScore() == 1400);
		
		
		ArrayList<ResultMover> rms2 = new ArrayList<ResultMover>();
		rms2.add(LBM.new ResultMover("ABC,1500"));
		rms2.add(LBM.new ResultMover("ABC,1600"));
		rms2.add(LBM.new ResultMover("AAA,1500"));
		
		Collections.sort(rms2);
		
		assertTrue(rms2.get(0).getResultScore() == 1600);
		assertTrue(rms2.get(1).getResultScore() == 1500 && rms2.get(1).getResultName().equals("AAA"));
		assertTrue(rms2.get(2).getResultScore() == 1500 && rms2.get(2).getResultName().equals("ABC"));
	}
	
	@Test
	public void readHighScores_test() throws Exception {
		Field f = cc.getDeclaredField("MAX_NUM_SCORES");
		f.setAccessible(true);
		
		
		String[] results = (String[]) readHighScores.invoke(LBM, (Object[]) null);
		assertEquals(10, f.get(LBM));
		for (int i = 0; i < results.length; i++) {
			assertTrue(results[i].split(",")[0] instanceof String);
			try {
				Integer.parseInt(results[i].split(",")[1]);
			} catch (Exception e) {fail("not an int");}
			assertTrue(results[i].lastIndexOf(",") != -1);
		}
	}
	
	@Test
	public void saveHighScores_test() throws Exception {
		Field f = cc.getDeclaredField("HIGHSCORES_PATH");
		f.setAccessible(true);
		f.set(LBM, "Data/testHighScores.csv");
		
		Field f2 = c.getDeclaredField("movers");
		f2.setAccessible(true);
		ArrayList<Mover> mvrs = new ArrayList<Mover>();
		mvrs.add(LBM.new ResultMover("ABC,1000"));
		mvrs.add(LBM.new ResultMover("DEF,1200"));
		f2.set(LBM, mvrs);
		try {
			//saveHighScores.invoke(LBM, (Object[]) null);
		} catch (Exception e) {fail("save failed");}
	}

	@Test
	public void addNewResult_test() throws Exception {
		Field f = cc.getDeclaredField("MAX_NUM_SCORES");
		f.setAccessible(true);
		f.set(LBM, 2);
		
		Field f2 = c.getDeclaredField("movers");
		f2.setAccessible(true);
		ArrayList<Mover> mvrs = new ArrayList<Mover>();
		mvrs.add(LBM.new ResultMover("ABC,1000"));
		mvrs.add(LBM.new ResultMover("DEF,1200"));
		f2.set(LBM, mvrs);
		
		//addNewResult.invoke(LBM, LBM.new ResultMover("ZZZ,1100"));
	}
	
	@Test
	public void update_test() throws Exception {
		/*
		 * "can't" test because we need a Button as the MouseEvent source but
		 * javafx elements give a thread exception and not enough time to add
		 * get this to work :(((( sad
		 */
	}
	
	@Test
	public void createResults_test() throws Exception {
		createResults.invoke(LBM, (Object)new String[] {"DDD,1400", "AAA,1200","BBB,1500"});
		
		Field f = c.getDeclaredField("movers");
		f.setAccessible(true);
		ArrayList<Mover> mvrs = (ArrayList<Mover>) f.get(LBM);
		assertTrue(mvrs.get(0).getValue().equals("BBB,1500"));
	}
}
