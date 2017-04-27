import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph {
	private HashMap<String, Vertex> vertices;
	private HashMap<Integer, Edge> edges;
	
	/**
	 * Constructor that accepts an array of vertices
	 * populates the graph with vertices
	 * Handles duplicate Town Names
	 * @param verticesList
	 */
	public Graph(ArrayList<Town> verticesList){
		this.vertices = new HashMap<String, Vertex>();
		this.edges = new HashMap<Integer, Edge>();
		
		for(Town v : verticesList){
			this.vertices.put(v.getName(), v);
		}
	}
	
	/**
	 * Checks if there already exists specified edge connecting the two param vertcies
	 * @param (Vertex 1, Vertex 2)
	 */
	public boolean canAddEdge(Edge newEdge){
		/*
		if(v1.equals(v2)){
			return false;
		}
		
		Edge newEdge = new Edge(v1, v2, weight);
		*/
		if(edges.containsKey(newEdge.hashCode())){
			return false;
			
			/*
		// Edge already has been assigned to a vertex
		}else if(v1.containsConnection(newEdge) || v2.containsConnection(newEdge)){
			return false;
			*/
		}
		
		
		// If edge passes all the above tests then add edge to graph
		addEdge(newEdge);
		return true;
	}
	
	/**
	 * Add this edge to the hashmap
	 * Add this edge to the respective vertex connections array
	 * @param e
	 */
	public void addEdge(Edge e){
		edges.put(e.hashCode(), e);
		e.getHead().addConnection(e);
		e.getTail().addConnection(e);
	}
	
	/**
	 * If edge is in graph
	 * @param e
	 * @return
	 */
	public boolean containsEdge(Edge e){
		return edges.containsKey(e.hashCode());
	}
	
	public boolean containsVertex(Town v){
		return vertices.containsValue(v);
	}
	
	public Vertex getVertex(String name){
		return vertices.get(name);
	}
	
	public boolean canAddVertex(Town v){
		if(v == null){
			return false;
		}
		
		addVertex(v);
		return true;
	}
	
	public void addVertex(Town v){
		vertices.put(v.getName(), v);
	}
	
	public Set<String> getVertexKeys(){
		return vertices.keySet();
	}
	
	public Set<Edge> getEdgeKeys(){
		return new HashSet<Edge>(edges.values());
	}
}
