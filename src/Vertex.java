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
	
	/**
	 * Returns a list of edges that contain this vertex
	 * @return
	 */
	public ArrayList<Edge> getAllConnections(){
		return connections;
	}

	/**
	 * Return unload cost of when a job finishes at this vertex
	 * @return
	 */
	public int getUnloadCost() {
		return unloadCost;
	}

}
