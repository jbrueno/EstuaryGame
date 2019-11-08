package pkgMover;


/**
 * DataNode is the "super-est" class of all movable objects within a MinigameModel. It only has a <code>value</code> that will be 
 * used to either store name of a class or a data String (Leaderboard) that will need to be parsed in MinigameView
 * 
 * @author Ryan Peters
 *
 */
public class DataNode {
	private String value;
	
	
	public DataNode(String v) {
		this.value = v;
	}
	
	public String getValue() {
		return value;
	}
}
 