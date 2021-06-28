import java.util.*;
import java.io.*;

public class Multiply{
	private static int naiveCount=0;
	private static int karatsubaCount=0;
    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {
    	NCountReset();
        // YOUR CODE GOES HERE  (Note: Change return statement)
        int result [] = new int [2];
        int[] answer;
        answer = naive_Helper(size, x, y);
        result[0] = answer[0];
        result[1] = answer[1];
        return result;
    }
    public static int[] naive_Helper(int size, int x, int y) {
    	int[] result = new int [2];
    	if (size == 1) {
    		result[0] = x*y;
    		result[1] = 1;
    		return result; 
    	}
    	else {
    		int m = (int) Math.ceil(size/2.0)  ; //New size 
    		int power2toM = (int) Math.pow(2, m);
    		int power2to2M =(int) Math.pow(2, (2*m));
    		int a = (x/(power2toM));
    		int b = (x%(power2toM));
    		int c = (y/(power2toM));
    		int d = (y%(power2toM));
    
    		int e[] = naive_Helper(m,a,c);
    		int f[] = naive_Helper(m,b,d);
    		int g[] = naive_Helper(m,b,c);
    		int h[] = naive_Helper(m,a,d);
    		result[0]=(e[0]*(power2to2M) +(g[0]+h[0])*(power2toM) +f[0]);
    		result[1] = 3*m + e[1]+f[1]+g[1]+h[1];
    		return result;
    	}
    }
    public static void KCountReset() {
    	karatsubaCount =0;
    }
    public static void NCountReset() {
    	naiveCount =0;
    }
    public static int[] karatsuba(int size, int x, int y) {
        KCountReset();
        // YOUR CODE GOES HERE  (Note: Change return statement)
    	 int result [] = new int [2];
         int cost =0;
         int answer[];
         answer =karatsubaHelper(size,x,y);
         result[0] = answer[0];
         result[1] = answer[1];
         return result;
        
    }
    public static int[]  karatsubaHelper(int size, int x, int y) {
    	int[] result = new int [2];
    	if (size == 1) {
    		result[0] = x*y;
    		result[1] = 1;
    		return result; 
    	}
    	else {
    		int m = (int) Math.ceil(size/2.0)  ; //New size 
    		int power2toM = (int) Math.pow(2, m);
    		int power2to2M =(int) Math.pow(2, 2*m);
    		int a =(x/(power2toM));
    		int b =(x % (power2toM ));
    	
    		int c = (y/(power2toM));
    		int d = (y % (power2toM));
    	
    		int e[] = karatsubaHelper(m, a,c);
    		int f[] = karatsubaHelper(m, b,d);
    		int g[] = karatsubaHelper(m,a-b, c-d);
    		result[0] =(e[0]*(power2to2M) +(e[0]+f[0]-g[0])*(power2toM) +f[0]);
    		result[1] =(6*m)+e[1]+f[1]+g[1];
    		return result;
    	}
    }
    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}