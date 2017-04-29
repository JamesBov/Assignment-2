

public interface Strategy {
	//public void search();

	//public void setHeuristic(Town reference, Graph graph, ArrayList<Job> jobList);
	public void setHeuristic(AStarSearch searchInstance);
}
