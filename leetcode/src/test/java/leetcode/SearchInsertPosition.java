package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a sorted array of distinct integers and a target value, return the index if the target is found.
 * If not, return the index where it would be if it were inserted in order.
 * <p>
 * You must write an algorithm with O(log n) runtime complexity.
 */
public class SearchInsertPosition {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 3, 5, 6}, 5, 2},
                {new int[]{1, 3, 5, 6}, 2, 1},
                {new int[]{1, 3, 5, 6}, 7, 4},
                {new int[]{1, 3, 5, 6}, 0, 0},
                {new int[]{1}, 0, 0},
                {new int[]{1, 2, 3, 4}, 5, 4},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int target, int expected) {
        assertEquals(searchInsert(nums, target), expected);
    }

    public int searchInsert(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int pivotIndex = (start + end) / 2;
            int pivot = nums[pivotIndex];
            if (pivot == target) {
                return pivotIndex;
            }
            if (pivot > target) {
                end = pivotIndex - 1;
            } else {
                start = pivotIndex + 1;
            }
        }
        return start;
    }

}
