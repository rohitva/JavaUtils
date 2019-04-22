package code.leetcode.hard;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class SkylineProblem {
    public static void main(String[] args) {
        SkylineProblem skylineProblem = new SkylineProblem();
        int[][] intput = {{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}};
        List<int[]> output = skylineProblem.getSkyline(intput);
        for(int i=0;i < output.size();i++){
            System.out.println(output.get(i)[0] + " " + output.get(i)[1]);
        }
    }

    @ToString
    public class BuildingPoint {
        public boolean isStart;
        public int x, height;

        public BuildingPoint(boolean isStart, int x, int height) {
            this.isStart = isStart;
            this.x = x;
            this.height = height;
        }
    }

    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> outPut = new ArrayList<>();
        if (buildings == null || buildings.length == 0) {
            return outPut;
        }
        List<BuildingPoint> buildingPoints = new ArrayList<>();
        for (int i = 0; i < buildings.length; i++) {
            buildingPoints.add(new BuildingPoint(true, buildings[i][0], buildings[i][2])); //start edge
            buildingPoints.add(new BuildingPoint(false, buildings[i][1], buildings[i][2])); //end edge
        }
        sortBuilding(buildingPoints);
        System.out.println(buildingPoints);
        PriorityQueue<Integer> heightPriority = new PriorityQueue<>(Collections.reverseOrder());
        heightPriority.add(0);

        /**
         * In case of start add, in case of end remove and if max height change then add that point with current maxHeight
         */
        for(int i=0;i<buildingPoints.size();i++){
            BuildingPoint buildingPoint = buildingPoints.get(i);
            int currentMax = heightPriority.peek();
            if(buildingPoints.get(i).isStart) {
                heightPriority.add(buildingPoints.get(i).height);
            } else {
                heightPriority.remove(buildingPoints.get(i).height);
            }
            if(heightPriority.peek() != currentMax){
                outPut.add(new int[] {buildingPoint.x, heightPriority.peek()});
            }
        }
        return outPut;
    }

    /**
     * We need to iterate points from left to right so if they have different x then compare based on X. Otherwise there are 3 different cases
     * [1] If both building are starting we need to pick the one with bigger height.
     * [2] If both building are ending then we need to pick one with lower height so we can take low end-point.
     * [3] Otherwise take the building which is starting.
     *
     * @param buildingPoints
     */
    private void sortBuilding(List<BuildingPoint> buildingPoints) {
        buildingPoints.sort((firstBuilding, secondBuilding) -> {
            if (firstBuilding.x != secondBuilding.x) {
                return Integer.compare(firstBuilding.x, secondBuilding.x);
            } else {
                if (firstBuilding.isStart && secondBuilding.isStart) {
                    return Integer.compare(secondBuilding.height, firstBuilding.height);
                } else if (!firstBuilding.isStart && !secondBuilding.isStart) {
                    return Integer.compare(firstBuilding.height, secondBuilding.height);
                } else {
                    if (firstBuilding.isStart) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });
    }

}
