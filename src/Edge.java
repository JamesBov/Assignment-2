/**
 * 
 * Edge class that stores the vertex (Towns) associated with the edge and 
 * the cost weight to travel across
 *
 */
public class Edge{
	private Town t1, t2;
	private int weight;
	
	
	/**
	 * Constructor for initialising a new edge
	 * @param t1 - Head vertex
	 * @param t2 - Tail vertex
	 * @param integer weight
	 */
	public Edge(Town t1, Town t2, int weight){
		this.t1 = t1;
		this.t2 = t2;
		this.weight = weight;
	}
	
	/**
	 * @return vertex associated with the "head" of the edge
	 */
	public Town getHead(){
		return t1;
	}

	/**
	 * @return vertex associated with the "tail" of the edge
	 */
	public Town getTail(){
		return t2;
	}
	
	/**
	 * @return integer of the cost travel across this edge
	 */
	public int getWeight(){
		return weight;
	}
		
	/*
	public boolean equals(Edge other){
		if(this.equals(other)){
			return true;
		}else{
			return false;
		}
	}
*/
	
	@Override
	/**
	 * Returns formatted output according to the spec
	 */
	public String toString() {
		return t1.getName() + " to " + t2.getName();
	}
	
	/**
	 * Return hash code for this edge
	 * Takes the name of the head and tail vertices and creates a hash code
	 */
	public int hashCode(){
		return(t1.getName() + t2.getName()).hashCode();
	}
	
	public Town getNeighbour(Town reference){
		Town neighbour;
		
		if(reference.equals(t1)){
			neighbour = t2;
		}else if(reference.equals(t2)){
			neighbour = t1;
		}else{
			neighbour = null;
		}
		return neighbour;
	}
}
