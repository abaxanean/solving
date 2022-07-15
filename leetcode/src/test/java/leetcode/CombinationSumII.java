/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a collection of candidate numbers (candidates) and a target number (target),
 * find all unique combinations in candidates where the candidate numbers sum to target.
 * Each number in candidates may only be used once in the combination.
 * Note: The solution set must not contain duplicate combinations.
 */
public class CombinationSumII {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{10,1,2,7,6,1,5}, 8, Arrays.asList(Arrays.asList(1, 1, 6), Arrays.asList(1, 2, 5), Arrays.asList(1, 7), Arrays.asList(2, 6))},
                {new int[]{2,5,2,1,2}, 5, Arrays.asList(Arrays.asList(1, 2, 2), Collections.singletonList(5))},
                {new int[]{3,1,3,5,1,1}, 8, Arrays.asList(Arrays.asList(1, 1, 1, 5), Arrays.asList(1, 1, 3, 3), Arrays.asList(3, 5))},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] candidates, int target, List<List<Integer>> expected) {
        assertEquals(combinationSum2(candidates, target), expected);
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSumRecursion(candidates, target, 0, 0, new ArrayDeque<>(), result);
        return result;
    }

    private void combinationSumRecursion(int[] candidates, int target, int start, int sum, Deque<Integer> queue, List<List<Integer>> result) {
        if (sum == target) {
            result.add(new ArrayList<>(queue));
            return;
        }
        if (start == candidates.length || sum > target) {
            return;
        }
        queue.addLast(candidates[start]);
        combinationSumRecursion(candidates, target, start + 1, sum + candidates[start], queue, result);
        queue.removeLast();
        while (start < candidates.length - 1 && candidates[start] == candidates[start + 1]) {
            start++;
        }
        combinationSumRecursion(candidates, target, start + 1, sum, queue, result);
    }

}
