/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static org.testng.Assert.assertEquals;

/**
 * Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path such that adding up all the values along the path equals targetSum.
 *
 * A leaf is a node with no children.
 */
public class PathSum {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {TreeNode.createTreeFromPreOrder(5,4,8,11,null,13,4,7,2,null,null,null,1), 22, true},
                {TreeNode.createTreeFromPreOrder(1,2,3), 5, false},
                {TreeNode.createTreeFromPreOrder(), 0, false},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, int targetSum, boolean expected) {
        assertEquals(hasPathSum(root, targetSum), expected);
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum  - root.val);
    }
}
