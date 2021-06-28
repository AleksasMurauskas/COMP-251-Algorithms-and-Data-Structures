package A1;

import static A1.main.*;

public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }
    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */

    public int removeKey(int key) {
		//ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
		int x = 0, k=key,collision = 0;
		int probe_val = probe(k, x);
		boolean found = false;
		while(!found && x < m) {
			if(k == Table[probe_val ]) {
				Table[probe_val ] = -2;
				found = true;
			} 
			else {
				if(isSlotEmpty(probe_val )) {
					collision++;
					break;
				}
				else {
					x++;
					collision++;
					probe_val  = probe(k, x);
					continue;
				}
			}
		}
		return collision;
	}

    /**
     * Implements the hash function g(k)
     */
    public int probe(int key, int i) {
    	int hashretval=-1, k =key, modval = power2(w);
    	hashretval = ((A*k)%modval);
    	hashretval = hashretval >>(w-r);
        hashretval = (hashretval +i)%power2(r);
        return hashretval;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int k =key;
    	int probe_i= 0,  probeval = probe(k, probe_i), collision =0;
    	while(probe_i<Table.length && isSlotEmpty(probeval)==false ) {
        	probe_i++;
        	probeval = probe(k, probe_i);
        	collision ++;
        }
    	 Table[probeval] = key;
         return collision;
    }

   

}
