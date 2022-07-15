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
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 *
 * A valid BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 */
public class ValidateBinarySearchTree {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createTreeFromPreOrder(2,1,3), true},
                {createTreeFromPreOrder(5,1,4,null,null,3,6), false},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, boolean expected) {
        assertEquals(isValidBST(root), expected);
    }

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);
    }

    public boolean isValidBST(TreeNode node, Integer min, Integer max) {
        if (node == null) {
            return true;
        }
        if(min != null && node.val <= min) {
            return false;
        }
        if(max != null && node.val >= max) {
            return false;
        }
        return isValidBST(node.left, min, node.val)
                && isValidBST(node.right, node.val, max);
    }
}
