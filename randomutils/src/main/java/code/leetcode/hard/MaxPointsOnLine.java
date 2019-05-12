package code.leetcode.hard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MaxPointsOnLine {
    public static void main(String[] args) {
        int[][] arr = {{0, 0}, {94911151, 94911150}, {94911152, 94911151}};
        MaxPointsOnLine maxPointsOnLine = new MaxPointsOnLine();
        System.out.println(maxPointsOnLine.maxPoints(arr));
    }

    public int maxPoints(int[][] points) {
        if (points == null || points.length == 0 || points[0].length == 0) {
            return 0;
        }
        if (points.length <= 2) {
            return points.length;
        }

        int max = 2;
        for (int i = 0; i < points.length - 1; i++) {
            int duplicate = 1;//
            int vertical = 0;
            Map<Double, Integer> sameSlopMap = new HashMap<>();
            for (int j = i + 1; j < points.length; j++) {
                if (duplicatePoints(points[i], points[j])) {
                    duplicate++;
                } else if (verticalPoints(points[i], points[j])) {
                    vertical++;
                } else {
                    double slope = getSlope(points[i], points[j]);
                    sameSlopMap.put(slope, sameSlopMap.getOrDefault(slope, 0) + 1);
                }
            }
            for (int counter : sameSlopMap.values()) {
                if (counter + duplicate > max) {
                    max = counter + duplicate;
                }
            }
            max = Math.max(max, duplicate + vertical);
        }

        return max;
    }

    private double getSlope(int[] firstPoint, int[] secondPoint) {
        if (firstPoint[1] == secondPoint[1]) {
            return 0.0;
        }
        double yDiff = secondPoint[1] - firstPoint[1];
        double xDiff = secondPoint[0] - firstPoint[0];
        return yDiff / xDiff;
    }

    private boolean verticalPoints(int[] firstPoint, int[] secondPoint) {
        return secondPoint[0] == firstPoint[0] && secondPoint[1] != firstPoint[1];
    }

    private boolean duplicatePoints(int[] firstPoint, int[] secondPoint) {
        return secondPoint[1] == firstPoint[1] && secondPoint[0] == firstPoint[0];
    }

}
