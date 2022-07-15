/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n, representing the number of elements in nums1 and nums2 respectively.
 *
 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
 *
 * The final sorted array should not be returned by the function, but instead be stored inside the array nums1. To accommodate this, nums1 has a length of m + n, where the first m elements denote the elements that should be merged, and the last n elements are set to 0 and should be ignored. nums2 has a length of n.
 */
public class MergeSortedArray {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1,2,3,0,0,0}, 3, new int[]{2,5,6}, 3, new int[]{1,2,2,3,5,6}},
                {new int[]{1}, 1, new int[]{}, 0, new int[]{1}},
                {new int[]{0}, 0, new int[]{1}, 1, new int[]{1}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums1, int m, int[] nums2, int n, int[] expected) {
        merge(nums1, m, nums2, n);
        assertEquals(nums1, expected);
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = m + n - 1;
        m--;n--;
        while(m >= 0 && n >= 0) {
            if (nums1[m] >= nums2[n]) {
                nums1[index] = nums1[m]; m--;
            } else {
                nums1[index] = nums2[n]; n--;
            }
            index--;
        }
        for(int i = n; n >=0; n--) {
            nums1[index--] = nums2[n];
        }
        // if there are nums1 numbers left - they are already in place
    }
}
