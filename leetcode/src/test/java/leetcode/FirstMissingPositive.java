/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an unsorted integer array nums, return the smallest missing positive integer.
 * You must implement an algorithm that runs in O(n) time and uses constant extra space.
 */
public class FirstMissingPositive {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 2, 0}, 3},
                {new int[]{2, 1, 0}, 3},
                {new int[]{3, 4, -1, 1}, 2,},
                {new int[]{7, 8, 9, 11, 12}, 1},
                {new int[]{1}, 2},
                {new int[]{1, 1}, 2},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int expected) {
        assertEquals(firstMissingPositive(nums), expected);
    }


    public int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            // swap numbers in the array so that every positive nums[i], i = 1...n, is put in position nums[i] - 1
            // numbers < 1 or > nums.length are ignored
            // number != nums[number - 1] checks whether we are not swapping the number with itself or a duplicate
            int number = nums[i];
            while (number > 0 && number <= nums.length && number != nums[number - 1]) {
                // swap
                nums[i] = nums[number - 1];
                nums[number - 1] = number;
                number = nums[i];
            }
        }
        // after all numbers are 'in place' find the first one missing
        int i = 1;
        while (i <= nums.length && nums[i - 1] == i) {
            i++;
        }
        return i;
    }
}
