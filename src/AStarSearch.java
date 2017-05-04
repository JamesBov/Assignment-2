import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * The main algorithm used for the path finding for optimal route that satisfies all job requirements
 * Strategy pattern simply sets what heuristic to use by calling setStrategy(Strategy s), and 
 * will accordingly execute an A* search with the respective heuristic
 */
public class AStarSearch{

	private int nodesExpanded = 0;
	private int pathCost = 0;
	private Strategy strategy;
	private ArrayList<Edge> jobList;
	
	private State openS;
	private State closedS;
	
	public AStarSearch(ArrayList<Edge> jobList){
		this.jobList = jobList;
	}
	
	/**
	 * Execute A* Search until we arrive at a goal state that contains all the jobs
	 * New states will be generated for any new connection (edge) and added onto the priority queue
	 * Polling the priority queue returns the associated state with the smallest fCost
	 * @param Starting location passed as a parameter
	 * @return Optimal path
	 */
	public ArrayList<Edge> findPath(Town start){
		
		Queue<State> queue = new PriorityQueue<State>();
		
		
		// Initialize a dummy start state
		State startState = new State();
		startState.addEdge(new Edge(new Town("Dummy", 0), start, 0));
		
		queue.add(startState);
		
		HashMap<State, Integer> closed = new HashMap<State, Integer>();
		//ArrayList<State> closed = new ArrayList<State>();
		
		
		while(!queue.isEmpty()){
			
			State curr = queue.poll();
			
			if(!curr.equals(startState)){
				//closed.put(curr, );
			}

			nodesExpanded++;
			
			/*
			if(nodesExpanded >= 1000){
				return curr.getStepsTaken();
			}
			*/

			
			if(isSubset(jobList, curr)){
				pathCost = curr.getgCost();
				return curr.getStepsTaken();
			}
			
			
			
			if(curr.getRemainingJobs().isEmpty() && !curr.equals(startState)){
				return curr.getStepsTaken();
			}
			
						
			for(Edge edge : curr.getStateHead().getConnections()){	
					
				State newState = new State();
				
				//Handles the dummy intial start state			
				if(curr.equals(startState) && edge.getHead().equals(start)){
					newState.addEdge(edge);
					newState.copyJobList(jobList);
				}else{
					newState.copyJobList(curr.getRemainingJobs());
					newState.copyStepsTaken(curr.getStepsTaken());
					
					if(!newState.getStateHead().equals(edge.getHead())){
						newState = null;
						continue;
					}
					
					newState.addEdge(edge);
				}
				

				//System.out.println(newState.getStepsTaken());

				newState.setgCost(curr.getgCost() + edge.getWeight());
				newState.sethCost(0);
				newState.calcfCost();


				if(jobList.contains(edge)){
					newState.getRemainingJobs().remove(edge);
				}
				
				if(withinClosed(closed, newState)){
				//if(closed.contains(newState)){
				//System.out.println("hi");
					closed.remove(closedS);
					queue.add(newState);
					continue;
				}else if(withinOpen(queue, newState)){
					//System.out.println("hi");
					
					removeFromQueue(openS, queue);
					queue.add(newState);
					continue;
				}else if(!withinOpen(queue, newState)){
					//System.out.println("hi");	
					queue.add(newState);
				}	
			}
		}
		//Fail
		return null;
	}
	
	public void removeFromQueue(State s, Queue<State> queue){
		
		Iterator<State> iter = queue.iterator();
		//System.out.println(queue);
		
		while(iter.hasNext()){
			State current = iter.next();

			if(current.equals(s)){
				iter.remove();
			}
		}
	}
	
	public boolean withinClosed(ArrayList<State> closed, State newState){
		boolean state = false;
		
		for(State s : closed){
			if(s.getStateHead().equals(newState.getStateHead())){
				state = true;
				
				if(newState.getfCost() < s.getfCost()){
					closedS = s;
				}
			}
		}
		return state;
	}
	
	public boolean withinOpen(Queue<State> open, State newState){
			
		boolean state = false;
		for(State s : open){
			if(s.getStateHead().equals(newState.getStateHead())){
				
				state = true;
				
				if(newState.getfCost() < s.getfCost()){	
					openS = s;
				}
			}		
		}
		return state;
	}
	
	
	
	/**
	 * True if successor is better option
	 * false if there is a better state in closed
	 * @param closed
	 * @param successor
	 * @return
	 */
	
	/*
	public boolean isClosedSuccessor(ArrayList<State> closed, Queue<State> open, State successor){
		
		for(State s : closed){
			if(s.getStateHead().equals(successor.getStateHead())){
				if(s.getfCost() > successor.getfCost())
					
					return false;
			}
		}
		return true;
	}
	
	public boolean isOpenSuccessor(Queue<State> open, State successor){		
		for(State s : open){
			if(s.getStateHead().equals(successor.getStateHead())){
				if(s.getfCost() < successor.getfCost())
					return false;
			}
		}
		return true;
	}
	*/
	
	/**
	 * Sets the strategy for the A* search heuristic
	 * @param s
	 */
	public void setStrategy(Strategy s){
		this.strategy = s;
	}
	
	
	/**
	 * Estimates the heuristic cost to reach the goal state by running another search instance but
	 * under a heuristic set to 0 (Dijkstra's search)
	 * More details in StraightLineStrategy class
	 * @param state s
	 * @return heuristic estimate
	 */
	public int heuristicEst(State s){	
		return strategy.getHeuristicEst(s);
	}

	/**
	 * get total number of nodes expanded
	 * @return integer of expanded nodes
	 */
	public int getNodesExpanded() {
		return nodesExpanded;
	}
	
	/**
	 * get total path cost
	 * @return integer of total cost
	 */
	public int getPathCost(){
		return pathCost;
	}
	
	/**
	 * Determines whether the jobList is a subset of state
	 * 
	 * @param jobList - List of edges that have jobs
	 * @param state - State which holds all the previous edges taken
	 * @return
	 */
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
