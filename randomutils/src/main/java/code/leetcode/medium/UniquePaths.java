package code.leetcode.medium;

/**
 * https://leetcode.com/submissions/detail/176267134/
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * <p>
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * <p>
 * How many possible unique paths are there?
 * <p>
 * <p>
 * Above is a 7 x 3 grid. How many possible unique paths are there?
 * <p>
 * Note: m and n will be at most 100.
 * <p>
 * Example 1:
 * <p>
 * Input: m = 3, n = 2
 * Output: 3
 * Explanation:
 * From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down
 * 2. Right -> Down -> Right
 * 3. Down -> Right -> Right
 * <p>
 * Example 2:
 * <p>
 * Input: m = 7, n = 3
 * Output: 28
 */
public class UniquePaths {
    public int uniquePaths(int m, int n) {
        if (m <= 1 || n <= 1) {
            return 1;
        }

        int[][] arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || j == 0) {
                    arr[i][j] = 1;
                } else {
                    arr[i][j] = arr[i - 1][j] + arr[i][j - 1];
                }
            }
        }
        return arr[n - 1][m - 1];
    }
}
