import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProblemWolfGoatCabbage extends Problem {

    boolean goal_test(Object state) {
        StateWolfGoatCabbage problemState = (StateWolfGoatCabbage) state;
        int[] successfulState = {1, 1, 1, 1};
        if(Arrays.equals(successfulState, problemState.stateArray)){
            return true;
        }
        return false;
    }

    Set<Object> getSuccessors(Object state) {

        Set<Object> set = new HashSet<Object>();
        StateWolfGoatCabbage s = (StateWolfGoatCabbage) state;

        StateWolfGoatCabbage ss; //successor state

        //move cabbage
        ss = new StateWolfGoatCabbage(s);
        if(ss.stateArray[0] == ss.stateArray[1]){
            ss.stateArray[0] = -ss.stateArray[0];
            ss.stateArray[1] = -ss.stateArray[1];
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //move goat
        ss = new StateWolfGoatCabbage(s);
        if(ss.stateArray[0] == ss.stateArray[2]){
            ss.stateArray[0] = -ss.stateArray[0];
            ss.stateArray[2] = -ss.stateArray[2];
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //move wolf
        ss = new StateWolfGoatCabbage(s);
        if(ss.stateArray[0] == ss.stateArray[3]){
            ss.stateArray[0] = -ss.stateArray[0];
            ss.stateArray[3] = -ss.stateArray[3];
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //move myself
        ss = new StateWolfGoatCabbage(s);
        ss.stateArray[0] = -ss.stateArray[0];
        if(isStateAllow(ss)){
            set.add(ss);
        }

        return set;
    }

    private boolean isStateAllow(StateWolfGoatCabbage state){
        if(state.stateArray[0] != state.stateArray[1] && state.stateArray[1] == state.stateArray[2]
            || state.stateArray[0] != state.stateArray[2] && state.stateArray[2] == state.stateArray[3]){
            return false;
        }
        return true;
    }

    double step_cost(Object fromState, Object toState) { return 1; }

    public double h(Object state) {
        StateWolfGoatCabbage s = (StateWolfGoatCabbage) state;
        double sum = 0;
        for(int i = 0; i < 4; i++){
            sum += (double)((StateWolfGoatCabbage) state).stateArray[0];
        }
        return 4 - sum;
    }

    public static void main(String[] args) throws Exception {
        ProblemWolfGoatCabbage problem = new ProblemWolfGoatCabbage();
        int[] initArray = {-1, -1, -1, -1};
        problem.initialState = new StateWolfGoatCabbage(initArray);

        Search search  = new Search(problem);

        System.out.println("TreeSearch------------------------");
        //System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
        //System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());
        //System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
        //System.out.println("GreedyBestFirstTreeSearch:\t" + search.GreedyBestFirstTreeSearch());
        //System.out.println("AstarTreeSearch:\t\t" + search.AstarTreeSearch());

        System.out.println("\n\nGraphSearch----------------------");
        System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
        System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
        //System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
        //System.out.println("GreedyBestGraphSearch:\t\t" + search.GreedyBestFirstGraphSearch());
        System.out.println("AstarGraphSearch:\t\t" + search.AstarGraphSearch());

        System.out.println("\n\nIterativeDeepening----------------------");
        //System.out.println("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
        System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());
    }

}
