

public class StraightLineHeuristic implements Strategy{

	public StraightLineHeuristic(AStarSearch searchInstance){
		setHeuristic(searchInstance);
	}

	@Override
	public void setHeuristic(AStarSearch searchInstance) {
		searchInstance.setHeuristic(this);
	}
}
