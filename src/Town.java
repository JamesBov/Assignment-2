import java.util.ArrayList;

public class Town extends Vertex{
	private String name;
		
	public Town(String name, int unloadCost){
		super(unloadCost);
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<Edge> getConnections(){
		return super.getAllConnections();
	}
}
