/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.
 */
public class LargestRectangleInHistogram {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{2, 1, 5, 6, 2, 3}, 10},
                {new int[]{2, 4}, 4},
                {new int[]{1, 1}, 2},
                {new int[]{0, 1, 0, 1}, 1},
                {new int[]{5, 4, 1, 2}, 8},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] heights, int expected) {
        assertEquals(largestRectangleArea5(heights), expected);
    }

    public int largestRectangleArea5(int[] heights) {
        int n = heights.length;
        int[] stack = new int[n];
        int stackIndex = -1;
        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            while (stackIndex >= 0 && heights[i] < heights[stack[stackIndex]]) {
                int height = heights[stack[stackIndex--]];
                int width = stackIndex == -1 ? i : i - 1 - stack[stackIndex];
                maxArea = Math.max(maxArea, width * height);
            }
            stack[++stackIndex] = i;
        }
        while (stackIndex >= 0) {
            int height = heights[stack[stackIndex--]];
            int width = stackIndex == -1 ? n : n - 1 - stack[stackIndex];
            maxArea = Math.max(maxArea, height * width);
        }
        return maxArea;
    }

    public int largestRectangleArea3(int[] heights) {
        // stack of index- value pairs, the stack will stay sorted in descendent order
        int indexInStack = -1;
        int[] indices = new int[heights.length];
        int[] values = new int[heights.length];
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            int index = i;
            int number = heights[i];
            while (indexInStack >= 0 && values[indexInStack] > number) {
                maxArea = Math.max(maxArea, (i - indices[indexInStack]) * values[indexInStack]);
                index = indices[indexInStack];
                indexInStack--;
            }

            if (indexInStack < 0 || values[indexInStack] < number) {
                indexInStack++;
                indices[indexInStack] = index;
                values[indexInStack] = heights[i];
            }

        }
        while (indexInStack >= 0) {
            maxArea = Math.max(maxArea, (heights.length - indices[indexInStack]) * values[indexInStack]);
            indexInStack--;
        }
        return maxArea;
    }

    public int largestRectangleArea4(int[] heights) {
        // stack of index- value pairs, the stack will stay sorted in descendent order
        ArrayDeque<Pair> stack = new ArrayDeque<>();
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            int index = i;
            int number = heights[i];
            while (!stack.isEmpty() && number < stack.peek().value) {
                maxArea = Math.max(maxArea, (i - stack.peek().index) * stack.peek().value);
                index = stack.pop().index;
                ;
            }

            if (stack.isEmpty() || number > stack.peek().value) {
                stack.push(new Pair(index, heights[i]));
            }

        }
        while (!stack.isEmpty()) {
            maxArea = Math.max(maxArea, (heights.length - stack.peek().index) * stack.pop().value);
        }
        return maxArea;
    }

    static class Pair {
        // index of the first item to the left that is less than this number - 1 indexed
        int index;
        int value;

        public Pair(final int index, final int value) {
            this.index = index;
            this.value = value;
        }
    }


    public int largestRectangleArea(int[] heights) {
        // the index of the first element to the left/right smaller that current one
        int[] smallestToTheLeft = new int[heights.length];
        int[] smallestToTheRight = new int[heights.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < heights.length; i++) {
            int height = heights[i];
            while (!stack.isEmpty() && heights[stack.peek()] >= height) {
                stack.pop();
            }
            smallestToTheLeft[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();
        for (int i = heights.length - 1; i >= 0; i--) {
            int height = heights[i];
            while (!stack.isEmpty() && heights[stack.peek()] >= height) {
                stack.pop();
            }
            smallestToTheRight[i] = stack.isEmpty() ? heights.length : stack.peek();
            stack.push(i);
        }

        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            max = Math.max(max, (smallestToTheRight[i] - smallestToTheLeft[i] - 1) * heights[i]);
        }
        return max;
    }

    public int largestRectangleArea2(int[] h) {

        int n = h.length;
        int p = -1;

        int[] x = new int[n];
        int[] y = new int[n];

        int area = 0;
        for (int i = 0; i < n; ++i) {
            int xval = i;
            while (p >= 0 && h[i] < y[p]) {
                area = Math.max(area, (i - x[p]) * y[p]);
                xval = x[p];
                p--;
            }

            if (p < 0 || h[i] > y[p]) {
                p++;
                x[p] = xval;
                y[p] = h[i];
            }
        }

        while (p >= 0) {
            area = Math.max(area, (n - x[p]) * y[p]);
            p--;
        }

        return area;

    }
}
