/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * There is an integer array nums sorted in non-decreasing order (not necessarily with distinct values).
 *
 * Before being passed to your function, nums is rotated at an unknown pivot index k (0 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,4,4,5,6,6,7] might be rotated at pivot index 5 and become [4,5,6,6,7,0,1,2,4,4].
 *
 * Given the array nums after the rotation and an integer target, return true if target is in nums, or false if it is not in nums.
 *
 * You must decrease the overall operation steps as much as possible.
 */
public class SearchInRotatedSortedArrayII {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{2,5,6,0,0,1,2}, 0, true},
                {new int[]{2,5,6,0,0,1,2}, 3, false},
                {new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,1,1,1}, 2, true},

        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int target, boolean expected) {
        assertEquals(search(nums, target), expected);
    }

    public boolean search(int[] nums, int target) {
        return search(nums, target, 0, nums.length - 1);
    }

    public boolean search(int[] nums, int target, int from, int to) {
        while (from <= to) {
            int pivotIndex = (from + to) / 2;
            int pivot = nums[pivotIndex];
            if (pivot == target) {
                return true;
            }

            if (nums[from] == nums[to] && nums[from] == pivot) {
                return search(nums, target, from, pivotIndex - 1)
                        || search(nums, target, pivotIndex + 1, to);
            }

            if (target > pivot) {
                if (pivot < nums[from] && target > nums[to]) {
                    to = pivotIndex - 1;
                } else {
                    from = pivotIndex + 1;
                }
            } else {
                if (pivot > nums[to] && target < nums[from]) {
                    from = pivotIndex + 1;
                } else {
                    to = pivotIndex - 1;
                }
            }
        }
        return false;
    }
}
