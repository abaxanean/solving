/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SeatingArrangements {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{5, 10, 6, 8}, 4},
                {new int[]{1, 2, 5, 3, 7}, 4},
                {new int[]{1, 2, 3, 4}, 2},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int expected) {
        assertEquals(minOverallAwkwardness(arr), expected);
    }

    int minOverallAwkwardness(int[] arr) {
        Arrays.sort(arr);
        int last = arr[arr.length - 1];
        int min = last - arr[0];
        for (int i = 1; i < arr.length - 2; i++) {
            int max = Math.max(last - arr[i], arr[i + 1] - arr[0]);
            if (max < min) {
                min = max;
            } else {
                break;
            }
        }
        return min;
    }

}
