package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * There is an integer array nums sorted in ascending order (with distinct values).
 * <p>
 * Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
 * <p>
 * Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums, or -1 if it is not in nums.
 * <p>
 * You must write an algorithm with O(log n) runtime complexity.
 */
public class SearchInRotatedSortedArray {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{4, 5, 6, 7, 0, 1, 2}, 0, 4},
                {new int[]{4, 5, 6, 7, 0, 1, 2}, 3, -1},
                {new int[]{1}, 0, -1},
                {new int[]{1}, 2, -1},
                {new int[]{1, 3}, 3, 1},
                {new int[]{3, 5, 1}, 3, 0},
                {new int[]{4, 5, 6, 7, 8, 1, 2, 3}, 8, 4},
                {new int[]{5, 1, 3}, 3, 2},
                {new int[]{3, 1}, 1, 1},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int target, int expected) {
        assertEquals(search(nums, target), expected);
    }

    public int search(int[] nums, int target) {
        int from = 0;
        int to = nums.length - 1;
        while (from <= to) {
            int pivotIndex = (from + to) / 2;
            int pivot = nums[pivotIndex];
            if (pivot == target) {
                return pivotIndex;
            }

            if (target > pivot) {
                if (pivot < nums[from] && target > nums[to]) {
                    to = pivotIndex - 1;
                } else {
                    from = pivotIndex + 1;
                }
            } else {
                if (pivot > nums[to] && target < nums[from]) {
                    from = pivotIndex + 1;
                } else {
                    to = pivotIndex - 1;
                }
            }
        }
        return -1;
    }

//    int findRotationPoint(int[] nums) {
//        if (nums[0] < nums[nums.length - 1]) {
//            return 0;
//        }
//
//    }

//    private int rotatedBinarySearch(final int[] nums, final int target, final int from, final int to) {
//        boolean rotated = nums[from] >= nums[to];
//        int pivot = (from + to) / 2;
//        if (nums[pivot])
//    }
}
