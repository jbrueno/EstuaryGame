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
	public void Terrapin_Move_test() {
		
		Terrapin t = new Terrapin(100, 100, 100, 100);
		t.move(99, 89);
		assertTrue(t.getYIncr() < 0);
		t.move(200, 200);
		assertTrue(t.getYIncr() > 0);
		assertTrue(t.getxIncr() == 0);
		
		
	}
	
	
	
	

}
