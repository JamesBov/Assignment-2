/**
 * Interface of strategy, works alongside searchContext to hide the back-end implementation
 * from users
 */
public interface Strategy {
	
	/*
	public ArrayList<Edge> findPath(ArrayList<Edge> jobList, Town start);
	public int getNodesExpanded();
	public int getPathCost();
	*/
	
	public int getHeuristicEst(State s);
}
