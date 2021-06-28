public class BellmanFord{


/* Author: Aleksas Murauskas 260718389
 * Collaborators: Nathan Brezner, Jacob McConnell
 * 
 * 
 */
	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 * 
	 * Use this to specify a negative cycle has been found 
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();} 
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}
	
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *  
         *  When throwing an exception, choose an appropriate one from the ones given above
         */
        
        /* YOUR CODE GOES HERE */
    	this.source = source;
    	distances = new int[g.getNbNodes()];
    	predecessors =new int[g.getNbNodes()];
    	for(int x=0;x< distances.length;x++) {
    		distances[x] = Integer.MAX_VALUE;
    	}
    	distances[this.source]=0;
    	predecessors[this.source]=0;
    	for(int y=1; y<g.getNbNodes();y++) {
    		for(Edge e: g.getEdges()) {
    			int u = e.nodes[0];
    			int v = e.nodes[1];
    			int weight = e.weight;
    			if(distances[u]!= Integer.MAX_VALUE && distances[u]+weight < distances[v]) {
    				distances[v] = distances[u]+weight;
    				predecessors[v] = u;
    			}
    		}
    	}
    	for(Edge e: g.getEdges()) {
    		int u = e.nodes[0];
			int v = e.nodes[1];
			int weight = e.weight;	
			if(distances[u]!= Integer.MAX_VALUE && distances[u]+weight < distances[v]) {
				throw new BellmanFordException("Negative Weight Cycle");
			}
    	}
    	
    }

    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Exception is thrown
         * Choose appropriate Exception from the ones given 
         */
    	int current_node =destination;
    	int shortest[] = new int[predecessors.length];
    	int index=1;
    	shortest[0] = destination;
    	while(predecessors[current_node]!=this.source) {
    		shortest[index] = predecessors[current_node];
    		index++;
    		current_node = predecessors[current_node];
    	}
    	shortest[index] = this.source;
    	int []  final_path= new int[index+1];
    	int z=0;
    	for(int y = index; y>=0;y--) {
    		final_path[z] = shortest[y];
    		z++;
    	}
        /* YOUR CODE GOES HERE (update the return statement as well!) */
        
        return final_path;
    }
    //public int [] shortPathfinder(int destination,)

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   } 
}

//


