/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static java.util.Arrays.asList;
import static leetcode.shared.TreeNode.createTreeFromPreOrder;
import static org.testng.Assert.assertEquals;

/**
 * Given an integer array nums where the elements are sorted in ascending order, convert it to a height-balanced binary search tree.
 * <p>
 * A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.
 */
public class ConvertSortedArray_toBinarySearchTree {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{-10, -3, 0, 5, 9}, createTreeFromPreOrder(0, -3, 9, -10, null, 5)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, TreeNode expected) {
        assertEquals(sortedArrayToBST(nums), expected);
    }


    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        int rootIndex = (start + end) / 2;
        TreeNode node = new TreeNode(nums[rootIndex]);
        node.left = sortedArrayToBST(nums, start, rootIndex - 1);
        node.right = sortedArrayToBST(nums, rootIndex + 1, end);
        return node;
    }
}
