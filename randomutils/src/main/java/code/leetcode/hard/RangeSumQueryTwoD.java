package code.leetcode.hard;

/**
 * Beauty to store another metric which is same as original metric
 * In that sumMetric[i][j] is sum of elements from 0th column to jth column in ith row.
 * It help to calculate the final sum. See the sumRegion method
 */
public class RangeSumQueryTwoD {
    int[][] matrix;
    int[][] sumMetrics;

    //Initilization is O(M*N)
    public RangeSumQueryTwoD(int[][] matrix) {
        this.matrix = matrix;
        sumMetrics = new int[matrix.length][matrix[0].length];

        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(j==0){
                    sumMetrics[i][j] = matrix[i][j];
                } else {
                    sumMetrics[i][j] = matrix[i][j] + sumMetrics[i][j-1];
                }
            }
        }
    }

    //Worst case is O(NumberOfColumns)
    public void update(int row, int col, int val) {
        int diff = val - matrix[row][col];
        for(int i=col;i<matrix[0].length;i++){
            sumMetrics[row][i] +=diff;
        }
        matrix[row][col] = val;
    }

    //Worst case is O(NumberOfRows)
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int output =0;
        for(int i=row1;i<=row2;i++){
            if(col1 == 0){
                output += sumMetrics[i][col2];
            } else {
                output += sumMetrics[i][col2] - sumMetrics[i][col1-1];
            }
        }
        return output;
    }


    public void wallsAndGates(int[][] rooms) {
        if(rooms ==null || rooms.length==0){
            return;
        }
        int rows  = rooms.length;
        int colums = rooms[0].length;
        for(int i=0;i<rows;i++){
            for(int j=0;j<colums;j++){
                if(rooms[i][j] ==0){
                    wallsAndGates(rooms, rows, colums, i, j, 0);
                }
            }
        }
    }

    public void wallsAndGates(int[][] rooms, int rows, int colums, int i, int j, int value) {
        if(i<0 || i>=rows || j<0 || j>=colums || rooms[i][j] == -1){
            return;
        }
        if(rooms[i][j] < value){
            rooms[i][j] = value;
            wallsAndGates(rooms, rows, colums, i+1, j, value+1);
            wallsAndGates(rooms, rows, colums, i, j+1, value+1);
            wallsAndGates(rooms, rows, colums, i-1, j, value+1);
            wallsAndGates(rooms, rows, colums, i, j-1, value+1);
        }
    }



}
