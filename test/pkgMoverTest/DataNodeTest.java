package pkgMoverTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pkgMover.DataNode;

public class DataNodeTest {
	
	@Test
	public void getValue_test() {
		DataNode dn = new DataNode("node");
		assertEquals(dn.getValue(), "node");
	}

}
