import java.util.ArrayList;
import java.util.Queue;

public interface Strategy {
	//public void search();

	public void setHeuristic(Town reference, Graph graph, ArrayList<Job> jobList);
	public int getNodesExpanded();
}
