/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * The set [1, 2, 3, ..., n] contains a total of n! unique permutations.
 * <p>
 * By listing and labeling all of the permutations in order, we get the following sequence for n = 3:
 * <p>
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * Given n and k, return the kth permutation sequence.
 */
public class PermutationSequence {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {2, 1, "12"},
                {3, 3, "213"},
                {3, 4, "231"},
                {4, 9, "2314"},
                {4, 10, "2341"},
                {3, 5, "312"},
                {4, 11, "2413"},
                {4, 12, "2431"},
                {3, 1, "123"},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int n, int k, String expected) {
        assertEquals(getPermutation(n, k), expected);
    }

    public String getPermutation(int n, int k) {
        StringBuilder result = new StringBuilder();
        // add numbers 1 to N to a list
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            numbers.add(i + 1);
        }
        while (n > 0) {
            int factorial = FACTORIALS[--n];
            int index = (k - 1) / factorial;
            result.append(numbers.remove(index));
            k -= factorial * index;
        }
        return result.toString();
    }

    public static final int[] FACTORIALS = { 1, 1, 2, 6, 24, 120, 720, 5040, 40320 };

}
