/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.
 * Return true if you can reach the last index, or false otherwise.
 */
public class JumpGame {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
//                {new int[]{2, 3, 1, 1, 4}, true},
//                {new int[]{3, 2, 1, 0, 4}, false},
                {new int[]{2, 0, 0}, true}
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, boolean expected) {
        assertEquals(canJump(nums), expected);
    }

    public boolean canJump(int[] nums) {
        int maxReach = 0;
        int lastIndex = nums.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            maxReach = Math.max(nums[i], maxReach);
            // optimization, a big enough number can spare us some time
            if (maxReach >= lastIndex - i) {
                return true;
            }
            if (maxReach-- == 0) {
                return false;
            }
        }
        return true;
    }
}
