package code.leetcode.medium2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntervalRoomSolution {
    public class IntervalRoom {
        int startTime;
        int endTime;

        public IntervalRoom(int startTime, int endTime){
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    public boolean canAttendMeetings(int[][] intervals) {
        if(intervals ==null || intervals.length<=1){
            return true;
        }
        List<IntervalRoom> rooms = new ArrayList<>();
        for(int i=0;i<intervals.length;i++){
            IntervalRoom newInterval = new IntervalRoom(intervals[i][0], intervals[i][1]);
            rooms.add(newInterval);
        }
        Collections.sort(rooms, (r1, r2) -> r1.startTime - r2.startTime);
        for(int i=1;i<rooms.size();i++){
            if(rooms.get(i-1).endTime > rooms.get(i).startTime){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){

    }
}
