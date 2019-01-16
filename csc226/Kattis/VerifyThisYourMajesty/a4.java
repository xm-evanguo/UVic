/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class a4 {
    
    private static boolean[] row;
    private static boolean[] rowPlus;
    private static boolean[] rowMinus;
    private static int[] place;
    private static int size;
    
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        size = io.getInt();
        place = new int[size];
        row = new boolean[size];
        rowPlus = new boolean[2*size];
        rowMinus = new boolean[3*size];
        if (size == 1) {
        	io.println("CORRENT");
        	io.close();
        	return;
        }
        for(int i = 0; i < size; i++) {
        	int x = io.getInt();
        	int y = io.getInt();
        	if(place[y] != 0) {
        		io.println("INCORRECT");
        		io.close();
        		return;
        	}
        	place[y] = x;
        }
        if(find(0)) {
        	io.println("CORRECT");
        }else {
        	io.println("INCORRECT");
        }
        io.close();
    }
    
    private static boolean find(int col) {
       int x = place[col];
       if(!row[x] && !rowPlus[x+col] && !rowMinus[x-col+size]) {
         	place[col] = -1;
           	row[x] = true;
           	rowPlus[x+col] = true;
           	rowMinus[x-col+size] = true;
           	if(col == size - 1) {
           		return solution();
           	}else {
           		return find(col + 1);
           	}
       }
        return false;
    }
    
    private static boolean solution() {
    	for(int i = 0; i < size; i++) {
    		if(place[i] != -1) {
    			return false;
    		}
    	}
    	return true;
    }
}
