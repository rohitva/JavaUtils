package code.leetcode.medium2;

/**
 * Thing about diff and flaten the diff 2 times, such that for gas.length, sum >0
 */
public class GasStation {
    public static void main(String[] args){

    }
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int dist = gas.length * 2;
        int[] diffArray = new int[dist];
        int index = 0;
        for(int i=0;i<2;i++){
            for(int j=0;j<gas.length;j++){
                diffArray[index++] = gas[j] - cost[j];
            }
        }

        int currentSum =0, windowStart =0, windowLength = 0;
        for(int i=0;i<dist;i++){
            currentSum += diffArray[i];
            if(currentSum < 0){
                currentSum = 0;
                windowStart = i+1;
                windowLength = 0;
            } else {
                windowLength++;
                if(windowLength == gas.length){
                    return windowStart % gas.length;
                }
            }
        }
        return -1;
    }

}
