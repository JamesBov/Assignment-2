import java.util.ArrayList;

public class Town extends Vertex implements Comparable<Vertex>{
	private String name;
	private ArrayList<Town> neighbours;
	
	public Town(String name, int unloadCost){
		super(unloadCost);
		this.name = name;
		this.neighbours = new ArrayList<Town>();
	}
	
	public String getName(){
		return name;
	}
	
	
	public ArrayList<Town> getNeighbours(){
		generateNeighbours();
		return neighbours;
	}
	
	public void generateNeighbours(){
		for(Edge e : getConnections()){
			neighbours.add(e.getNeighbour(this));
		}
	}
	
	public ArrayList<Edge> getConnections(){
		return super.getAllConnections();
	}

	@Override
	public int compareTo(Vertex o) {
		return super.compareTo(o);
	}
}
