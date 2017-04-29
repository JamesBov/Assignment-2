import java.util.ArrayList;

public class Vertex{
	private ArrayList<Edge> connections;
	private int unloadCost;
	private int g;
	private int f; // f(x) = distance(x) + h(x)
	private int h;
	
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

	public void setF(int f) {
		this.f = f;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public void setH(int h){
		this.h = h;
	}
	
	public void calculateF(){
		this.f = g + h;
	}

	public int compareTo(Vertex v) {
		if(this.f < v.f){
			return -1;
		}else if(this.f > v.f){
			return 1;
		}else{
			return 0;
		}
	}


	

}
