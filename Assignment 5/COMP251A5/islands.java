/*
 * Author: Aleksas Murauskas 260718389
 * Island class 
 * Worked With: Jacob McConnell, Frances Jenks 
 * 
 */


import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;


public class islands {
	ArrayList<String[][]> oceans = new ArrayList<String[][]>();
	ArrayList<boolean[][]> discovered = new ArrayList<boolean[][]>();
	private int trials;
	private int[] heights;
	private int[] widths;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final long start = System.currentTimeMillis();
		islands island_test = new islands();
		for (int a = 0; a <island_test.trials; a++) {
			int island_count = island_test.countOceans(a);
			island_test.writeAnswer(island_count);
			System.out.println(island_count);
			 //Desired Answer is 62 560 142 137 55 290
		}
		final long end = System.currentTimeMillis();
		System.out.println("Milliseconds Execution " + (end - start));
	}
	public static void writeAnswer(int line) {
		BufferedReader reader  = null;
		File file = new File("testIslands_solution.txt");
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
	islands() {
		try {
			int trial_num = 0;
			Scanner scan = new Scanner(new File("testIslands.txt"));
			String[] ln = scan.nextLine().split("\\s+"); 
			this.trials = Integer.parseInt(ln[0]);
			heights = new int[this.trials];
			widths = new int[this.trials];
			for (int a = 0; a < trials; a++) {
				String[] line = scan.nextLine().split("\\s+");
				int height = Integer.parseInt(line[0]);
				int width = Integer.parseInt(line[1]);
				heights[trial_num ] = height;
				widths[trial_num] = width;
				String[][] new_ocean = new String[height][width];
				boolean[][] new_island = new boolean[height][width];
				this.oceans.add(trial_num , new_ocean);
				this.discovered.add(trial_num , new_island);
				for (int x = 0; x < height; x++) {
					String[] ocean_line = scan.nextLine().split("");

					for (int y = 0; y < width; y++) {
						this.discovered.get(trial_num) [x][y] = false;
						this.oceans.get(trial_num)[x][y] = ocean_line[y];
					}
				}
				trial_num ++;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}
	}
	public int countOceans(int num) {
		int num_island = 0;
		int height = this.heights[num];
		int width = this.widths[num];
		String[][] ocean = this.oceans.get(num);
		ArrayList<int[]> depth_change = new ArrayList<int[]>();
		ArrayList<int[]> depth_add = new ArrayList<int[]>();
		boolean[][] discovered = this.discovered.get(num);
		for (int h = 0; h < height; h++) {
			depth_change.clear();
			for (int w = 0; w < width; w++) {
				depth_add.clear();
				depth_change.clear();
				if (is_island(ocean[h][w]) && discovered[h][w] == false) {
					num_island++;
					Isdiscovered(num, h, w);
					if (h > 0) {
						depth_change.add(new int[] { h - 1, w });
					}
					if (h < height - 1) {
						depth_change.add(new int[] { h + 1, w });
					}
					Stack<Integer> queue_y = new Stack<Integer>();
					Stack<Integer> queue_x = new Stack<Integer>();
					boolean check = false;
					if (w == width - 1 && h != height - 1) {
						queue_x.push(w);
						 queue_y.push(h + 1);
					} else if (w != width - 1) {
						queue_x.push(w + 1);
						 queue_y.push(h);
					}
					while ( queue_y.size() != 0) {
						int current_h =  queue_y.peek();
						int current_w = queue_x.peek();
						int up_h;
						int down_h;
						if (current_h < height - 1 && current_h > 0) {
							up_h = current_h + 1;
							down_h = current_h - 1;
						} else if (current_h == height - 1 && current_h > 0) {
							up_h = current_h;
							down_h = current_h - 1;
						} else if (current_h < height - 1 && current_h == 0) {
							up_h = current_h + 1;
							down_h = current_h;
						} else {
							up_h = current_h;
							down_h = current_h;
						}
						if (is_island(ocean[current_h][current_w]) && discovered[current_h][current_w] == false) {
							queue_x.pop();
							 queue_y.pop();
							Isdiscovered(num, current_h, current_w);
							depth_change.add(new int[] { up_h, current_w });
							depth_change.add(new int[] { down_h, current_w });
							if (current_w < width - 1) {
								queue_x.push(current_w + 1);
								 queue_y.push(current_h);
							}
							if (h < current_h) {
								if (current_w > 0 && current_w - 1 != depth_change.get(0)[1]) {
									for (int i = 0; i < depth_change.size(); i++) {
										if (depth_change.get(i)[1] == current_w - 1) {
											check = true;
										}
									}
									if (check == false) {
										queue_x.push(current_w - 1);
										 queue_y.push(current_h);
									}
									check= false;
								}
							}
						} else {
							queue_y.pop();
							queue_x.pop();
							
						}
						if (queue_x.size() == 0) {
								for (int c = 0; c < depth_change.size(); c++) {
									if (is_island(ocean[depth_change.get(c)[0]][depth_change.get(c)[1]])
											&& discovered[depth_change.get(c)[0]][depth_change
													.get(c)[1]] == false) {
										depth_add.add(0, depth_change.get(c));
									}
								}
								if (depth_add.size() != 0) {
									for (int d = 0; d< depth_add.size(); d++) {
										queue_y.push(depth_add.get(d)[0]);
										queue_x.push(depth_add.get(d)[1]);
										
									}
									depth_add.clear();
									depth_change.clear();
								}
						}
					}
				}
			}
		}
		return num_island;
	}
	public void Isdiscovered(int num, int y, int x) {
		this.discovered.get(num)[y][x] = true;
	}
	public boolean is_island(String possible_island) {
		if (possible_island.equals("-")) {
			return true;
		} else {
			return false;
		}
	}
	
}
