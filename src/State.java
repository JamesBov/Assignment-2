import java.util.ArrayList;

/**
 * Search instance up to a particular edge. 
 * i.e. A state holds all edges(steps) taken to reach this state running
 * on the A* search algorithm. 
 * 
 * Also holds the remaining jobs that have yet to be completed
 * 
 * Priority queue in AStarSearch corresponds to the fCost.
 */
public class State implements Comparable<State>{
	private ArrayList<Edge> remainingJobs;
	private int gCost;
	private int fCost;
	private int hCost;
	
	private Edge head;


	private State prev;
	
	/**
	 * State constructor, takes in remainingJobs and stepsTaken to reach this edge
	 */
	public State(Edge head, State prev){
		this.head = head;
		this.prev = prev;
	}
	
	@Override
	public int compareTo(State s) {
		return this.fCost - s.fCost;
	}
			
	public int getfCost(){
		return fCost;
	}
	
	public void calcfCost(){
		this.fCost = gCost + hCost;
	}

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	public int gethCost() {
		return hCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}
		
	public ArrayList<Edge> getRemainingJobs(){
		return remainingJobs;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fCost;
		result = prime * result + gCost;
		result = prime * result + hCost;
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((prev == null) ? 0 : prev.hashCode());
		result = prime * result + ((remainingJobs == null) ? 0 : remainingJobs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (fCost != other.fCost)
			return false;
		if (gCost != other.gCost)
			return false;
		if (hCost != other.hCost)
			return false;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (prev == null) {
			if (other.prev != null)
				return false;
		} else if (!prev.equals(other.prev))
			return false;
		if (remainingJobs == null) {
			if (other.remainingJobs != null)
				return false;
		} else if (!remainingJobs.equals(other.remainingJobs))
			return false;
		return true;
	}	
}
