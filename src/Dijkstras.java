import java.util.ArrayList;

public class Dijkstras implements Strategy{
	/*
	private int nodesExpanded;
	private ArrayList<Edge> path;
	private int pathCost;
	
	@Override
	public ArrayList<Edge> findPath(ArrayList<Edge> jobList, Town start) {
		AStarSearch searchInstance = new AStarSearch(jobList);
		searchInstance.setStrategy(this);
		
		path = searchInstance.findPath(start);
		pathCost = searchInstance.getPathCost();
		nodesExpanded = searchInstance.getNodesExpanded();
		return path;
	}
	
	public int getNodesExpanded(){
		return nodesExpanded;
	}
	
	public int getPathCost(){
		return pathCost;
	}
	*/

	@Override
	public int getHeuristicEst(State s) {
		return 0;
	}
}
