import java.util.ArrayList;
/**
 * Context interface for hiding the back-end implementation of the search algorithm/Heuristics
 * Client selects which search strategy to use by calling searchContext(s)
 */
public class SearchContext {
	private Strategy strategy;
	private ArrayList<Edge> path;
	private int nodesExpanded;
	
	/**
	 * Constructor that sets the search context to be used with A* search algorithm
	 * @param s
	 */
	public SearchContext(Strategy s){
		this.strategy = s;
	}
	
	/**
	 * Execute strategy by setting the heuristic
	 * @param jobList
	 * @param start
	 */
	public void executeStrategy(ArrayList<Edge> jobList, Town start){
		AStarSearch searchInstance = new AStarSearch(jobList);
		searchInstance.setStrategy(strategy);
		path = searchInstance.findPath(start);
		nodesExpanded = searchInstance.getNodesExpanded();
	}
	
	public ArrayList<Edge> getPath(){
		return path;
	}
	
	public int getExpandedNodes(){
		return nodesExpanded;
	}
}
