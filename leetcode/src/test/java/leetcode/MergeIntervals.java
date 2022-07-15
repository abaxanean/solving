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
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
 */
public class MergeIntervals {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[][]{{1,3},{2,6},{8,10},{15,18}}, new int[][]{{1,6}, {8,10}, {15,18}}},
                {new int[][]{{1,4},{4,5}}, new int[][]{{1,5}}},
                {new int[][]{{1,4}}, new int[][]{{1,4}}},
                {new int[][]{{1,4}, {0,4}}, new int[][]{{0,4}}},
                {new int[][]{{1,4}, {2,3}}, new int[][]{{1,4}}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[][] intervals, int[][] expected) {
        int[][] result = merge(intervals);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(result[i], expected[i]);
        }
    }


    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> result = new ArrayList<>();
        int[] currentInterval = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if (currentInterval[1] >= interval[0]) {
                // merge
                currentInterval[1] = Math.max(interval[1], currentInterval[1]);
            } else {
                result.add(currentInterval);
                currentInterval = interval;
            }
        }
        result.add(currentInterval);
        return result.toArray(new int[result.size()][2]);
    }

}
