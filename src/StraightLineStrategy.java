import java.util.ArrayList;
/**
 * 
 * @author James
 *
 */
public class StraightLineStrategy implements Strategy{
		
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
