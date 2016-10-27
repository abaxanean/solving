/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler.util;

import java.util.Arrays;

public class Triangle {

    // cache
    int[][] maximums;

    protected int computeMaximum(String triangle) {
        String[] lines = triangle.split("\n");
        int[][] numbers = new int[lines.length][];
        maximums = new int[lines.length][];
        int i = 0;
        for (String line : lines) {
            numbers[i++] = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
            maximums[i-1] = new int[numbers[i-1].length];
        }
        return computeMaximum(numbers, 0, 0);
    }

    private int computeMaximum(int[][] numbers, int x, int y) {
        if (x >= numbers.length || y >= numbers[x].length) {
            return 0;
        }
        if (maximums[x][y] != 0) {
            return maximums[x][y];
        }
        int maxleft = computeMaximum(numbers, x + 1, y);
        int maxRight = computeMaximum(numbers, x + 1, y + 1);
        int result = Math.max(numbers[x][y] + maxleft, numbers[x][y] + maxRight);
        maximums[x][y] = result;
        return result;
    }

}
