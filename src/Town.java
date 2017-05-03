import java.util.ArrayList;

/**
 * Child class of vertex
 * Holds a string of the Town name
 * Useful when there are vertices that are not towns
 */
public class Town extends Vertex{
	private String name;
		
	public Town(String name, int unloadCost){
		super(unloadCost);
		this.name = name;
	}
	
	/**
	 * Gets string name of this town
	 * @return string
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Get all connected edges to this town
	 * @return
	 */
	public ArrayList<Edge> getConnections(){
		return super.getAllConnections();
	}
}
