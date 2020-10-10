public class StatePancake
{
    int[] pancakeArray;

    public StatePancake(int[] pancakeArray) {
        this.pancakeArray = pancakeArray;
    }

    //It has to be a copy of values not reference because we will
    //create many states and don't want to overwrite the same array.
    public StatePancake(StatePancake state) {
        int size = state.pancakeArray.length;
        pancakeArray = new int[size];

        for(int i=0; i<size; i++)
            pancakeArray[i] = state.pancakeArray[i];
    }

    public boolean equals(Object o) {
        return java.util.Arrays.equals( pancakeArray, ((StatePancake) o).pancakeArray );
    }

    public int hashCode() {
        return java.util.Arrays.hashCode( pancakeArray );
    }

    public String toString() {
        return java.util.Arrays.toString( pancakeArray );
    }
}