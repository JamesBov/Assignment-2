
public class Edge implements Comparable<Edge> {
	private Town t1, t2;
	private int weight;
	
	
	//Edge tail -> head
	public Edge(Town t1,Town t2, int weight){
		this.t1 = t1;
		this.t2 = t2;
		this.weight = weight;
	}
	
	/**
	 * Gets the neighbouring town if exists
	 * @param reference
	 * @return
	 */
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
	
	public Town getHead(){
		return t1;
	}
	
	public Town getTail(){
		return t2;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public boolean weightGreater(Edge other){
		if(weight - other.getWeight() >= 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean equals(Edge other){
		if(this.equals(other)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String toString() {
		return t1.getName() + " to " + t2.getName();
	}
	
	/**
	 * Return hash code for this edge
	 */
	public int hashCode(){
		return(t1.getName() + t2.getName()).hashCode();
	}


	@Override
	public int compareTo(Edge e) {
		if(this.equals(e)){
			return 0;
		}else{
			return 1;
		}
	}
	
}
