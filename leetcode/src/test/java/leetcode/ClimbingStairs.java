/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 */
public class ClimbingStairs {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {2, 2},
                {3, 3},

        };
    }

    @Test(dataProvider = "testCases")
    public void test(int n, int expected) {
        assertEquals(climbStairs(n), expected);
    }

public int climbStairs(int n) {
    // the number of distinct ways we can reach the current step climbing 2 steps
    int climb2 = 1;
    // the number of distinct ways we can reach the current step climbing 1 step
    int climb1 = 1;
    int current = 1;
    for (int i = 1; i < n; i++) {
        current = climb1 + climb2;
        climb2 = climb1;
        climb1 = current;
    }
    return current;
}
}
