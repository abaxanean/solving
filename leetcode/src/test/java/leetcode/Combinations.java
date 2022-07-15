package leetcode;/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given two integers n and k, return all possible combinations of k numbers out of the range [1, n].
 * <p>
 * You may return the answer in any order.
 */
public class Combinations {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {4, 2, Arrays.asList(Arrays.asList(2, 4), Arrays.asList(3, 4), Arrays.asList(2, 3), Arrays.asList(1, 2), Arrays.asList(1, 3), Arrays.asList(1, 4))},
                {1, 1, Arrays.asList(Arrays.asList(1))}
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int n, int k, List<List<Integer>> expected) {
        assertEquals(combine(n, k), expected);
    }


    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>(combinationsCount(n, k));
        long nano = System.nanoTime();
        combine(n, 0, 1, result, new Integer[k]);
        System.out.println((System.nanoTime() - nano) / 1_000_000);
        return result;
    }

    private void combine(int n, int index, int number, List<List<Integer>> result, Integer[] current) {
        if (index == current.length) {
            result.add(toList(current));
            return;
        }

        for (int i = number; i <= n; i++) {
            current[index] = i;
            combine(n, index + 1, i + 1, result, current);
        }

    }

    List<Integer> toList(Integer[] arr) {
        Integer[] result = new Integer[arr.length];
        System.arraycopy(arr, 0, result, 0, arr.length);
        return Arrays.asList(result);
    }

    int combinationsCount(int n, int k) {
        return (int)(factorial(n) / (factorial(k) * factorial(n - k)));
    }

    long factorial(long n) {
        if (n < 2) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}
