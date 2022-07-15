/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * You are given a large integer represented as an integer array digits, where each digits[i] is the ith digit of the integer. The digits are ordered from most significant to least significant in left-to-right order. The large integer does not contain any leading 0's.
 *
 * Increment the large integer by one and return the resulting array of digits.
 */
public class PlusOne {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 2, 3}, new int[]{1, 2, 4}},
                {new int[]{4, 3, 2, 1}, new int[]{4, 3, 2, 2}},
                {new int[]{9}, new int[]{1, 0}},

        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] digits, int[] expected) {
        assertEquals(plusOne(digits), expected);
    }

public int[] plusOne(int[] digits) {
    for (int i = digits.length - 1; i >= 0; i--) {
        digits[i] += 1;
        if (digits[i] < 10) {
            return digits;
        }
        digits[i] = 0;
    }
    // corner case where the number does not fit in the original array (e.g. input = 9, result = 10)
    int[] result = new int[digits.length + 1];
    result[0] = 1;
    System.arraycopy(digits, 0, result, 1, digits.length);
    return result;
}
}
