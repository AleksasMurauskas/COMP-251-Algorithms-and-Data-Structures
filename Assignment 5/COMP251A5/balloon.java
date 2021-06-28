/*
 * Author: Aleksas Murauskas 260718389
 * Balloon Class 
 * Worked With: Jacob McConnell, Frances Jenks 
 * 
 */


import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;


public class balloon {
	private int[] balloon_numbers;
	ArrayList<int[]> levels = new ArrayList<int[]>();
	private int trials;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		balloon ballon_test = new balloon();
		int arrows_needed = 0;
		for(int a = 0;a<ballon_test.trials;a++){
		arrows_needed = ballon_test.shoot_arrow(ballon_test.levels.get(a), ballon_test.balloon_numbers[a]);
		writeAnswer(arrows_needed);
		System.out.println(arrows_needed + " ARROWS REQUIRED");
		//Desired answer is 6 1 4 3 6 1785
		}
		long end = System.currentTimeMillis();
		System.out.println("Milliseconds Execution " + (end - start));
	}
	public static void writeAnswer(int line) {
		BufferedReader reader  = null;
		File file = new File("testBalloons_solution.txt");
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter file_writ = new FileWriter(file, true);
			BufferedWriter writer = new BufferedWriter(file_writ);
			writer.write(line + "\n");
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			 System.exit(1);
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException ex) {
				 System.exit(1);
			}
		}
	}
	balloon(){
		try {
	        Scanner balloonFile = new Scanner(new File("testBalloons.txt"));
	        String[] ln = balloonFile.nextLine().split("\\s+"); 
	        this.trials = Integer.parseInt(ln[0]);
	        balloon_numbers = new int[this.trials];
	        String[] line = balloonFile.nextLine().split("\\s+");
	        for(int a = 0;a<trials;a++) {
	        	balloon_numbers[a] = Integer.parseInt(line[a]);
	        }
	        for(int b = 0;b<trials;b++) {
	        	int[] curr_h = new int[balloon_numbers[b]];
	        	line = balloonFile.nextLine().split("\\s+");
	        	for(int c = 0 ;c<balloon_numbers[b];c++){
	        		curr_h[c] = Integer.parseInt(line[c]);
	        	}
	        	levels.add(curr_h);
	        }
	        balloonFile.close();
	    }
	    catch (FileNotFoundException e){
	        System.out.println("File not found!");
	        System.exit(1);
	    }
	}
	public int shoot_arrow(int[] heights,int balloons){
		int[] balloonsRemain = heights.clone();
		ArrayList<ArrayList<Integer>> arr_path= new ArrayList<ArrayList<Integer>>();
		int arr = 0;
		int current_arr_h = 0;
		int balloons_left = balloonsRemain.length;
		ArrayList<Integer> current_arr_path = new ArrayList<Integer>();
		while(balloons_left>0){
			int[] max_height = FindMaximum(balloonsRemain);
			current_arr_h = max_height[1];
			current_arr_path.add(current_arr_h);
			balloons_left--;
			balloonsRemain[max_height[0]] = 0;
			for(int i = max_height[0];i<balloonsRemain.length;i++){
				if(current_arr_h==1){
					break;
				}
				if(balloonsRemain[i]==current_arr_h-1&&balloonsRemain[i]!= 0){
					current_arr_h--;
					current_arr_path.add(current_arr_h);
					balloonsRemain[i] = 0;
					balloons_left--;
				}
			}
			arr_path.add(current_arr_path);
			current_arr_path.clear();
		}
		arr = arr_path.size();
		return arr;
	}
	public int[] FindMaximum(int[] heights){
		int[] maximum = new int[2];
		maximum[0] = 0; maximum[1] = 0;
		for(int a = 0;a<heights.length;a++){
			if(heights[a]>maximum[1]){
				maximum = (new int[] {a,heights[a]});
			}
		}
		return maximum;
	}
}
