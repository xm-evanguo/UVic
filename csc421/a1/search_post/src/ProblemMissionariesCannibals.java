import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProblemMissionariesCannibals extends Problem {

    boolean goal_test(Object state) {
        StateMissionariesCannibals problemState = (StateMissionariesCannibals) state;
        int[][] successfulState = {{0, 0}, {0, 0}, {3, 3}};
        if(Arrays.deepEquals(successfulState, problemState.stateArray)){
            return true;
        }
        return false;
    }

    Set<Object> getSuccessors(Object state) {

        Set<Object> set = new HashSet<Object>();
        StateMissionariesCannibals s = (StateMissionariesCannibals) state;

        StateMissionariesCannibals ss; //successor state

        //move two missionaries to boat
        ss = new StateMissionariesCannibals(s);
        if(ss.boat == 0 && ss.stateArray[0][0] > 1){
            ss.stateArray[0][0] -= 2;
            ss.stateArray[1][0] += 2;
            ss.boat = 1;
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }
        ss = new StateMissionariesCannibals(s);
        if(ss.boat == 1 && ss.stateArray[2][0] > 1){
            //move left
            ss.stateArray[2][0] -= 2;
            ss.stateArray[1][0] += 2;
            ss.boat = 0;
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //move one missionary
        ss = new StateMissionariesCannibals(s);
        if(ss.boat == 0 && ss.stateArray[0][0] > 0){
            ss.stateArray[0][0] -= 1;
            ss.stateArray[1][0] += 1;
            ss.boat = 1;
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }
        ss = new StateMissionariesCannibals(s);
        if(ss.boat == 1 && ss.stateArray[2][0] > 0){
            ss.stateArray[2][0] -= 1;
            ss.stateArray[0][0] += 1;
            ss.boat = 0;
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //move one missionary and one cannibal on boat
        ss = new StateMissionariesCannibals(s);
        if(ss.boat == 0 && ss.stateArray[0][0] > 0 && ss.stateArray[0][1] > 0){
            ss.stateArray[0][0] -= 1;
            ss.stateArray[0][1] -= 1;
            ss.stateArray[1][0] += 1;
            ss.stateArray[1][1] += 1;
            ss.boat = 1;
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }
        ss = new StateMissionariesCannibals(s);
        if(ss.boat == 1 && ss.stateArray[2][0] > 0 && ss.stateArray[2][1] > 0){
            ss.stateArray[2][0] -= 1;
            ss.stateArray[2][1] -= 1;
            ss.stateArray[1][0] += 1;
            ss.stateArray[1][1] += 1;
            ss.boat = 0;
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //move two cannibals on boat
        ss = new StateMissionariesCannibals(s);
        if(ss.boat == 0 && ss.stateArray[0][1] > 1){
            ss.stateArray[0][1] -= 2;
            ss.stateArray[1][1] += 2;
            ss.boat = 1;
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }
        ss = new StateMissionariesCannibals(s);
        if(ss.boat == 1 && ss.stateArray[2][1] > 1){
            ss.stateArray[2][1] -= 2;
            ss.stateArray[1][1] += 2;
            ss.boat = 0;
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //move one cannibal on boat
        ss = new StateMissionariesCannibals(s);
        if(ss.boat == 0 && ss.stateArray[0][1] > 0){
            ss.stateArray[0][1] -= 1;
            ss.stateArray[1][1] += 1;
            ss.boat = 1;
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }
        ss = new StateMissionariesCannibals(s);
        if(ss.boat == 1 && ss.stateArray[1][1] > 0){
            ss.stateArray[2][1] -= 1;
            ss.stateArray[1][1] += 1;
            ss.boat = 0;
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //offload two missionaries boat
        ss = new StateMissionariesCannibals(s);
        if(ss.stateArray[1][0] == 2){
            ss.stateArray[1][0] -= 2;
            if(ss.boat == 0){
                ss.stateArray[0][0] += 2;
            }else{
                ss.stateArray[2][0] += 2;
            }
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //offload one missionary boat
        ss = new StateMissionariesCannibals(s);
        if(ss.stateArray[1][0] > 0){
            ss.stateArray[1][0] -= 1;
            if(ss.boat == 0){
                ss.stateArray[0][0] += 1;
            }else{
                ss.stateArray[2][0] += 1;
            }
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //offload two cannibals boat
        ss = new StateMissionariesCannibals(s);
        if(ss.stateArray[1][1] == 2){
            ss.stateArray[1][1] -= 2;
            if(ss.boat == 0){
                ss.stateArray[0][1] += 2;
            }else{
                ss.stateArray[2][1] += 2;
            }
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //offload one cannibal on boat
        ss = new StateMissionariesCannibals(s);
        if(ss.stateArray[1][1] > 0){
            ss.stateArray[1][1] -= 1;
            if(ss.boat == 0){
                ss.stateArray[0][1] += 1;
            }else{
                ss.stateArray[2][1] += 1;
            }
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        //offload one cannibal and one missionary on boat
        ss = new StateMissionariesCannibals(s);
        if(ss.stateArray[1][0] > 0 && ss.stateArray[1][1] > 0){
            ss.stateArray[1][0] -= 1;
            ss.stateArray[1][1] -= 1;
            if(ss.boat == 0){
                ss.stateArray[0][0] += 1;
                ss.stateArray[0][1] += 1;
            }else{
                ss.stateArray[2][0] += 1;
                ss.stateArray[2][1] += 1;
            }
            if(isStateAllow(ss)){
                set.add(ss);
            }
        }

        return set;
    }

    private boolean isStateAllow(StateMissionariesCannibals state){
        if(state.stateArray[0][1] > state.stateArray[0][0]
            || state.stateArray[2][1] > state.stateArray[2][0]
            || state.stateArray[1][0] + state.stateArray[1][1] > 2)
            return false;
        if(state.stateArray[1][0] + state.stateArray[1][1] == 0
            && state.stateArray[0][0] + state.stateArray[0][1] != 0)
            return false;
        return true;
    }

    double step_cost(Object fromState, Object toState) { return 1; }

    public double h(Object state) { return 0; }

    public static void main(String[] args) throws Exception {
        ProblemMissionariesCannibals problem = new ProblemMissionariesCannibals();
        int[][] initArray = {{3, 3}, {0, 0}, {0, 0}};
        problem.initialState = new StateMissionariesCannibals(initArray);

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
