/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an array of non-negative integers nums, you are initially positioned at the first index of the array.
 * Each element in the array represents your maximum jump length at that position.
 * Your goal is to reach the last index in the minimum number of jumps.
 * You can assume that you can always reach the last index.
 */
public class JumpGameII {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{2, 3, 1, 1, 4}, 2},
                {new int[]{2, 3, 0, 1, 4}, 2},
                {new int[]{0}, 0},
                {new int[]{1,1,5,1,1,1,1,1,1}, 4},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int expected) {
        assertEquals(jump(nums), expected);
    }

public int jump(int[] nums) {
    int indexWeAreCurrentlyAt = 0;
    int reach = 0;
    int maxReach = 0;
    int steps = 0;
    while (indexWeAreCurrentlyAt < nums.length - 1) {
        maxReach = Math.max(maxReach, nums[indexWeAreCurrentlyAt]);
        if (reach == 0) {
            reach = maxReach;
            steps++;
        }
        maxReach--;
        reach--;
        indexWeAreCurrentlyAt++;
    }
    return steps;
}
}
