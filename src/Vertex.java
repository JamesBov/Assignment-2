import java.util.ArrayList;

public class Vertex{
	private ArrayList<Edge> connections;
	private int unloadCost;
	
	public Vertex(int unloadCost){
		this.unloadCost = unloadCost;
		this.connections = new ArrayList<Edge>();
	}
	

	
	/**
	 * Adds an edge adjacent vertices of this graph if and only if not already present
	 */
	public void addConnection(Edge e){
		if(this.connections.contains(e)){
			return;
		}
		this.connections.add(e);
	}
	
	public ArrayList<Edge> getAllConnections(){
		return connections;
	}

	public int getUnloadCost() {
		return unloadCost;
	}

}
