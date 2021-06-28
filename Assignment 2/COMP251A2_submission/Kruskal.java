package A2;
import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
        WGraph kruskalG =new WGraph();
        DisjointSets myset = new DisjointSets(g.getNbNodes());
        for (Edge e: g.listOfEdgesSorted()) {
        	if(IsSafe(myset, e)){
        		myset.union(e.nodes[0], e.nodes[1]);
        		kruskalG.addEdge(e);
        	}
        }
        return kruskalG; 
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){

        /* Fill this method (The statement return 0 is here only to compile) */
    	boolean retval = true;
    	int endpoints[] =e.nodes;
    	if(p.find(endpoints[0])==p.find(endpoints[1])) {
    		retval =false;
    	}
        return retval;
    
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
