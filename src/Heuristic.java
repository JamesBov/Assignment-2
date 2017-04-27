import java.util.ArrayList;
import java.util.Queue;

public class Heuristic implements Strategy{
	private int nodesExpanded = 0;

	@Override
	public void setHeuristic(Town reference, Graph graph, ArrayList<Job> jobList) {
		
		for(Job j : jobList){
			AStarSearch searchInstance = new AStarSearch(graph);
			
			searchInstance.search(reference, j.getFrom());			
			int lowerB = searchInstance.getPathCost();
			nodesExpanded = searchInstance.getNodesExpanded();
			

			searchInstance.search(j.getFrom(), j.getTo());
			int upperB = searchInstance.getPathCost();	
			nodesExpanded = nodesExpanded + searchInstance.getNodesExpanded();
			

			j.setPathCost(upperB);


			int distance = upperB + lowerB;
			j.setGCost(lowerB);
			j.setHeuristicCost(distance);
			j.calcTotalCost();
		}
	}
	
	public int getNodesExpanded(){
		return nodesExpanded;
	}

}
