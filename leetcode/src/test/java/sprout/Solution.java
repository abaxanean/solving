/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package sprout;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class Solution {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {2, 4},

        };
    }

    @Test(dataProvider = "testCases")
    public void test(int n, int expected) {
        assertEquals(solution(n), expected);
    }

    private int solution(final int n) {
        return n * n;
    }
}
