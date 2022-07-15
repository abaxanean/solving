/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi] represent the start and the end of the ith interval and intervals is sorted in ascending order by starti. You are also given an interval newInterval = [start, end] that represents the start and end of another interval.
 * Insert newInterval into intervals such that intervals is still sorted in ascending order by starti and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).
 * Return intervals after the insertion.
 */
public class InsertInterval {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[][]{{1, 3}, {6, 9}}, new int[]{2, 5}, new int[][]{{1, 5}, {6, 9}}},
                {new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8}, new int[][]{{1, 2}, {3, 10}, {12, 16}}},
                {new int[][]{{1, 5}}, new int[]{2, 3}, new int[][]{{1, 5}}},
                {new int[][]{{1, 5}}, new int[]{0, 0}, new int[][]{{0, 0}, {1, 5}}},
                {new int[][]{{2, 5}, {6, 7}, {8, 9}}, new int[]{0, 1}, new int[][]{{0, 1}, {2, 5}, {6, 7}, {8, 9}}},
                {new int[][]{{2, 4}, {5, 7}, {8, 10}, {11, 13}}, new int[]{3, 6}, new int[][]{{2, 7}, {8, 10}, {11, 13}}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[][] intervals, int[] newInterval, int[][] expected) {
        int[][] result = insert(intervals, newInterval);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(result[i], expected[i]);
        }
    }

public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>(intervals.length);
    int index = 0;
    // add to result all intervals that end before the new interval
    while (index < intervals.length && intervals[index][1] < newInterval[0]) {
        result.add(intervals[index++]);
    }
    // if all intervals end before the new interval or the new interval ends before the interval at index
    if (index == intervals.length || newInterval[1] < intervals[index][0]) {
        result.add(newInterval);
    } else {
        // arriving here means we have to merge
        newInterval[0] = Math.min(newInterval[0], intervals[index][0]);
        while (index < intervals.length && newInterval[1] >= intervals[index][0]) {
            newInterval[1] = Math.max(newInterval[1], intervals[index++][1]);
        }
        result.add(newInterval);
    }
    // add remaining intervals
    while (index < intervals.length) {
        result.add(intervals[index++]);
    }
    return result.toArray(new int[result.size()][2]);
}

    int findIndex(int[][] intervals, int value) {
        int start = 0;
        int end = intervals.length;
        while (start < end) {
            int pivot = (start + end) / 2;
            int[] interval = intervals[pivot];
            if (interval[0] == value) {
                return pivot;
            }
            if (interval[pivot] < value) {
                start = pivot + 1;
            } else {
                end = pivot - 1;
            }
        }
        return end;
    }

}
