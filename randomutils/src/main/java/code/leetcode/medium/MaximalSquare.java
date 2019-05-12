package code.leetcode.medium;

public class MaximalSquare {
    public static void main(String[] args) {
        //[["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
        MaximalSquare maximalSquare = new MaximalSquare();
        char[][] arr = {{'1','0','1','0','0'}, {'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
        System.out.println(maximalSquare.maximalSquare(arr));
    }

    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix[0].length == 0) {
            return 0;
        }
        int rows = matrix.length;
        int colums = matrix[0].length;
        int[][] dp = new int[rows][colums];

        int max = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) { //Initialize part
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                    }
                    if (dp[i][j] > max) {
                        max = dp[i][j];
                    }
                }
            }
        }

        return max * max;
    }

}
