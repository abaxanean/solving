/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package oracle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an array of meeting time `meetings` where `meetings[i] = [startTime, endTime]`
 * 1. determine if a person can attend all meetings
 * 2. determine the minimum number of conference rooms required.
 */
public class Meetings {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[][]{{10, 15}, {5, 10}, {0, 5}}, 1},
                {new int[][]{{10, 15}, {5, 11}, {0, 5}}, 2},
                {new int[][]{{10, 15}, {5, 10}, {0, 16}}, 2},
                {new int[][]{{10, 15}, {5, 11}, {0, 16}}, 3},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[][] meetings, int expected) {
        assertEquals(minMeetingRooms(meetings), expected);
        assertEquals(canAttendAllMeetings(meetings), expected == 1);
    }



    public static int minMeetingRooms(int[][] meetings) {
        List<Meeting> meetingsList = new ArrayList<>();
        for (int i = 0; i < meetings.length; i++) {
            meetingsList.add(new Meeting(meetings[i][0], true));
            meetingsList.add(new Meeting(meetings[i][1], false));
        }
        meetingsList.sort((m1, m2) -> {
            if (m1.time != m2.time) {
                return m1.time - m2.time;
            }
            if (m1.isStartTime == m2.isStartTime) {
                return 0;
            }
            return m1.isStartTime ? 1 : -1;
        });
        int maxRooms = 0;
        int currentRooms = 0;
        for (Meeting meeting : meetingsList) {
            if (meeting.isStartTime) {
                currentRooms++;
                maxRooms = Math.max(maxRooms, currentRooms);
            } else {
                currentRooms--;
            }
        }
        return maxRooms;
    }

    public static boolean canAttendAllMeetings(int[][] meetings) {
        Arrays.sort(meetings, (m1, m2) -> m1[0] - m2[0]);
        for (int i = 0; i < meetings.length - 1; i++) {
            if (meetings[i][1] > meetings[i+1][0]) {
                return false;
            }
        }
        return true;
    }

    static class Meeting {
        int time;
        boolean isStartTime;
        Meeting(int time, boolean isStartTime) {
            this.time = time;
            this.isStartTime = isStartTime;
        }

    }


}



// Question: Given an array of meeting time `meetings` where `meetings[i] = [startTime, endTime]`,
// please determine the minimum number of conference rooms required.
