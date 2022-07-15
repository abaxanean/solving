/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a non-negative integer x, compute and return the square root of x.
 * Since the return type is an integer, the decimal digits are truncated, and only the integer part of the result is returned.
 * Note: You are not allowed to use any built-in exponent function or operator, such as pow(x, 0.5) or x ** 0.5.
 */
public class Sqrt_x_ {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {4, 2},
                {8, 2},
                {9, 3},
                {10, 3},
                {2147483647, 46340},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int x, int expected) {
        assertEquals(mySqrt(x), expected);
    }

    public int mySqrt(int x) {
        if (x < 3) {
            return 1;
        }
        return binarySearch(0, 46340, x);
    }

    int binarySearch(int min, int max, int x) {
        if (min == max) {
            return min;
        }
        int pivot = (min + max) / 2;
        int square = pivot * pivot;
        if (x == square) {
            return pivot;
        }
        if (x < square) {
            return binarySearch(min, pivot - 1, x);
        }
        int nextSquare = (pivot + 1) * (pivot + 1);
        if (nextSquare > x) {
            return pivot;
        }
        return binarySearch(pivot + 1, max, x);
    }
}
