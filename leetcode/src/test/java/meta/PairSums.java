/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PairSums {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 2, 3, 4, 3}, 6, 2},
                {new int[]{1, 5, 3, 3, 3}, 6, 4},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int k, int expected) {
        assertEquals(numberOfWays(arr, k), expected);
    }


    int numberOfWays(int[] arr, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int result = 0;
        for(int i = 0; i < arr.length; i++) {
            int number = arr[i];
            Integer pairCount = map.get(k - number);
            if (pairCount != null) {
                result += pairCount;
            }
            map.merge(number, 1, Integer::sum);
        }
        return result;
    }
}
