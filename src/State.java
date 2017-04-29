import java.util.ArrayList;
import java.util.Comparator;

public class State implements Comparable<State>{
	private int gCost;
	private int fCost;
	private int hCost;
	
	private ArrayList<Edge> stepsTaken;
	
	public State(){
		this.stepsTaken = new ArrayList<Edge>();
	}
	
	@Override
	public int compareTo(State s) {
		return this.fCost - s.fCost;
	}
	
	public Town getStateHead(){
		Edge finalE = stepsTaken.get(stepsTaken.size() - 1);
		Town head = finalE.getTail();
		return head;
	}
	
	public Town getStateTail(){
		Edge startE = stepsTaken.get(0);
		Town tail = startE.getHead();
		return tail;
	}
	

	public void addEdge(Edge e){
		stepsTaken.add(e);
	}
	
	public int getfCost(){
		return fCost;
	}
	
	public void setfCost(int fCost){
		this.fCost = fCost;
	}

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}

	public int gethCost() {
		return hCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}
	
	public ArrayList<Edge> getStepsTaken(){
		return stepsTaken;
	}
	
	public void copyStepsTaken(ArrayList<Edge> steps){
			stepsTaken = new ArrayList<Edge>(steps);
	}
}
