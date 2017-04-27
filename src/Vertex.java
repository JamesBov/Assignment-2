import java.util.ArrayList;

public class Vertex{
	private ArrayList<Edge> connections;
	private int unloadCost;
	
	private int d; // distance from the source
	private int h; // heuristic of destination
	private int f; // f(x) = distance(x) + h(x)
	
	public Vertex(int unloadCost){
		this.setUnloadCost(unloadCost);
		this.connections = new ArrayList<Edge>();
	}
	
	/**
	 * Adds an edge to neighbours of this graph if and only if not already present
	 */
	public void addConnection(Edge e){
		if(this.connections.contains(e)){
			return;
		}
		this.connections.add(e);
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public boolean containsConnection(Edge e){
		return this.connections.contains(e);
	}
	
	public Edge getconnections(int i){
		return this.connections.get(i);
	}
	
	public ArrayList<Edge> getAllConnections(){
		return connections;
	}

	public int getUnloadCost() {
		return unloadCost;
	}

	public void setUnloadCost(int unloadCost) {
		this.unloadCost = unloadCost;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int compareTo(Vertex v) {
		if(this.d < v.d){
			return -1;
		}else if(this.d > v.d){
			return 1;
		}else{
			return 0;
		}
	}


	

}
