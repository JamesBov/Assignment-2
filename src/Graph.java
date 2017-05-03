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

		if(edges.containsKey(newEdge.hashCode()))
			return false;
			
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
	 * Check if edge exists in graph
	 * @param e
	 * @return
	 */
	public boolean containsEdge(Edge e){
		return edges.containsKey(e.hashCode());
	}
	
	/**
	 * Check if vertex exists in graph
	 * @param v
	 * @return
	 */
	public boolean containsVertex(Town v){
		return vertices.containsValue(v);
	}
	
	/**
	 * Returns a vertex given a string
	 * @param String of the vertex name
	 * @return
	 */
	public Vertex getVertex(String name){
		return vertices.get(name);
	}
	
	/**
	 * Check if possible to add vertex to graph
	 * @param v
	 * @return
	 */
	public boolean canAddVertex(Town v){
		if(v == null){
			return false;
		}
		
		addVertex(v);
		return true;
	}
	
	/**
	 * Add vertex v to the graph
	 * @param v
	 */
	public void addVertex(Town v){
		vertices.put(v.getName(), v);
	}
	
	/**
	 * Return set of keys for the vertices map
	 * @return
	 */
	public Set<String> getVertexKeys(){
		return vertices.keySet();
	}
	
	/**
	 * Return a set of keys for the edges map
	 * @return
	 */
	public Set<Edge> getEdgeKeys(){
		return new HashSet<Edge>(edges.values());
	}
}
