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
	
	//private ArrayList<Edge> stepsTaken;
	private State prevState;
	
	/**
	 * State constructor, takes in remainingJobs and stepsTaken to reach this edge
	 */
	public State(State p){
		this.prevState = p;
		this.remainingJobs = new ArrayList<Edge>();
	}
	
	@Override
	public int compareTo(State s) {
		return this.fCost - s.fCost;
	}
	
	public Town getStateHead(){
		Edge finalE = stepsTaken.get(stepsTaken.size() - 1);
		Town head = finalE.getTail();
		return head;
	}
	
	public Town getStateTail(){
		Edge startE = stepsTaken.get(0);
		Town tail = startE.getHead();
		return tail;
	}
	

	public void addEdge(Edge e){
		stepsTaken.add(e);
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
	
	public ArrayList<Edge> getStepsTaken(){
		return stepsTaken;
	}
	
	public ArrayList<Edge> getRemainingJobs(){
		return remainingJobs;
	}
	
	/**
	 * Copies over all steps Taken and generates a new array to hold this data
	 * @param steps
	 */
	public void copyStepsTaken(ArrayList<Edge> steps){
		stepsTaken = new ArrayList<Edge>(steps);
	}
	
	/**
	 * Copies over all remaining jobs and generates a new array to hold this data
	 * @param jobs
	 */
	public void copyJobList(ArrayList<Edge> jobs){
		if(jobs.isEmpty()){
			remainingJobs = new ArrayList<Edge>();
			return;
		}
		remainingJobs = new ArrayList<Edge>(jobs);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fCost;
		result = prime * result + gCost;
		result = prime * result + hCost;
		result = prime * result + ((remainingJobs == null) ? 0 : remainingJobs.hashCode());
		result = prime * result + ((stepsTaken == null) ? 0 : stepsTaken.hashCode());
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
		if (remainingJobs == null) {
			if (other.remainingJobs != null)
				return false;
		} else if (!remainingJobs.equals(other.remainingJobs))
			return false;
		if (stepsTaken == null) {
			if (other.stepsTaken != null)
				return false;
		} else if (!stepsTaken.equals(other.stepsTaken))
			return false;
		return true;
	}
}
