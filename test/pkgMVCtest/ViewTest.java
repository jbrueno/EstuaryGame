package pkgMVCtest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.stage.Stage;
import pkgEnum.Game;
import pkgMVC.View;

public class ViewTest {
	
	/**
	 * 
	 * 
	 * @author HM
	 */
	@Test
	public void getGameType_Test() {
		Stage s = new Stage();
		View v = new View(s);
		
		assertEquals(v.getGameType(), Game.MAINSCREEN);
	}

}
