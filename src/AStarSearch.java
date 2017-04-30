import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStarSearch{

	private Graph graph;
	private int nodesExpanded = 0;
	private int pathCost = 0;
	private ArrayList<Town> path = null;
	private Strategy strategy;
	private ArrayList<Edge> jobList;
	
	public AStarSearch(Graph g, ArrayList<Edge> jobList){
		this.graph = g;
		this.path = new ArrayList<Town>();
		this.jobList = jobList;
	}
	
	
	/**
	 * Take the starting point at Sydney
	 * Execute A* Search until we arrive at a goal state that contains all the jobs
	 * @param start
	 * @return Optimal path
	 */
	public ArrayList<Edge> findPath(Town start){
		
		List<Town> closed = new ArrayList<Town>();
		Queue<State> queue = new PriorityQueue<State>();
		
		//Initialise a dummy start state
		State startState = new State();
		startState.setfCost(0);
		startState.addEdge(new Edge(new Town("Dummy", 0), start, 0));
		
		queue.add(startState);
		
		while(!queue.isEmpty()){
			
			State curr = queue.poll();
			//System.out.println(curr.getStepsTaken());
			nodesExpanded++;

			if(isSubset(jobList, curr)){
				return curr.getStepsTaken();
			}
						
			for(Edge edge : curr.getStateHead().getConnections()){	
					
				State newState = new State();
				
				//Handles the dummy intial start state
				if(curr.equals(startState) && edge.getHead().equals(start)){
					newState.addEdge(edge);
				}else{
					newState.copyStepsTaken(curr.getStepsTaken());
					
					if(newState.getStateHead().equals(edge.getTail())){
						newState = null;
						continue;
					}
					newState.addEdge(edge);
				}
				
				newState.setgCost(curr.getgCost() + edge.getWeight());
				newState.setfCost(newState.getgCost() + 0);

				if(!queue.contains(newState)){
					queue.add(newState);
				}
			}
			
			
		}
		//Fail
		return null;
		
	}
		
	public void setStrategy(Strategy s){
		this.strategy = s;
	}
	
	public ArrayList<Town> getPath(){
		return this.path;
	}
	
	public int heuristicEst(Town start, Town dest){
		//Use dijkstra's
		int cost = 0;

		return cost;
	}

	public int getNodesExpanded() {
		return nodesExpanded;
	}
	
	public int getPathCost(){
		return pathCost;
	}
	
	private boolean isSubset(ArrayList<Edge> jobList, State state){
		boolean status = false;
		for(Edge job : jobList){
			if(state.getStepsTaken().contains(job)){
				status = true;
			}else{
				return false;
			}
		}
		return status;
	}
}
