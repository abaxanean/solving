/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 * A subarray is a contiguous part of an array.
 */
public class MaximumSubarray {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}, 6},
                {new int[]{5, 4, -1, 7, 8}, 23},
                {new int[]{1}, 1}
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int expected) {
        assertEquals(maxSubArray(nums), expected);
    }

    public int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (sum < 0) {
                // if the numbers up to this point add up to less than (equal) 0, it does not make sense to include them
                sum = 0;
            }
            sum += nums[i];
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

}
