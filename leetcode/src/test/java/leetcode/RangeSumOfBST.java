/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static leetcode.shared.TreeNode.createTreeFromPreOrder;
import static org.testng.Assert.assertEquals;

/**
 * Given the root node of a binary search tree and two integers low and high, return the sum of values of all nodes with a value in the inclusive range [low, high].
 */
public class RangeSumOfBST {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createTreeFromPreOrder(new Integer[]{10, 5, 15, 3, 7, null, 18}), 7, 15, 32},
                {createTreeFromPreOrder(new Integer[]{10, 5, 15, 3, 7, 13, 18, 1, null, 6}), 6, 10, 23},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, int low, int high, int expected) {
        assertEquals(rangeSumBST(root, low, high), expected);
    }


    public int rangeSumBST(TreeNode node, int low, int high) {
        if (node == null) {
            return 0;
        }
        if (node.val < low) {
            return rangeSumBST(node.right, low, high);
        }
        if (node.val > high) {
            return rangeSumBST(node.left, low, high);
        }
        return node.val + rangeSumBST(node.left, low, high) + rangeSumBST(node.right, low, high);
    }
}
