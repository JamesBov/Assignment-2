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
	
	public AStarSearch(Graph g){
		this.graph = g;
		this.path = new ArrayList<Town>();
	}
	
	// Implementation of A* Search algorithm
	public ArrayList<Town> findPath(Town start, ArrayList<Job> jobList){
		
		List<Town> closed = new ArrayList<Town>();
		Queue<Town> queue = new PriorityQueue<Town>();
		
		queue.add(start);
		
		Map<Town, Integer> gScore = new HashMap<Town, Integer>();
		gScore.put(start, 0);
		
		
		Map<Town, Integer> fScore = new HashMap<Town, Integer>();
		fScore.put(start, heuristicEst(start, dest));
		start.setF(fScore.get(start));
		
		
		Map<Town, Town> cameFrom = new HashMap<Town, Town>();
		
		while(!queue.isEmpty()){
			Town curr = queue.poll();
			nodesExpanded++;

			if(curr.equals(dest)){
				this.pathCost = gScore.get(curr);
				return reconstructPath(cameFrom, curr);
			}
			
			closed.add(curr);
			
			
			for(Edge edge : curr.getConnections()){
				Town neighbour = edge.getNeighbour(curr);
				
				
				if(closed.contains(neighbour))
					continue;
				
				int toDecideGScore = gScore.get(curr) + edge.getWeight();
				curr.setG(toDecideGScore);
				
				if(!queue.contains(neighbour)){
					queue.add(neighbour);
				}else if(toDecideGScore >= gScore.get(neighbour)){
					//Path is not as good
					continue;
				}
				
				// Now if it IS a better path, update cameFrom map and gScore map
				cameFrom.put(neighbour, curr);
				gScore.put(neighbour, toDecideGScore);
				
				int f = gScore.get(neighbour) + heuristicEst(neighbour, dest);
				fScore.put(neighbour, f);
				neighbour.setF(f);
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
	
	public void setStrategy(Strategy s){
		this.strategy = s;
	}
	
	public ArrayList<Town> getPath(){
		return this.path;
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
