import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProblemPancake extends Problem {

    boolean goal_test(Object state) {
        StatePancake pancakeState = (StatePancake) state;

        for(int i=1; i<pancakeState.pancakeArray.length; i++)
            if(pancakeState.pancakeArray[i] < pancakeState.pancakeArray[i-1])
                return false;

        return true;
    }

    Set<Object> getSuccessors(Object state) {

        Set<Object> set = new HashSet<Object>();
        StatePancake s = (StatePancake) state;
        int size = s.pancakeArray.length;

        StatePancake ss; //successor state

        for(int i = 1; i < size; i++){
            ss = new StatePancake(s);
            int tmp;
            for(int j = 0; j < i/2; j++){
                tmp = ss.pancakeArray[j];
                ss.pancakeArray[j] = ss.pancakeArray[i - j];
                ss.pancakeArray[i - j] = tmp;
            }
            set.add(ss);
        }

        return set;
    }

    double step_cost(Object fromState, Object toState) { return 1; }

    public double h(Object state) {
        StatePancake s = (StatePancake) state;
        StatePancake ss = new StatePancake((StatePancake) state);
        double sum = 0;
        Arrays.sort(ss.pancakeArray);
        for(int i = 0; i < s.pancakeArray.length; i++){
            for(int j = 0; j < s.pancakeArray.length; j++){
                if(s.pancakeArray[i] == ss.pancakeArray[j]){
                    sum += Math.abs(j-i);
                    break;
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        ProblemPancake problem = new ProblemPancake();
        int[] pancakeArray = {1, 0, 3, 5, 2, 4};
        problem.initialState = new StatePancake(pancakeArray);

        Search search  = new Search(problem);

        System.out.println("TreeSearch------------------------");
        System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
        System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());
        System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
        //System.out.println("GreedyBestFirstTreeSearch:\t" + search.GreedyBestFirstTreeSearch());
        System.out.println("AstarTreeSearch:\t\t" + search.AstarTreeSearch());

        System.out.println("\n\nGraphSearch----------------------");
        System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
        System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
        System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
        System.out.println("GreedyBestGraphSearch:\t\t" + search.GreedyBestFirstGraphSearch());
        System.out.println("AstarGraphSearch:\t\t" + search.AstarGraphSearch());

        System.out.println("\n\nIterativeDeepening----------------------");
        System.out.println("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
        System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());
    }

}
