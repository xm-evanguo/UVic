import java.util.Arrays;
import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class CG {
    
    public static int min;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nV = sc.nextInt();
        if(nV == 0) {
            System.out.println("0");
            return;
        }
        int[][] map = new int[nV][nV];
        sc.nextLine();
        min = nV;
        for(int i = 0; i < nV; i++) {
            String[] tmp = sc.nextLine().split(" ");
            for(int j = 0; j < tmp.length; j++) {
                map[i][Integer.parseInt(tmp[j])] = 1;
            }
        }
        for(int i = 0; i < nV; i++) {
            int[] color = new int[nV];
            color[i] = 1;
            for(int j = 0; j < nV; j++) {
                for(int k = 0; k < nV; k++) {
                    if(map[j][k] == 1) {
                        if(color[j] == color[k]) {
                            color[k]++;
                        }
                    }
                }
            }
            Arrays.sort(color);
            if(color[color.length - 1] + 1 < min)
                min = color[color.length - 1] + 1;
        }
                
        System.out.println(min);
        sc.close();
    }
    
}