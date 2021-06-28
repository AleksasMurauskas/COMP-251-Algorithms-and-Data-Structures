/*
 * Author: Aleksas Murauskas 260718389
 * Mancala Class 
 * Worked With: Jacob McConnell, Frances Jenks 
 * 
 */



import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class mancala {
	private int trials;       
	ArrayList<int[]> man_boards = new ArrayList<int[]>();    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int moves = 0;
		long start = System.currentTimeMillis();
		mancala mancala_game = new mancala();
		for(int a = 0; a<mancala_game.trials;a++){
		moves = mancala_game.play_mancala(mancala_game.man_boards.get(a));
		mancala_game.writeToFile(moves);
		//Desired Answer 0 1 1 1 5 3
		}
		long end = System.currentTimeMillis();
		System.out.println("Milliseconds Execution " + (end - start));
	}
	public static void writeToFile(int line) {
		BufferedReader reader = null;
		File file = new File("testMancala_solution.txt");
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
	public mancala(){
		try {
			File mancala_file = new File("testMancala.txt");
	        Scanner mancalafile = new Scanner(mancala_file);
	        String[] ln = mancalafile.nextLine().split("\\s+");
	        this.trials = Integer.parseInt(ln[0]);
	        int[] boards = new int[12];
	        for(int x = 0;x<this.trials;x++) {
	        	String[] input = mancalafile.nextLine().split("\\s+");
	        	for(int y = 0;y<12;y++){
	        		boards[y] = Integer.parseInt(input[y]);
	        	}
	        	man_boards.add(boards.clone());
	        }
	        mancalafile.close();
	    }
	    catch (FileNotFoundException e){
	        System.out.println("File not found!");
	        System.exit(1);
	    }
	}
	public int[] move(int[] current_board,int[] selected){
		if(current_board[selected[0]]==1){
			current_board[selected[0]]=0; current_board[selected[1]] = 0; current_board[selected[2]] = 1;
		}
		else{
			current_board[selected[0]]=1; current_board[selected[1]] = 0; current_board[selected[2]] = 0;
		}
		return current_board;
	}
	public boolean check_valid(int[] possibilty){
		if(possibilty[0]==0&&possibilty[1]==1&&possibilty[2]==1){
			return true;
		}
		else if(possibilty[0]==1&&possibilty[1]==1&&possibilty[2]==0){
			return true;
		}
		else{
			return false;
		}
	}
	public int play_mancala(int[] board){
		int last_rock = 0;
		int min_rocks = 0;
		ArrayList<int[] > options = new ArrayList<int[]>();
		int[] possible_move = new int[3];
		for(int a = 0;a<12;a++){
			if(board[a]==1){
				min_rocks++;
			}
		}
		for(int b = 1;b<11;b++){
			possible_move[0] = board[b-1];possible_move[1] = board[b];possible_move[2] = board[b+1];
			if(check_valid(possible_move)){
				options.add(new int[] {b-1,b,b+1});
			}
		}
		for(int c = 0;c<options.size();c++){
			int[] current = board.clone();
			current = move(current,options.get(c));
			for(int d = 0;d<12;d++){
        	}
			last_rock  = play_mancala(current);
			if(last_rock <min_rocks){
				min_rocks = last_rock ;
			}
		}
		return min_rocks;
	}
}
