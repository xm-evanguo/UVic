/*
 * Name: Evan Guo
 * Student Number: V00866199
 * Assignment 3
 */

public class MazeSolver {
	private static StackRefBased<MazeLocation> path;
	private static StackRefBased<MazeLocation> tmp;
	private static MazeLocation entry;
	private static MazeLocation exit;
	private static MazeLocation currentPoint;
	private static MazeLocation tmp2;
	private static boolean[][] discovery;
	private static int row;
	private static int col;
	
    public static String findPath(Maze maze){
    	entry = maze.getEntry();
    	exit = maze.getExit();
    	
    	//build a 2d-boolean array to track the locations have visited
    	discovery = new boolean[maze.getNumRows()][maze.getNumCols()];
    	path = new StackRefBased<MazeLocation>();
		tmp = new StackRefBased<MazeLocation>();
		
		//push the entry into stack
    	path.push(entry);
    	discovery[entry.getRow()][entry.getCol()] = true;
    	currentPoint = new MazeLocation(entry.getRow(), entry.getCol());
    	
    	try {
    		//keep doing this when program hasn't reach the exit
			while(!path.peek().equals(exit)){
				row = currentPoint.getRow();
				col = currentPoint.getCol();
				//if the stack is not wall and unvisited, push it into stack
				if(!maze.isWall(row - 1, col) && !discovery[row - 1][col]){
					currentPoint = new MazeLocation(row - 1, col);
					path.push(currentPoint);
					discovery[row - 1][col] = true;
				}else if(!maze.isWall(row, col + 1) && !discovery[row][col + 1]){
					currentPoint = new MazeLocation(row, col + 1);
					path.push(currentPoint);
					discovery[row][col + 1] = true;
				}else if(!maze.isWall(row, col - 1) && !discovery[row][col - 1]){
					currentPoint = new MazeLocation(row, col - 1);
					path.push(currentPoint);
					discovery[row][col - 1] = true;
				}else if(!maze.isWall(row + 1, col) && !discovery[row + 1][col]){
					currentPoint = new MazeLocation(row + 1, col);
					path.push(currentPoint);
					discovery[row + 1][col] = true;
				}else{
					//if it is a dead end
					path.pop();
					if(path.size() == 0){
						break;
					}
					currentPoint = path.peek();
				}
			}
			//change the stack order
			while(!path.isEmpty()){
				tmp2 = path.pop();
				tmp.push(tmp2);
    		}
		} catch (StackEmptyException e) {
			System.out.println("Something wrong");
		}
        return tmp.toString();
    }
}