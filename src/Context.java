
public class Context {
	private Strategy strategy;
	
	public Context(Strategy s){
		this.strategy = s;
	}
	
	public void executeStrategy(Town curr, Job j){
		strategy.calculateHeuristic(curr, j);
	}
}
