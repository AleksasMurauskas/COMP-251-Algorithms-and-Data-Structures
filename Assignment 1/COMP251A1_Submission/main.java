package A1;

import A1.Chaining.*;
import A1.Open_Addressing.*;


import java.io.*;
import java.util.*;

public class main {

    /**
     * Calculate 2^w
     */
    public static int power2(int w) {
        return (int) Math.pow(2, w);
    }

    /**
     * Uniformly generate a random integer between min and max, excluding both
     */
    public static int generateRandom(int min, int max, int seed) {
        Random generator = new Random();
        //if the seed is equal or above 0, we use the input seed, otherwise not.
        if (seed >= 0) {
            generator.setSeed(seed);
        }
        int i = generator.nextInt(max - min - 1);
        return i + min + 1;
    }

    /**
     * export CSV file
     */
    public static void generateCSVOutputFile(String filePathName, ArrayList<Double> alphaList, ArrayList<Double> avColListChain, ArrayList<Double> avColListProbe) {
        File file = new File(filePathName);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            int len = alphaList.size();
            fw.append("Alpha");
            for (int i = 0; i < len; i++) {
                fw.append("," + alphaList.get(i));
            }
            fw.append('\n');
            fw.append("Chain");
            for (int i = 0; i < len; i++) {
                fw.append("," + avColListChain.get(i));
            }
            fw.append('\n');
            fw.append("Open Addressing");
            for (int i = 0; i < len; i++) {
                fw.append(", " + avColListProbe.get(i));
            }
            fw.append('\n');
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int[] createRandomList()
    {
    	int[] retlist = new int[32];
    	HashSet<Integer> used = new HashSet<Integer>();
    	for(int x=0; x<retlist.length; x++) {
    		int value= generateRandom(1,300,-1);
    		while(used.contains(value)) {
    			value = generateRandom(1,300,-1);
    		}
    		used.add(value);
    		retlist[x] = value;
    	}
    	return retlist;
    }
    public static void main(String[] args) {

        /*===========PART 1 : Experimenting with n===================*/
        //Initializing the three arraylists that will go into the output 
        ArrayList<Double> alphaList = new ArrayList<Double>();
        ArrayList<Double> avColListChain = new ArrayList<Double>();
        ArrayList<Double> avColListProbe = new ArrayList<Double>();

        //Keys to insert into both hash tables
        int[] keysToInsert = {164, 127, 481, 132, 467, 160, 205, 186, 107, 179,
            955, 533, 858, 906, 207, 810, 110, 159, 484, 62, 387, 436, 761, 507,
            832, 881, 181, 784, 84, 133, 458, 36};

        //values of n to test for in the experiment
        int[] nList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        //value of w to use for the experiment on n
        int w = 10;

        for (int n : nList) {

            //initializing two hash tables with a seed
            Chaining MyChainTable = new Chaining(w, 137);
            Open_Addressing MyProbeTable = new Open_Addressing(w, 137);

          
            int c_collisions=0, p_collisions=0;
            for(int a = 0; a<n;a++) {
            	c_collisions += MyChainTable.insertKey(keysToInsert[a]);
            	p_collisions +=  MyProbeTable.insertKey(keysToInsert[a]);
            }
            alphaList.add(Double.valueOf(n)/Double.valueOf(MyChainTable.m));
            avColListChain.add(Double.valueOf(c_collisions)/Double.valueOf(n));
            avColListProbe.add(Double.valueOf(p_collisions)/Double.valueOf(n));
            
        }
        
        generateCSVOutputFile("n_comparison.csv", alphaList, avColListChain, avColListProbe);

        /*===========    PART 2 : Test removeKey  ===================*/
 /* In this exercise, you apply your removeKey method on an example.
        Make sure you use the same seed, 137, as you did in part 1. You will
        be penalized if you don't use the same seed.
         */
        //Please not the output CSV will be slightly wrong; ignore the labels.
        ArrayList<Double> removeCollisions = new ArrayList<Double>();
        ArrayList<Double> removeIndex = new ArrayList<Double>();
        int[] keysToRemove = {6, 8, 164, 180, 127, 3, 481, 132, 4, 467, 5, 160,
            205, 186, 107, 179};

        double collision_removed = 0;
        Open_Addressing ProbeTableRemove = new Open_Addressing(w, 137);
		for(int b = 0; b < keysToInsert.length ; b++) {
			ProbeTableRemove.insertKey(keysToInsert[b]);
		}
		for(int c = 0; c < keysToRemove.length ; c++) {
			int key = keysToRemove[c];
			collision_removed = ProbeTableRemove.removeKey(key);
			removeCollisions.add(collision_removed);
			removeIndex.add((double)c);
		}
		

		generateCSVOutputFile("remove_collisions.csv", removeIndex, removeCollisions, removeCollisions);
        /*===========PART 3 : Experimenting with w===================*/

 /*In this exercise, the hash tables are random with no seed. You choose 
                values for the constant, then vary w and observe your results.
         */
        //generating random hash tables with no seed can be done by sending -1
        //as the seed. You can read the generateRandom method for detail.
        //randomNumber = generateRandom(0,55,-1);
        //Chaining MyChainTable = new Chaining(w, -1);
        //Open_Addressing MyProbeTable = new Open_Addressing(w, -1);
        //Lists to fill for the output CSV, exactly the same as in Task 1.
        ArrayList<Double> alphaList2 = new ArrayList<Double>();
        ArrayList<Double> avColListChain2 = new ArrayList<Double>();
        ArrayList<Double> avColListProbe2 = new ArrayList<Double>();

        //ADD YOUR CODE HERE
        
        int[] wTest = {10,11,12,13,14,15,16,17,18,19,20};
     
      //   int[] wTest = {5,6,7,8,9,10,11,12,13,14,15};
         for(int k = 0;k<wTest.length;k++) { 
            double probe_collisions_w = 0;
            
            double chain_collisions_w = 0;
            for(int x=0; x<10; x++) {
            	int test_chain_collisions_pertest = 0 ;
                int test_probe_collisions_pertest = 0 ;
                int[] addList=createRandomList();
                Chaining TestingChainTable = new Chaining(wTest[k], -1);
                Open_Addressing TestingProbeTable = new Open_Addressing(wTest[k], -1);
                for(int a=0;a< addList.length;a++ ) {
                	test_chain_collisions_pertest +=  TestingChainTable.insertKey(keysToInsert[a]);
                    test_probe_collisions_pertest += TestingProbeTable.insertKey(keysToInsert[a]);
                }
                chain_collisions_w += test_chain_collisions_pertest;
                probe_collisions_w += test_probe_collisions_pertest;
                
            }
            int m = power2((wTest[k] - 1) / 2 + 1);
            alphaList2.add(Double.valueOf(32/m));
            avColListChain2.add(Double.valueOf(chain_collisions_w)/10.0);
            avColListProbe2.add(Double.valueOf(probe_collisions_w)/10.0);
         }
        
         
        generateCSVOutputFile("w_comparison.csv", alphaList2, avColListChain2, avColListProbe2);

    }
}