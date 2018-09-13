import java.util.*;
import java.io.*;

public class sat2sud{

  //output graph
  static int[] sud = new int[82];

  public static void main(String[] args){

    Scanner in = new Scanner(System.in);
    //track position of solution sud
    int pos = 1;
    int solution = 0;
    //for basic task output file, solution contains SAT
    String check = in.next();
    if(check.equals("SAT")){
      for(int i=0; i<729; i++){
        solution = Integer.parseInt(in.next());
        if(solution>0){
          solution = solution%9;
          if(solution == 0){
            solution = 9;
            sud[pos] = solution;
          }else{
            sud[pos] = solution%9;
          }
          pos++;
        }
      }
    //for extended task 3, output file do not contains SAT
    }else{
      solution = Integer.parseInt(check);
      if(solution>0){
        solution = solution%9;
        if(solution == 0){
          solution = 9;
          sud[pos] = solution;
        }else{
          sud[pos] = solution%9;
        }
        pos++;
      }
      for(int i=0; i<728; i++){
        solution = Integer.parseInt(in.next());
        if(solution>0){
          solution = solution%9;
          if(solution == 0){
            solution = 9;
            sud[pos] = solution;
          }else{
            sud[pos] = solution%9;
          }
          pos++;
        }
      }
    }
    //output in sudoku format
    System.out.println("Solution:\n");
    for(int i=1; i<82; i++){
      System.out.print(sud[i]+ " ");
      if(i%3 == 0){
        System.out.print(" ");
      }
      if(i%9 == 0){
        System.out.print("\n");
      }
      if(i%27 == 0){
        System.out.print("\n");
      }
    }
  }
}
