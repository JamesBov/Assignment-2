import java.util.ArrayList;
/**
 * Main Heuristic used
 * 
 * Analysis:
 * Initializes a new search instance using Dijkstra as a strategy for the A* search.
 * Given a job list of remaining jobs for this particular state that is passed as a parameter
 * Calculates a heuristic cost associated to each remaining job. 
 * Admissible as it cannot overestimate: h*(n) <= h(n)
 **/
public class StraightLineStrategy implements Strategy{
		
	
	/**
	 * Uses Dijkstra's search to find the cost of the nearest job to edge associated with the state
	 * passed as a parameter
	 * @Param search state 
	 */
	public int getHeuristicEst(State s){
		int heuristicCost = 0;
		ArrayList<Edge> heuristicList = new ArrayList<Edge>();
					
		for(Edge j : s.getRemainingJobs()){
			heuristicList.add(j);
						
			AStarSearch searchInstance = new AStarSearch(heuristicList);
			searchInstance.setStrategy(new Dijkstras());
			searchInstance.findPath(s.getStateHead());
			heuristicCost = searchInstance.getPathCost();
			
			heuristicList.remove(j);
		}
		return heuristicCost;
	}
}
