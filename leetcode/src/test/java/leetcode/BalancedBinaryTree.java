/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.ListNode;
import leetcode.shared.TreeNode;

import static leetcode.shared.TreeNode.createTreeFromPreOrder;
import static org.testng.Assert.assertEquals;

/**
 * Given a binary tree, determine if it is height-balanced.
 *
 * For this problem, a height-balanced binary tree is defined as:
 *
 * a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
 */
public class BalancedBinaryTree {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {TreeNode.createTreeFromPreOrder(3,9,20,null,null,15,7), true},
                {TreeNode.createTreeFromPreOrder(1,2,2,3,3,null,null,4,4), false},
                {TreeNode.createTreeFromPreOrder(), true},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, boolean expected) {
        assertEquals(isBalanced(root), expected);
    }

    public boolean isBalanced(TreeNode root) {
        return getHeight(root) != -1;
    }

    int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = getHeight(node.left);
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = getHeight(node.right);
        if (rightHeight == -1) {
            return -1;
        }
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
