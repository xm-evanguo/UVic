/*
 * CSC320 Summer 2018
 * Project - SAT-based Sudoku Solving
 * sud2sat.java
 */

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class sud2sat_v2{

  //list of clauses
  static String clauses = "";
  //number of clauses
  static int numc = 0;


public static void main (String[] args){
    Scanner in = new Scanner(System.in);
    String number = in.nextLine();
    if(number.length() < 81){
        multiLine(in, number);
    }else{
        oneLine(number);
    }
    in.close();
    encode();
    try {
		outputs();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public static void multiLine(Scanner in, String number) {
	String input;
	int pos;
	int i = 1;
	int j = 1;
	for(;;) {
		pos = 0;
		for(j = 1; j < 10; j++) {
			if(number.length() < 9)
				number = in.nextLine();
			number = replacing(number);
			while(number.charAt(pos) == ' ') {
				pos++;
			}
			if(number.charAt(pos) != '0'){
		    		input = Integer.toString(bnine(i, j, number.charAt(pos)-48));
		    		clauses = clauses + input + " 0\n";
		    		numc++;
		   	}
            pos++;
		}
		if(i == 9 && j == 10) break;
		number = in.nextLine();
		i += 1;
		j = 1;
	}
}

public static void oneLine(String number){
    String input;
    int pos = 0;
    number = replacing(number);
    for(int i = 1; i < 10; i++){
        for(int j = 1; j < 10; j++){
           while(number.charAt(pos) == ' '){
                pos++;
           }
           if(number.charAt(pos) != '0'){
        	   input = Integer.toString(bnine(i, j, number.charAt(pos)-48));
               clauses = clauses + input + " 0\n";
               numc++;
           }
           pos++;
        }
    }
}

public static String replacing(String number) {
    if(number.contains("*"))
        number = number.replace("*", "0");
    if(number.contains("."))
        number = number.replace(".", "0");
    if(number.contains("?"))
        number = number.replace("?", "0");
    return number;
}

  //converting ijk to base-9 number
public static int bnine(int i, int j, int k){
	return 81*(i-1)+9*(j-1)+(k-1)+1;
}

  //encoding
public static void encode(){
	String newc = "";

   //Every cell contains at least one number
  	for(int i=1; i<10; i++){
  		for(int j=1; j<10; j++){
  			for(int k=1; k<10; k++){
        	       newc = Integer.toString(bnine(i,j,k));
        	       clauses = clauses + newc + " ";
  			}
  			clauses = clauses + "0\n";
  			numc++;
  		}
	}

   //Each number appears at least once in every row
    for(int j=1; j<10; j++){
		for(int k=1; k<10; k++){
			for(int i=1; i<10; i++){
				newc = Integer.toString(bnine(i,j,k));
                clauses = clauses + newc + " ";
			}
            clauses = clauses + "0\n";
            numc++;
		}
    }

   //Each number appears at least once in every column
    for(int i=1; i<10; i++){
    	for(int k=1; k<10; k++){
    		for(int j=1; j<10; j++){
    			newc = Integer.toString(bnine(i,j,k));
                clauses = clauses + newc + " ";
            }
    		clauses = clauses + "0\n";
    		numc++;
    	}
    }
   //Each number appears at most one in every 3x3 sub-grid
    for(int k=1; k<10; k++){
    	for(int a=0; a<3; a++){
    		for(int b=0; b<3; b++){
				for(int u=1; u<4; u++){
					for(int v=1; v<3; v++){
						for(int w=v+1; w<4; w++){
							newc = Integer.toString(bnine(3*a+u,3*b+v,k)*-1) + " " + Integer.toString(bnine(3*a+u,3*b+w,k)*-1);
							clauses = clauses + newc + " 0\n";
							numc++;
						}
					}
				}
    		}
    	}
	}

    for(int k=1; k<10; k++){
    	for(int a=0; a<3; a++){
    		for(int b=0; b<3; b++){
    			for(int u=1; u<3; u++){
    				for(int v=1; v<4; v++){
    					for(int w=u+1; w<4; w++){
    						for(int t=1; t<4; t++){
    							newc = Integer.toString(bnine(3*a+u,3*b+v,k)*-1) + " " + Integer.toString(bnine(3*a+w,3*b+t,k)*-1);
    							clauses = clauses + newc + " 0\n";
    							numc++;
    				         }
    					 }
					  }
				   }
    		    }
    	    }
	   }

}

    public static void outputs() throws IOException{
        FileWriter writer = new FileWriter("sud2satout.txt");
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.print("p cnf 729 " + numc + "\n");
        printWriter.print(clauses);
        printWriter.close();
    }
}
