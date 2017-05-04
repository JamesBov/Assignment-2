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
			
	@Override
	/**
	 * Returns formatted output according to the spec
	 */
	public String toString() {
		return t1.getName() + " to " + t2.getName();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((t1 == null) ? 0 : t1.hashCode());
		result = prime * result + ((t2 == null) ? 0 : t2.hashCode());
		result = prime * result + weight;
		return result;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (t1 == null) {
			if (other.t1 != null)
				return false;
		} else if (!t1.equals(other.t1))
			return false;
		if (t2 == null) {
			if (other.t2 != null)
				return false;
		} else if (!t2.equals(other.t2))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}
}
