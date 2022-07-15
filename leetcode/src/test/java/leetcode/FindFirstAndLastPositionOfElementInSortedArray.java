package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.
 * <p>
 * If target is not found in the array, return [-1, -1].
 * <p>
 * You must write an algorithm with O(log n) runtime complexity.
 */
public class FindFirstAndLastPositionOfElementInSortedArray {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{5, 7, 7, 8, 8, 10}, 8, new int[]{3, 4}},
                {new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1}, 1, new int[]{0, 8}},
                {new int[]{5, 7, 7, 8, 8, 10}, 6, new int[]{-1, -1}},
                {new int[0], 0, new int[]{-1, -1}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int target, int[] expected) {
        assertEquals(searchRange(nums, target), expected);
    }

    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        searchRecursive(nums, target, result, 0, nums.length - 1);
        return result;
    }

    public void searchRecursive(int[] nums, int target, int[] result, int from, int to) {
        while (from <= to) {
            int pivotIndex = (from + to) / 2;
            int pivot = nums[pivotIndex];
            if (pivot == target) {
                boolean searchToTheLeft = result[0] == -1 || pivotIndex < result[0];
                boolean searchToTheRight = pivotIndex > result[1];
                // update min & max before recurse call to minimize redundant calls
                if (searchToTheLeft) {
                    result[0] = pivotIndex;
                }
                result[1] = Math.max(result[1], pivotIndex);
                if (searchToTheLeft) {
                    searchRecursive(nums, target, result, from, pivotIndex - 1);
                }
                if (searchToTheRight) {
                    searchRecursive(nums, target, result, pivotIndex + 1, to);
                }
                break;
            } else if (pivot > target) {
                to = pivotIndex - 1;
            } else {
                from = pivotIndex + 1;
            }
        }
    }
}


