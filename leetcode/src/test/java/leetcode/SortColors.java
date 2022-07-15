/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.
 * <p>
 * We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
 * <p>
 * You must solve this problem without using the library's sort function.
 */
public class SortColors {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{2, 0, 2, 1, 1, 0}, new int[]{0, 0, 1, 1, 2, 2}},
                {new int[]{2, 0, 1}, new int[]{0, 1, 2}},
                {new int[]{1, 0}, new int[]{0, 1}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int[] expected) {
        sortColors2(nums);
        assertEquals(nums, expected);
    }

    static int RED = 0;
    static int WHITE = 1;
    public void sortColors2(int[] nums) {
        int red = 0, redOrWhite = 0, blue = nums.length - 1;
        while (redOrWhite <= blue) {
            int num = nums[redOrWhite];
            if (num == RED) {
                swap(nums, red, redOrWhite);
                red++;
                redOrWhite++;
            } else if (num == WHITE) {
                redOrWhite++;
            } else {
                // it is blue
                swap(nums, redOrWhite, blue);
                blue--;
            }
        }
    }

    void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void sortColors(int[] nums) {
        int red = 0, white = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                red++;
            } else if (nums[i] == 1) {
                white++;
            }
        }
        for (int i = 0; i < red; i++) {
            nums[i] = 0;
        }
        for (int i = red; i < red + white; i++) {
            nums[i] = 1;
        }

        for (int i = red + white; i < nums.length; i++) {
            nums[i] = 2;
        }
    }
}
