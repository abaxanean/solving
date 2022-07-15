/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SlowSums {


    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{4, 2, 1, 3}, 26},
                {new int[]{2, 3, 9, 8, 4}, 88},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int expected) {
        assertEquals(getTotalTime(arr), expected);
    }



    int getTotalTime(int[] arr) {
        Arrays.sort(arr);
        int penalty = arr[arr.length - 1];
        int result = 0;
        for (int i = arr.length - 2; i >= 0; i--) {
            penalty += arr[i];
            result += penalty;
        }
        return result;
    }

}
