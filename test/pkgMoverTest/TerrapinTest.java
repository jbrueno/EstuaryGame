package pkgMoverTest;
import static org.junit.Assert.*;
import org.junit.Test;
import pkgMover.Terrapin;

public class TerrapinTest {
	
	@Test
	public void Terrapin_breathe_hold_air_test() {
		Terrapin test1 = new Terrapin(10, 10, 100, 100);
		
		test1.holdBreath();
		assertTrue(test1.getAirAmount() == 99.5);
		assertFalse(test1.getAirAmount() == 100.0);
		test1.breathe();
		assertTrue(test1.getAirAmount() == 100.0);
		assertFalse(test1.getAirAmount() == 99.5);
		
		
	}
	
	@Test
	public void Terrapin_Move_test_1() {
		
		Terrapin t = new Terrapin(100, 100, 100, 100);
		// Y Branch
		// True True
		t.move(99, 99);
		assertTrue(t.getYIncr() < 0);
		assertTrue(t.getXIncr() == 0);
		t.setY(100);
		// true false
		t.move(99, 99);
		assertTrue(t.getYIncr() < 0);
		assertTrue(t.getXIncr() == 0);
		t.setY(100);
		// false false
		t.move(101, 101);
		assertTrue(t.getYIncr() > 0);
		assertTrue(t.getXIncr() == 0);
		
		
	}
	
	
	@Test
	public void Terrapin_Move_test_2() {
		
		Terrapin t = new Terrapin(100, 100, 0, -100);
		
		t.move(99, 99);
		assertTrue(t.getYIncr() < 0);
		assertTrue(t.getXIncr() == 0);
		t.setY(100);
		
		t.move(99, 99);
		assertTrue(t.getYIncr() < 0);
		assertTrue(t.getXIncr() == 0);
		t.setY(100);
		
		t.move(101, 101);
		assertTrue(t.getYIncr() > 0);
		assertTrue(t.getXIncr() == 0);
		t.setY(100);
		
		t.move(101, 101);
		assertTrue(t.getYIncr() > 0);
		assertTrue(t.getXIncr() == 0);
		
	
	}
	
	@Test
	public void Terrapin_setAir_test() {
		Terrapin t = new Terrapin(100, 100, 100, 100);
		t.setAirAmount(50);
		assertTrue(t.getAirAmount() == 50);
	}
	
	
	
	

}
