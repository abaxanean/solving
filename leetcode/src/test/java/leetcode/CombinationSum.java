package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.
 * <p>
 * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the frequency of at least one of the chosen numbers is different.
 * <p>
 * It is guaranteed that the number of unique combinations that sum up to target is less than 150 combinations for the given input.
 */
public class CombinationSum {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{2, 3, 6, 7}, 7, Arrays.asList(Arrays.asList(2, 2, 3), Collections.singletonList(7))},
                {new int[]{2, 3, 5}, 8, Arrays.asList(Arrays.asList(2, 2, 2, 2), Arrays.asList(2, 3, 3), Arrays.asList(3, 5))},
                {new int[]{2}, 1, Collections.emptyList()},
                {new int[]{1}, 1, Collections.singletonList(1)},
                {new int[]{1}, 2, Arrays.asList(1, 1)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] candidates, int target, List<List<Integer>> expected) {
        assertEquals(combinationSum(candidates, target), expected);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        combinationSumRecursion(candidates, target, 0, 0, new ArrayDeque<>(), result);
        return result;
    }

    private void combinationSumRecursion(int[] candidates, int target, int start, int sum, Deque<Integer> queue, List<List<Integer>> result) {
        if (sum > target) {
            return;
        }
        if (sum == target) {
            result.add(new ArrayList<>(queue));
            return;
        }
        if (start == candidates.length) {
            return;
        }
        queue.addLast(candidates[start]);
        combinationSumRecursion(candidates, target, start, sum + candidates[start], queue, result);
        queue.removeLast();
        combinationSumRecursion(candidates, target, start + 1, sum, queue, result);
    }

}
