/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an integer array nums sorted in non-decreasing order, remove some duplicates in-place such that each unique element appears at most twice. The relative order of the elements should be kept the same.
 * <p>
 * Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the first part of the array nums. More formally, if there are k elements after removing the duplicates, then the first k elements of nums should hold the final result. It does not matter what you leave beyond the first k elements.
 * <p>
 * Return k after placing the final result in the first k slots of nums.
 * <p>
 * Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory.
 */
public class RemoveDuplicatesFromSortedArrayII {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1,1,1,2,2,3}, 5, new int[]{1,1,2,2,3, -1}},
                {new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3}, 7, new int[]{0, 0, 1, 1, 2, 3, 3, -1, -1}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int expectedLength, int[] expectedNums) {
        int k = removeDuplicates(nums);
        assertEquals(k, expectedLength);
        for (int i = 0; i < k; i++) {
            assertEquals(nums[i], expectedNums[i]);
        }
    }

    public int removeDuplicates(int[] nums) {
        int writeIndex=2;
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] == nums[writeIndex - 2]) {
                continue;
            }
            nums[writeIndex++] = nums[i];
        }
        return writeIndex;
    }

}
