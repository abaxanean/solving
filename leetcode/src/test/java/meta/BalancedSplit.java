/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an array of integers (which may include repeated integers), determine if there's a way to split the array into two subsequences A and B such that the sum of the integers in both arrays is the same, and all of the integers in A are strictly smaller than all of the integers in B.
 * Note: Strictly smaller denotes that every integer in A must be less than, and not equal to, every integer in B.
 */
public class BalancedSplit {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 5, 7, 1}, true},
                {new int[]{12, 7, 6, 7, 6}, false},
                {new int[]{2, 1, 2, 5}, true},
                {new int[]{3, 6, 3, 4, 4}, false},
                {new int[]{1}, false},
                {new int[]{1,1}, false},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, boolean expected) {
        assertEquals(balancedSplitExists(arr), expected);
    }


    boolean balancedSplitExists(int[] arr) {
        Arrays.sort(arr);
        int sum1 = 0;
        int sum2 = Arrays.stream(arr).sum();
        for (int i = 0; i < arr.length - 1; i++) {
            sum2 -= arr[i];
            sum1 += arr[i];
            if (arr[i+1] > arr[i] && sum1 == sum2) {
                return true;
            }
        }
        return false;
    }

}
