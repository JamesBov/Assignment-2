import java.util.ArrayList;

public class Job implements Comparable<Job>{
	private boolean job;
	private Town from;
	private Town to;
	private int gCost;
	private int heuristicCost;
	private int totalCost;
	private int pathCost;
	private ArrayList<Town> path = null;
	
	public Job(Town from, Town to, boolean job){
		this.from = from;
		this.to = to;
		this.job = job;
	}

	public Town getFrom() {
		return from;
	}


	public Town getTo() {
		return to;
	}


	@Override
	public String toString() {
		if(job == true){
			return "Job = " + from.getName() + " ---> " + to.getName();
		}else{
			return "(EMPTY) Job = " + from.getName() + " ---> " + to.getName();
		}
		
	}

	public boolean getJob() {
		return job;
	}

	@Override
	public int compareTo(Job j) {		
		if(this.getTotalCost() < j.getTotalCost()){
			return -1;
		}else if(this.getTotalCost() > j.getTotalCost()){
			return 1;
		}else{
			return 0;
		}
	}

	public int getGCost() {
		return gCost;
	}
	

	public void setGCost(int cost) {
		this.gCost = cost;
	}
	
	public void calcTotalCost(){
		this.totalCost = gCost + heuristicCost;
	}
	
	public int getTotalCost(){
		return totalCost;
	}
	

	public ArrayList<Town> getPath() {
		return path;
	}

	public void setPath(ArrayList<Town> path) {
		this.path = path;
	}

	public int getHeuristicCost() {
		return heuristicCost;
	}

	public void setHeuristicCost(int heuristicCost) {
		this.heuristicCost = heuristicCost;
	}

	public int getFinalCost() {
		return pathCost + to.getUnloadCost();
	}


	public int getPathCost() {
		return pathCost;
	}

	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}
	
	
	

}
