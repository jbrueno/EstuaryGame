package pkgMover;


/**
 * DataNode is a class that holds a string value. It is used to transfer data from View --> Model and Model --> View
 * <p>
 * Rather than just pass a String, we choose to use the DataNode in order to specify a few types of ways that the String
 * can be constructed. The String <code>value</code> is used to pass data like score (int), hints (String), labels (String), and
 * and leaderboard results (String).
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