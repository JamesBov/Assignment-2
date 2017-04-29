

public class Dijkstras implements Strategy {
	
	private AStarSearch searchInstance;
	
	public Dijkstras(AStarSearch searchInstance) {
		this.searchInstance = searchInstance;
	}

	@Override
	public void calculateHeuristic(Town curr, Job j) {
		// TODO Auto-generated method stub
		
	}
	
}
