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

public class ReverseToMakeEqual {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 2, 3, 4}, new int[]{1, 4, 3, 2}, true},
                {new int[]{1, 2, 3, 4}, new int[]{1, 4, 3, 3}, false},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] array_a, int[] array_b, boolean expected) {
        assertEquals(areTheyEqual(array_a, array_b), expected);
    }


    boolean areTheyEqual(int[] array_a, int[] array_b) {
        if (array_a.length != array_b.length) {
            return false;
        }
        Map<Integer, Integer> map = new HashMap<>(array_a.length);
        for (int i = 0; i < array_a.length; i++) {
            map.merge(array_a[i], 1, Integer::sum);
        }
        for (int i = 0; i < array_b.length; i++) {
            Integer count = map.get(array_b[i]);
            // exit early
            if (count == null || count == 0) {
                return false;
            }
            map.put(array_b[i], count - 1);
        }
        return map.values().stream().allMatch(count -> count == 0);
    }
}
