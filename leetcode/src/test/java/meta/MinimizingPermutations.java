/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * In this problem, you are given an integer N, and a permutation, P of the integers from 1 to N, denoted as (a_1, a_2, ..., a_N). You want to rearrange the elements of the permutation into increasing order, repeatedly making the following operation:
 * Select a sub-portion of the permutation, (a_i, ..., a_j), and reverse its order.
 * Your goal is to compute the minimum number of such operations required to return the permutation to increasing order.
 */
public class MinimizingPermutations {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{3, 1, 2}, 2},
                {new int[]{1, 2, 5, 4, 3}, 1},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int expected) {
        assertEquals(minOperations(arr), expected);
    }


    int minOperations(int[] arr) {
        Set<Array> permutationsSet = new HashSet<>();
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(arr);
        queue.add(new int[0]);
        int permutationsCount = 0;
        int[] item;
        while (true) {
            item = queue.removeFirst();
            if (item.length == 0) {
                permutationsCount++;
                queue.add(new int[0]);
                continue;
            }
            if (!permutationsSet.add(new Array(item))) {
                continue;
            }
            if (isIncreasing(item)) {
                return permutationsCount;
            }
            for (int i = 0; i < item.length - 1; i++) {
                for (int j = i + 1; j < item.length; j++) {
                    queue.addLast(permutation(item, i, j));
                }
            }
        }
    }

    private int[] permutation(int[] item, int start, int end) {
        int[] copy = Arrays.copyOf(item, item.length);
        int temp;
        while (start < end) {
            temp = copy[start];
            copy[start] = copy[end];
            copy[end] = temp;
            start++;
            end--;
        }
        return copy;
    }

    boolean isIncreasing(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    static class Array {

        int[] arr;
        int hash;

        public Array(final int[] arr) {
            this.arr = arr;
            this.hash = Arrays.hashCode(arr);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            final Array that = (Array)o;
            return Arrays.equals(this.arr, that.arr);
        }

        @Override
        public int hashCode() {
            return this.hash;
        }
    }

}
