

public class Dijkstras implements Strategy {
	
	public Dijkstras(AStarSearch searchInstance) {
		setHeuristic(searchInstance);
	}

	@Override
	public void setHeuristic(AStarSearch searchInstance) {
		searchInstance.setHeuristic(this);
	}

	
}
