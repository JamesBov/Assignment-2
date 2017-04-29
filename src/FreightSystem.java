import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FreightSystem {
	
	static ArrayList<Job> jobList = new ArrayList<Job>();
	static ArrayList<Town> townList = new ArrayList<Town>();
	static ArrayList<Edge> edgeList = new ArrayList<Edge>();
	static int nodesExpanded = 0;
	static int cost = 0;
	static Graph graph = null;
	
	public static void main(String[] args) {		
	      Scanner sc = null;
	      ArrayList<String> input = new ArrayList<String>();
	        
	      try{
	          sc = new Scanner(new FileReader(args[0]));
	          
		      while(sc.hasNextLine()){
		    	  input.add(sc.nextLine());
		      }
	      }
	      catch (FileNotFoundException e) {
	    	  System.out.println("Can't find file.");
	      }
	      finally
	      {
	          if (sc != null) sc.close();
	      }
	           
	      // Send to input handler
	      input_handler(input);
	      generateGraph();
	      pathFind();
	}
	
	
	/**
	 * Find optimal path
	 * No optimisation of job order yet
	 */
	public static void pathFind(){
		ArrayList<Job> jobOrder = discoverJobOrder();
		ArrayList<Job> path = constructPath(jobOrder);
		calculateCost(path);
		
		
		
		System.out.println(nodesExpanded + " " + "nodes expanded");
		System.out.println("cost = " + cost);
		
		for(Job j : path){
			System.out.println(j.toString() + " " + j.getFinalCost());
		}
		
	}
	
	
	public static ArrayList<Job> constructPath(ArrayList<Job> jobOrder){

		ArrayList<Job> path = new ArrayList<Job>();
		Town prev = null;
		AStarSearch searchInstance = new AStarSearch(graph);
		
		
		for(Job j : jobOrder){
			if(prev == null){
				prev = j.getTo();
				path.add(j);
				continue;
			}else if(!prev.equals(j.getFrom())){
				Job newJob = new Job(prev, j.getFrom(), false);
				newJob.setPath(searchInstance.search(prev, j.getFrom()));
				newJob.setPathCost(searchInstance.getPathCost());
				path.add(newJob);
				path.add(j);
				prev = j.getTo();
			}else{
				path.add(j);
				prev = j.getTo();
			}
		}
		return path;
	}
	
	public static void calculateCost(ArrayList<Job> path){
		for(Job j : path){
			cost += j.getFinalCost();
		}
	}
	
	public static ArrayList<Job> discoverJobOrder(){
		Queue<Job> jobQueue = new PriorityQueue<Job>();	
		ArrayList<Job> completed = new ArrayList<Job>();
		ArrayList<Job> order = new ArrayList<Job>();

		Town reference = getStartTown();
		Job jobPoll = null;
		
		searchJobOrder(jobQueue, reference);
		
		while(!jobQueue.isEmpty()){
						
			jobPoll = jobQueue.poll();
			
			if(completed.contains(jobPoll)){
				continue;
			}
				
			reference = jobPoll.getTo();			
			searchJobOrder(jobQueue, reference);
			
			completed.add(jobPoll);
			order.add(jobPoll);
		}
		return order;
	}
	
	public static void searchJobOrder(Queue<Job> jobQueue, Town reference){
		
		for(Job j : jobList){
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
		}

		for(Job j : jobList){
			jobQueue.add(j);
		}
	}
	
	
	public static Town getStartTown(){
		for(Town t : townList){
			if(t.getName().equals("Sydney")){
				return t;
			}
		}
		return null;
	}
	
	
	
	public static void showPath(ArrayList<Town> path){
		System.out.println("~~~PATH~~~");
		String output = "";
		
		for(Town t : path){
			if(path.lastIndexOf(t) == path.size() - 1){
				output += t.getName();
				break;
			}
			output += t.getName() + " ---> ";
		}
		
		System.out.println(output);
	}

	/**
	 * Extract/Declaration/Cancellation/Change details via regex
	 * Handles these details in their respective handlers
	 * @param input
	 */
	public static void input_handler(ArrayList<String> input){
		
		// Handles duplicate locations, assuming ordered
		//Depot previousDepot = null;
		
		for(String element : input){
			
			// Extract and generate Vertices unloading cost
			if(element.matches("^Unloading.*")){	
				unloadHandler(element);
			}
			
			// Extract and generate required jobs
			if(element.matches("Job.*")){
				jobHandler(element);
			}
			
			// Extract and generate Weighted edges
			if(element.matches("^Cost.*")){
				costHandler(element);
			}


		}	
	}
	
	
	public static void unloadHandler(String element) {
		String[] parts = element.split(Pattern.quote(" "));
		
		int unloadCost = Integer.parseInt(parts[1]);
		
		Town newTown = new Town(parts[2], unloadCost);
		townList.add(newTown);
	}
	
	public static void costHandler(String element){
		String[] parts = element.split(Pattern.quote(" "));
		
		int travelCost = Integer.parseInt(parts[1]);
		String head = parts[2];
		String tail = parts[3];
		
		Town headV = null;
		Town tailV = null;
		
		for(Town town : townList){
			if(headV != null && tailV != null){
				break;
			}else if(head.equals(town.getName())){
				headV = town;
			}else if(tail.equals(town.getName())){
				tailV = town;
			}
		}
		Edge newEdge = new Edge(headV, tailV, travelCost);
		edgeList.add(newEdge);
		
		for(Job j : jobList){
			if(j.getFrom().equals(headV) && j.getTo().equals(tailV))
				return;
		}
		//Job newJob = new Job(headV, tailV, false);
		//jobList.add(newJob);
	}
	
	public static void jobHandler(String element){
		String[] parts = element.split(Pattern.quote(" "));
		
		String head = parts[1];
		String tail = parts[2];
		
		Town headV = null;
		Town tailV = null;
		
		for(Town town : townList){
			if(headV != null && tailV != null){
				break;
			}else if(head.equals(town.getName())){
				headV = town;
			}else if(tail.equals(town.getName())){
				tailV = town;
			}
		}
		Job newJob = new Job(headV, tailV, true);
		jobList.add(newJob);	
	}
	
	public static void generateGraph(){
		graph = newGraph();
		generateEdges(graph);
	}
	
	public static Graph newGraph(){
		Graph newGraph = new Graph(townList);
		return newGraph;
	}
	
	public static void generateEdges(Graph g){
		for(Edge edge : edgeList){
			g.addEdge(edge);
		}
	}
	
	public static void showTownList(){
		for(Town t : townList){
			System.out.println(t.getName());
		}
	}
	
	public static void showJobList(){
		for(Job j : jobList){
			System.out.println(j.toString());
		}
	}
	
	public static void showMap(){
		System.out.println(graph.getVertexKeys());
		
		for(Edge key : graph.getEdgeKeys()){
			System.out.println(key);
		}
	}
}
