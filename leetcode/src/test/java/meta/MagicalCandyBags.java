/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MagicalCandyBags {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{2, 1, 7, 4, 2}, 3, 14},
                {new int[]{19, 78, 76, 72, 48, 8, 24, 74, 29}, 3, 228},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int k, int expected) {
        assertEquals(maxCandies(arr, k), expected);
    }


    int maxCandies(int[] arr, int k) {
        final PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.<Integer>naturalOrder().reversed());
        for (int i = 0; i < arr.length; i++) {
            heap.add(arr[i]);
        }
        int result = 0;
        for (int i = 0; i < k; i++) {
            int biggestCandy = heap.poll();
            result += biggestCandy;
            heap.add(biggestCandy / 2);

        }
        return result;
    }
}
