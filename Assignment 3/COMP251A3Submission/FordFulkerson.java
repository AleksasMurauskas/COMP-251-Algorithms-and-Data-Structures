import java.io.*;
import java.util.*;

/* Author: Aleksas Murauskas 260718389
 * Collaborators: Nathan Brezner, Jacob McConnell
 * 
 * 
 */


public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		ArrayList<Integer> visited = new ArrayList<Integer>();
		ArrayList<Integer> path_so_far= new ArrayList<Integer>();
		Stack = pathHelper(source, destination,graph, visited,path_so_far);
		return Stack;
	} 
	static Boolean checkEdge(Edge e,ArrayList<Edge> edges) {
		for (Edge active_edge : edges) {
			if (active_edge.nodes[0]==e.nodes[0]&&active_edge.nodes[1]==e.nodes[1]) {
				return true;
			}
		}
		return false;
	}
	public static ArrayList<Integer> pathHelper(Integer current, Integer destination, WGraph graph, ArrayList<Integer> visitedIN, ArrayList<Integer> path_so_farIN){
		ArrayList<Integer>  path_so_far = new ArrayList<Integer>(path_so_farIN);
		ArrayList<Integer>  visited = new ArrayList<Integer>(visitedIN);
		Integer next;
		ArrayList<Integer>  temp = new ArrayList<Integer>(path_so_far);
		temp.add(current);
		visited.add(current);
		if(current ==destination) {
			path_so_far.add(current);
			return path_so_far;
		}
		for(Edge e: graph.getEdges()) {
			if(e.nodes[0]==current &&e.weight!=0) {
				next =e.nodes[1];
			
			
				if(!visited.contains(next)) {
					
					path_so_far =pathHelper(next, destination,graph,visited,temp);
					if(path_so_far.size()>0) {
						return path_so_far;
					}
				}
			}
			/*if(e.nodes[1]==current) {
				next =e.nodes[0];
				if(!visited.contains(next)) {
					path_so_far.add(current);
					
					path_so_far =pathHelper(next, destination,graph,visited,path_so_far);
					if(path_so_far.size()>0) {
						return path_so_far;
					}
				}
			} */
		} 
		ArrayList<Integer> failure = new ArrayList<Integer>();
		return failure;
	} 
		public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260718389"; //Please initialize this variable with your McGill ID
		int maxFlow = 0; 
		WGraph copy= new WGraph(graph);
		ArrayList<Edge> Edges= new ArrayList<Edge>();
		ArrayList<Edge> startEdges= copy.getEdges();
		for (int i=0;i<startEdges.size();i++){ 
			Edges.add(startEdges.get(i));
		}
		for(Edge e: graph.getEdges()) {
			if (e.weight<0) {	
				maxFlow= -1000;
				answer += maxFlow + "\n" + graph.toString();	
				writeAnswer(filePath+myMcGillID+".txt",answer);
				System.out.println(answer);
				return;
			}
		}
		for (Edge e: Edges) {
			try {
			copy.addEdge(new Edge(e.nodes[1],e.nodes[0],0)); //add reverse edges 
			} catch (Exception a) {
				//Empty catch
			}
		}
		for(Edge e: graph.getEdges()) {
			graph.setEdge(e.nodes[0], e.nodes[1], 0); 
		}
    	ArrayList<Integer> current_path= pathDFS(source,destination,copy);
		while (!current_path.isEmpty()){
			int mininum= copy.getEdge(current_path.get(0), current_path.get(1)).weight;
			for (int i=0; i < current_path.size()-1; i++) {
				int currentCap = (copy.getEdge(current_path.get(i), current_path.get(i+1))).weight;
				if (currentCap<mininum) {
					mininum=currentCap;
				}
			}
			for (int i=0; i < current_path.size()-1; i++) {
				int v_start =current_path.get(i);
				int v_end= current_path.get(i+1);
				if (checkEdge(new Edge(v_start,v_end,0),graph.getEdges())) { 
					Edge current =graph.getEdge(v_start, v_end);
					int current_weight= current.weight;
					graph.setEdge(v_start, v_end, (current_weight +mininum));
				}
				else {
					int current= graph.getEdge(v_end, v_start).weight;  
					graph.setEdge(v_end, v_start,( current -mininum));
				}
				int newBackwordEdge= copy.getEdge(v_end, v_start).weight+mininum;
				int newForwordEdge= copy.getEdge(v_start, v_end).weight-mininum;
				copy.setEdge(v_end, v_start, newBackwordEdge);
				copy.setEdge(v_start, v_end,newForwordEdge);
			}
			maxFlow+= mininum; 
			current_path=pathDFS(source,destination, copy); 
		}
		//End Code
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	} 
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
