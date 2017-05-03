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
/**
 * Implementation of the A* search algorithm via strategy pattern in finding the cheapest path 
 *  in a given graph satisfying a list of paths (Jobs) that must be taken. 
 * Initial location is also given
 * 
 * For details on Heuristic view the heuristic class.
 * 
 * 
 * COMP2911 Assignment 2
 * @author James Zhang
 * @zID: z5062479 
 *
 */
public class FreightSystem {
	
	static ArrayList<Edge> jobList = new ArrayList<Edge>();
	static ArrayList<Town> townList = new ArrayList<Town>();
	static ArrayList<Edge> edgeList = new ArrayList<Edge>();
	
	static boolean hasSolution = true;
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
	      initiateSearch();
	}
	
	
	/* Runtime analysis of StraightLineHeuristic
	 * 
	 * 
	 * 
	 */
	private static void initiateSearch(){
		
		if(hasSolution() == false){
			System.out.println("No Solution");
			return;
		}
		
		SearchContext context = new SearchContext(new StraightLineStrategy());

		context.executeStrategy(jobList, getStartTown());
		ArrayList<Edge> path = context.getPath();
		int nodesExpanded = context.getExpandedNodes();
		
		outputHandler(nodesExpanded, path);	
	}
	
	/**
	 * Receives data and prints accordingly to the spec
	 * @param integer - nodesExpanded
	 * @param ArrayList of edges - path
	 */
	private static void outputHandler(int nodesExpanded, ArrayList<Edge> path){
		
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
		
		
		// Print
		System.out.println(nodesExpanded + " " + "nodes expanded");
		System.out.println("cost = " + totalCost);
		for(String s : output){
			System.out.println(s);
		}
	}
		
	/**
	 * Get the starting location for the search
	 * Spec sets it as "Sydney"
	 * Returns town under "Sydney"
	 * @return
	 */
	private static Town getStartTown(){
		for(Town t : townList){
			if(t.getName().equals("Sydney")){
				return t;
			}
		}
		return null;
	}
	
	
	/**
	 * Extract/Declaration/Cancellation/Change details via regex
	 * Handles these details in their respective handlers
	 * @param input
	 */
	private static void input_handler(ArrayList<String> input){
				
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
	
	/**
	 * Handles input involving unload cost
	 * Initializes towns and sets unload costs
	 * @param String with associated data
	 */
	private static void unloadHandler(String element) {
		String[] parts = element.split(Pattern.quote(" "));
		
		int unloadCost = Integer.parseInt(parts[1]);
		
		Town newTown = new Town(parts[2], unloadCost);
		townList.add(newTown);
	}
	
	/**
	 * Handles input involving edges
	 * Initializes edges and sets the distance costs
	 * @param String with associated data
	 */
	private static void costHandler(String element){
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
		
		//Undirected graph, so generate complement edge
		Edge newEdgeC = new Edge(tailV, headV, travelCost);
		edgeList.add(newEdgeC);
	}
	
	/**
	 * Handles input involving jobs
	 * Treated similarly as edges however with weight cost
	 * Added to jobList afterwards
	 * @param String with associated data
	 */
	private static void jobHandler(String element){
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
				hasSolution = true;
				break;
			}else{
				hasSolution = false;
			}
		}
	}
	

	
	/**
	 * Generate the graph from the input
	 */
	private static void generateGraph(){
		graph = newGraph();
		generateEdges(graph);
	}
	
	/**
	 * Initializes a graph from a list of towns (Vertices)
	 * @return
	 */
	private static Graph newGraph(){
		Graph newGraph = new Graph(townList);
		return newGraph;
	}
	
	/**
	 * Generate all edges from the edgeList
	 * @param g
	 */
	private static void generateEdges(Graph g){
		for(Edge edge : edgeList){
			g.addEdge(edge);
		}
	}
	
	private static ArrayList<Town> visited = new ArrayList<Town>();
	
	/**
	 * Perform a DFS to ensure that there are no disconnected subgraphs
	 * Check otherwise, and ensure no jobs are in the disconnected regions
	 * @param curr
	 */
	private static void DFS(Town curr){
		visited.add(curr);
		
		for(Edge connection : curr.getAllConnections()){
			Town neighbour = connection.getNeighbour(curr);
			
			if(!visited.contains(neighbour)){
				DFS(neighbour);
			}
		}
	}
	
	/**
	 * Checks if graph is connected, no solutions if disconnected with jobs in different subgraphs
	 * @param graph
	 * @return boolean
	 */
	private static boolean hasSolution(){
		boolean flag = true;
		DFS(getStartTown());
		
		if(visited.size() == townList.size()){
			flag = true;
		}else{
			ArrayList<Town> checkList = new ArrayList<Town>(townList);
			
			for(Town town : visited){
				checkList.remove(town);
			}
			
			// unvisited nodes remain in checkList
			// check if any jobs are inside this list
			for(Edge j : jobList){
				if(checkList.contains(j.getHead()) || checkList.contains(j.getTail())){
					flag = false;
					break;
				}
			}
		}
		return flag;	
	}
}