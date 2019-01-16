/*
*Student Name: Xi Ming Guo
*Student ID: 00866199
*Assignment #6 - 2-D Arrays
*/

import java.util.Scanner;
import java.util.Random;

public class MineSweeper{

	//in ASCII, 48 = 0
    static int[][] hide = {
        {48, 48, 48, 48, 48, 48, 48, 48},
        {48, 48, 48, 48, 48, 48, 48, 48},
        {48, 48, 48, 48, 48, 48, 48, 48},
        {48, 48, 48, 48, 48, 48, 48, 48},
        {48, 48, 48, 48, 48, 48, 48, 48},
        {48, 48, 48, 48, 48, 48, 48, 48},
        {48, 48, 48, 48, 48, 48, 48, 48},
        {48, 48, 48, 48, 48, 48, 48, 48}
    };
	
	static int[] xy = new int[2];

	static boolean lose = false;
	
	static int c = 0;
	
    static char[][] show = {
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.', '.', '.', '.'}
    };

	//Print array 
	public static void printShow(){
        System.out.println("  | 0 1 2 3 4 5 6 7");
		System.out.println();
		System.out.println("-------------------");

        for(int i = 0; i < show.length; i++){
            System.out.print(i + " | ");
            for(int z = 0; z < show[i].length; z++){
                System.out.print(show[i][z] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
	
	//Random bombs' location, totally 10 bombs
	public static void initializeFullGrid(){
        int count = 0 ;
		int max = 10;
        Random ra = new Random();
        while(count < max){
            int x = ra.nextInt(hide.length);
            int y = ra.nextInt(hide[0].length);
            if(hide[x][y] == 48){
				//99 is a random number that I pick
				hide[x][y] = 99;
                count++;
            }
        }
		cellAroundBomb();
    }
	
	//Record the number of bombs around the cells
	public static void cellAroundBomb(){
		for(int i = 0; i < hide.length; i++){
			for(int z = 0; z < hide[0].length; z++){
				if(hide[i][z] > 98 && i != 0 && i != 7 && z != 0 && z != 7){
					hide[i][z + 1]++;
					hide[i][z - 1]++;
					hide[i + 1][z + 1]++;
					hide[i + 1][z]++;
					hide[i + 1][z - 1]++;
					hide[i - 1][z + 1]++;
					hide[i - 1][z]++;
					hide[i - 1][z - 1]++;
				} else if(hide[i][z] > 98 && i == 0 && z != 0 && z != 7){
					hide[i][z + 1]++;
					hide[i][z - 1]++;
					hide[i + 1][z + 1]++;
					hide[i + 1][z]++;
					hide[i + 1][z - 1]++;
				} else if(hide[i][z] > 98 && i == 7 && z != 0 && z != 7){
					hide[i][z + 1]++;
					hide[i][z - 1]++;
					hide[i - 1][z + 1]++;
					hide[i - 1][z]++;
					hide[i - 1][z - 1]++;
				} else if(hide[i][z] > 98 && z == 0 && i != 0 && i != 7){
					hide[i][z + 1]++;
					hide[i + 1][z + 1]++;
					hide[i + 1][z]++;
					hide[i - 1][z + 1]++;
					hide[i - 1][z]++;
				} else if(hide[i][z] > 98 && z == 7 && i != 0 && i != 7){
					hide[i][z - 1]++;
					hide[i + 1][z]++;
					hide[i + 1][z - 1]++; 
					hide[i - 1][z]++;
					hide[i - 1][z - 1]++;
				} else if(hide[i][z] > 98 && z == 0 && i == 0){
					hide[i][z + 1]++;
					hide[i + 1][z + 1]++;
					hide[i + 1][z]++;
				} else if(hide[i][z] > 98 && z == 0 && i == 7){
					hide[i][z + 1]++;
					hide[i - 1][z + 1]++;
					hide[i - 1][z]++;
				} else if(hide[i][z] > 98 && z == 7 && i == 0){
					hide[i][z - 1]++;
					hide[i + 1][z]++;
					hide[i + 1][z - 1]++;
				} else if(hide[i][z] > 98 && z == 7 && i == 7){
					hide[i][z - 1]++;
					hide[i - 1][z]++;
					hide[i - 1][z - 1]++;
				}
			}
		}
		for(int k = 0; k < hide.length; k++){
			for(int l = 0; l < hide[0].length; l++){
				if(hide[k][l] == 48){
					//In ASCII, 32 = space
					hide[k][l] = 32;
				}
			}
		}
    }
	
	//Read user's input
	public static void scanValue(){
        Scanner sc = new Scanner(System.in);
		System.out.print("Select a cell. Row value (a digit between 0 and 7): ");
		xy[0] = sc.nextInt();
		while(xy[0] > 7 || xy[0] < 0){
			System.out.println("The number you entre isn't between 0 and 7");
			System.out.print("Select a cell. Row value (a digit between 0 and 7): ");
			xy[0] = sc.nextInt();
		}
		
		System.out.print("Select a cell. Column value (a digit between 0 and 7): ");
		xy[1] = sc.nextInt();
		while(xy[1] > 7 || xy[1] < 0){
			System.out.println("The number you entre isn't between 0 and 7");
			System.out.print("Select a cell. Column value (a digit between 0 and 7): ");
			xy[1] = sc.nextInt();
		}
		replace();
		GGWP();
	}
	
	//Replace dot with space, number or bomb
	public static void replace(){
		if(hide[xy [0]][xy [1]] != 32){
			if(hide[xy [0]][xy [1]] > 98 && hide[xy [0]][xy [1]] < 200){
				show[xy [0]][xy [1]] = 'X';
				lose = true;
			} else {
				show[xy [0]][xy [1]] = (char)hide[xy [0]][xy [1]];
				c++;
			}
		} else {
			show[xy [0]][xy [1]] = ' ';
			revealGridCell(xy[0], xy[1]);
			c++;
		}
	}

	//If player uncovers an empty cell, uncover cells around it.
	public static void revealGridCell(int row, int col){
		if(xy[0] != 0 && xy[0] != 7 && xy[1] != 0 && xy[1] != 7){
			show[row][col + 1] = (char)hide[row][col + 1];
			show[row][col - 1] = (char)hide[row][col - 1];
			show[row + 1][col + 1] = (char)hide[row + 1][col - 1];
			show[row + 1][col] = (char)hide[row + 1][col];
			show[row + 1][col - 1] = (char)hide[row + 1][col + 1];
			show[row - 1][col + 1] = (char)hide[row - 1][col + 1];
			show[row - 1][col] = (char)hide[row - 1][col];
			show[row - 1][col - 1] = (char)hide[row - 1][col - 1];
			c = c + 8;
		}else if(xy[0] == 0 && xy[1] != 0 && xy[1] != 7){
			show[row][col + 1] = (char)hide[row][col + 1];
			show[row][col - 1] = (char)hide[row][col - 1];
			show[row + 1][col + 1] = (char)hide[row + 1][col + 1];
			show[row + 1][col] = (char)hide[row + 1][col];
			show[row + 1][col - 1] = (char)hide[row + 1][col - 1];
			c = c + 5;
		}else if(xy[0] == 7 && xy[1] != 0 && xy[1] != 7){
			show[row][col + 1] = (char)hide[row][col + 1];
			show[row][col - 1] = (char)hide[row][col - 1];
			show[row - 1][col + 1] = (char)hide[row - 1][col + 1];
			show[row - 1][col] = (char)hide[row - 1][col];
			show[row - 1][col - 1] = (char)hide[row - 1][col - 1];
			c = c + 5;
		}else if(xy[1] == 0 && xy[0] != 0 && xy[0] != 7){
			show[row][col + 1] = (char)hide[row][col + 1];
			show[row + 1][col + 1] = (char)hide[row + 1][col + 1];
			show[row + 1][col] = (char)hide[row + 1][col];
			show[row - 1][col + 1] = (char)hide[row - 1][col + 1];
			show[row - 1][col] = (char)hide[row - 1][col];
			c = c + 5;
		}else if(xy[1] == 7 && xy[0] != 0 && xy[0] != 7){
			show[row][col - 1] = (char)hide[row][col - 1];
			show[row + 1][col] = (char)hide[row + 1][col];
			show[row + 1][col - 1] = (char)hide[row + 1][col - 1];
			show[row - 1][col] = (char)hide[row - 1][col];
			show[row - 1][col - 1] = (char)hide[row - 1][col - 1];
			c = c + 5;
		}else if(xy[0] == 0 && xy[1] == 0){
			show[row][col + 1] = (char)hide[row][col + 1];
			show[row + 1][col + 1] = (char)hide[row + 1][col + 1];
			show[row + 1][col] = (char)hide[row + 1][col];
			c = c + 3;
		}else if(xy[0] == 0 && xy[1] == 7){
			show[row][col - 1] = (char)hide[row][col - 1];
			show[row + 1][col] = (char)hide[row + 1][col];
			show[row + 1][col - 1] = (char)hide[row + 1][col - 1];
			c = c + 3;
		}else if(xy[0] == 7 && xy[1] == 0){
			show[row][col + 1] = (char)hide[row][col + 1];
			show[row - 1][col + 1] = (char)hide[row - 1][col + 1];
			show[row - 1][col] = (char)hide[row - 1][col];
			c = c + 3;
		} else {
			show[row][col - 1] = (char)hide[row][col - 1];
			show[row - 1][col] = (char)hide[row - 1][col];
			show[row - 1][col - 1] = (char)hide[row - 1][col - 1];
			c = c + 3;
		}
	}
	
	//Determine if the game is over, player win or lose.
	//If the game is over, print all bombs' location
	public static void GGWP(){
		if(lose){
			System.out.println("Kaboom! Game Over!");
			for(int i = 0; i < show.length; i++){
				for(int z = 0; z < show[0].length; z++){
					if(hide[i][z] > 98){
						show[i][z] = 'B';
					}
				}
			}
			show[xy[0]][xy[1]] = 'X';
			printShow();
		} else if(count()){
			System.out.println("Congrats! You Won!");
			for(int i = 0; i < show.length; i++){
				for(int z = 0; z < show[0].length; z++){
					if(hide[i][z] > 98){
						show[i][z] = 'B';
					}
				}
			}
		} else {
			drawFullGrid();
		}
	}
	
	//record unselected cells, if unselected cells is equal or less than 10, player wins.
	public static boolean count(){
		int count = 0;
		for(int i = 0; i < show.length; i++){
            for(int z = 0; z < show[i].length; z++){
                if(show[i][z] == '.'){
					count++;
				}
            }
		}
        if(count <= 10){
			return true;
		} else {
			return false;
		}
	}
	
	//draw the current state of the game
    public static void drawFullGrid(){
        System.out.println("  | 0 1 2 3 4 5 6 7");
		System.out.println();
		System.out.println("-------------------");

        for(int i = 0; i < show.length; i++){
            System.out.print(i + " | ");
            for(int z = 0; z < show[i].length; z++){
                System.out.print(show[i][z] + " ");
            }
            System.out.println();
        }
        System.out.println();
		scanValue();
    }
	
    public static void main(String[] args) {
        printShow();
		initializeFullGrid();
		scanValue();
    }
	
}