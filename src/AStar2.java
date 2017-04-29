import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar2 {

	private Graph graph;
	private int nodesExpanded = 0;
	private int pathCost = 0;
	private ArrayList<Town> path = null;
	private Strategy strategy;
	private ArrayList<Job> goals;
	
	public AStar2(Graph g, ArrayList<Job> jobList){
		this.graph = g;
		this.path = new ArrayList<Town>();
		this.goals = jobList;
	}
		
	public ArrayList<Town> search(Town v1) {
		return findPath(v1);
	}
	
	// Implementation of A* Search algorithm
	public ArrayList<Town> findPath(Town start){
		
		List<Town> closed = new ArrayList<Town>();
		Queue<Town> queue = new PriorityQueue<Town>();
		
		queue.add(start);
		
		Map<Town, Integer> gScore = new HashMap<Town, Integer>();
		gScore.put(start, 0);
		
		
		Map<Town, Integer> fScore = new HashMap<Town, Integer>();
		
		for(Job j : goals){
			mapHeuristics(start, j);
		}
		
		Map<Town, Town> cameFrom = new HashMap<Town, Town>();
		
		
		while(!queue.isEmpty()){
			Town curr = queue.poll();
			nodesExpanded++;

			if(goals.isEmpty()){
				this.pathCost = gScore.get(curr);
				return reconstructPath(cameFrom, curr);
			}
						
			for(Edge edge : curr.getConnections()){
				Town neighbour = edge.getNeighbour(curr);
								
				int toDecideGScore = gScore.get(curr) + edge.getWeight();
				curr.setG(toDecideGScore);
				
				if(toDecideGScore >= gScore.get(neighbour)){
					//Path is not as good
					continue;
				}
				
				// Now if it IS a better path, update cameFrom map and gScore map
				cameFrom.put(neighbour, curr);
				gScore.put(neighbour, toDecideGScore);
				
				Job toRemove = null;
				
				for(Job j : goals){
					mapHeuristics(neighbour, j);
					
					if(curr.equals(j.getFrom()) && neighbour.equals(j.getTo())){
						toRemove = j;
					}
				}
				neighbour.calculateF();
				goals.remove(toRemove);
			}
			
			
		}
		//Fail
		return null;
	}
	
	/**
	 * Reconstruct path by using the cameFrom map
	 * @param cameFrom Map<V,V>
	 * @param current V
	 * @return reconstructed path
	 */
	public ArrayList<Town> reconstructPath(Map<Town, Town> cameFrom, Town current){
		ArrayList<Town> path = new ArrayList<Town>();
		

		Town curr = current;
		path.add(current);
		
		while(cameFrom.containsKey(curr)){
			
			curr = cameFrom.get(curr);
			path.add(curr);
		}
		Collections.reverse(path);
		return path;
	}
	
	public void setHeuristic(Strategy s){
		this.strategy = s;
	}
	
	public ArrayList<Town> getPath(){
		return this.path;
	}
	
	public void mapHeuristics(Town reference, Job j){
		AStarSearch searchInstance = new AStarSearch(graph);
		new Dijkstras(searchInstance);
		
		searchInstance.search(reference, j.getFrom());			
		int lowerB = searchInstance.getPathCost();
		nodesExpanded += searchInstance.getNodesExpanded();
		

		searchInstance.search(j.getFrom(), j.getTo());
		int upperB = searchInstance.getPathCost();	
		nodesExpanded += searchInstance.getNodesExpanded();
		
		j.setPathCost(upperB);

		int distance = upperB + lowerB;
		j.setGCost(lowerB);
		j.setHeuristicCost(distance);
		
		//fScore.put(start, heuristicEst(start, dest));
		reference.setH(distance);
	}
	
	public int heuristicEst(Town start, Town dest){
		//Use dijkstra's
		int cost = 0;

		if(this.strategy instanceof Dijkstras){
			cost = 0;
		}else if(this.strategy instanceof StraightLineHeuristic){
			AStarSearch searchInstance = new AStarSearch(graph);
			new Dijkstras(searchInstance);
			
			searchInstance.search(start, dest);			
			cost = searchInstance.getPathCost();
		}
		return cost;
	}

	public int getNodesExpanded() {
		return nodesExpanded;
	}
	
	public int getPathCost(){
		return pathCost;
	}
}
