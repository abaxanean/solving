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
 * Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).
 */
public class SymmetricTree {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createTreeFromPreOrder(1,2,2,3,4,4,3), true},
                {createTreeFromPreOrder(1,2,2,null,3,null,3), false},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, boolean expected) {
        assertEquals(isSymmetric(root), expected);
    }

    public boolean isSymmetric(TreeNode root) {
        return isEquals(root.left, root.right);
    }

    private boolean isEquals(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.val == right.val && isEquals(left.left, right.right) && isEquals(left.right, right.left);
    }
}
