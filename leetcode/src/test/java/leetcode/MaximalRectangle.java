/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
 */
public class MaximalRectangle {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new char[][]{
                        {'1', '0', '1', '0', '0'},
                        {'1', '0', '1', '1', '1'},
                        {'1', '1', '1', '1', '1'},
                        {'1', '0', '0', '1', '0'}}, 6},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(char[][] nums, int expected) {
        assertEquals(maximalRectangle2(nums), expected);
    }

    public int maximalRectangle(final char[][] nums) {
        int rows = nums.length;
        int columns = nums[0].length;
        int[] dp = new int[columns];
        int max = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (nums[i][j] == '1') {
                    dp[j]++;
                } else {
                    dp[j] = 0;
                }
            }
            max = Math.max(max, largestRectangleArea(dp));
        }
        return max;
    }


    private int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] indices = new int[n];
        int[] values = new int[n];
        int head = -1;

        int max = 0;
        for (int i = 0; i < n; ++i) {
            // 1-based index of the previous element less than current, zero if none
            int index = i;
            while (head >= 0 && heights[i] < values[head]) {
                max = Math.max(max, (i - indices[head]) * values[head]);
                index = indices[head];
                head--;
            }

            if (head < 0 || heights[i] > values[head]) {
                head++;
                indices[head] = index;
                values[head] = heights[i];
            }
        }

        while (head >= 0) {
            max = Math.max(max, (n - indices[head]) * values[head]);
            head--;
        }

        return max;
    }

    // copied from LC

    private char[][] matrix;
    private int R;
    private int C;
    private int[] left;  // tricky: cache gets updated on the fly of each row
    private int[] right;
    private int[] height;

    public int maximalRectangle2(char[][] matrix) {
        this.matrix = matrix;
        R = matrix.length;
        C = matrix[0].length;
        left = new int[C];
        right = new int[C];
        height = new int[C];
        Arrays.fill(right, C);

        int max = 0;
        for (int i = 0; i < R; i++)
            max = Math.max(max, dp(i));

        return max;
    }

    private int dp(int row) {
        int currentLeft = 0;
        int currentRight = C;

        // update height cache
        for (int i = 0; i < C; i++) {
            if (matrix[row][i] == '1')
                height[i]++;
            else
                height[i] = 0;
        }

        // update left cache
        for (int i = 0; i < C; i++) {
            if (matrix[row][i] == '1')
                left[i] = Math.max(left[i], currentLeft);
            else {
                left[i] = 0;
                currentLeft = i+1;
            }
        }

        // update right cache
        for (int i = C-1; i >= 0; i--) {
            if (matrix[row][i] == '1')
                right[i] = Math.min(right[i], currentRight);
            else {
                right[i] = C;
                currentRight = i;
            }
        }

        // update area
        int max = 0;
        for (int i = 0; i < C; i++)
            max = Math.max(max, (right[i]-left[i])*height[i]);
        return max;
    }
}
