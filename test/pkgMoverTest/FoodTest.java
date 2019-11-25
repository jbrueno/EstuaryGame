package pkgMoverTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pkgMover.Food;


public class FoodTest {
	
	@Test
	public void changeScore_Test() {
		Food f = new Food(1000, 100, -10);
		int food = f.changeScore(100);
		assertTrue(food == 200);
	}
	
	@Test
	public void changeSpeed_test() {
		Food f = new Food (100, 100, -10);
		f.changeSpeed(-100);
		assertTrue(f.getxIncr() == -100);
	}
	
	@Test
	public void trashConstructor_test() {
		Food f = new Food (0, 0, 0, 0, 0, 0, "Food");
		assertTrue(f instanceof Food);
	}

}
