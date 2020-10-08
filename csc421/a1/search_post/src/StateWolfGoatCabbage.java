import java.util.Arrays;

public class StateWolfGoatCabbage {
    int[] stateArray;
//    stateArray[0] = me/boat
//    stateArray[1] = cabbage
//    stateArray[2] = goat
//    stateArray[3] = wolf

    public StateWolfGoatCabbage(int[] stateArray) {
        this.stateArray = stateArray;
    }

    public StateWolfGoatCabbage(StateWolfGoatCabbage state){
        int[] copyArray = new int[4];
        for(int i = 0; i < 4; i++){
            copyArray[i] = state.stateArray[i];
        }
        this.stateArray = copyArray;
    }

    public boolean equals(Object o) {
        return Arrays.equals( stateArray, ((StateWolfGoatCabbage) o).stateArray );
    }

    public int hashCode() {
        return Arrays.hashCode( stateArray );
    }

    public String toString() {
        return Arrays.toString( stateArray );
    }
}