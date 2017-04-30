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
	
	static ArrayList<Edge> jobList = new ArrayList<Edge>();
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
				
		AStarSearch searchInstance = new AStarSearch(graph, jobList);
		ArrayList<Edge> path = searchInstance.findPath(getStartTown());
		
		if(path == null){
			System.out.println("No Solution");
			return;
		}
		System.out.println(searchInstance.getNodesExpanded());
		outputHandler(path);	
	}
	
	public static void outputHandler(ArrayList<Edge> path){
		
		int totalCost = 0;
		ArrayList<String> output = new ArrayList<String>();
		
		for(Edge e : path){
			if(jobList.contains(e)){
				int jobCost = e.getTail().getUnloadCost() + e.getWeight();
				output.add("Job " + e);
				totalCost += jobCost;
			}else{
				output.add("Empty " + e);
				totalCost += e.getWeight();
			}
		}
		
		System.out.println(totalCost);
		
		for(String s : output){
			System.out.println(s);
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
		Edge newEdgeC = new Edge(tailV, headV, travelCost);
		edgeList.add(newEdgeC);
		/*
		for(Job j : jobList){
			if(j.getFrom().equals(headV) && j.getTo().equals(tailV))
				return;
		}
		*/
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
		for(Edge e : edgeList){
			if(e.getHead().equals(headV) && e.getTail().equals(tailV)){
				jobList.add(e);
				break;
			}
		}
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