/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it can trap after raining.
 */
public class TrappingRainWater {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, 6},
                {new int[]{4, 2, 0, 3, 2, 5}, 9},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] height, int expected) {
        assertEquals(trap(height), expected);
    }

    public int trap(int[] height) {
        int[] highestToTheLeft = new int[height.length];
        int[] highestToTheRight = new int[height.length];
        int max = height[0];
        for (int i = 1; i < height.length - 1; i++) {
            highestToTheLeft[i] = max;
            max = Math.max(height[i], max);
        }
        max = height[height.length - 1];
        for (int i = height.length - 2; i > 0; i--) {
            highestToTheRight[i] = max;
            max = Math.max(height[i], max);
        }
        int result = 0;
        for (int i = 1; i < height.length - 1; i++) {
            int maxHeightOnBothSides = Math.min(highestToTheLeft[i], highestToTheRight[i]);
            if (maxHeightOnBothSides > height[i]) {
                result += maxHeightOnBothSides - height[i];
            }
        }
        return result;
    }
}
