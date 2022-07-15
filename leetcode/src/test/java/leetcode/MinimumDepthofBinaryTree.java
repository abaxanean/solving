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
 * Given a binary tree, find its minimum depth.
 *
 * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
 *
 * Note: A leaf is a node with no children.
 */
public class MinimumDepthofBinaryTree {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {TreeNode.createTreeFromPreOrder(3,9,20,null,null,15,7), 2},
                {TreeNode.createTreeFromPreOrder(2,null,3,null,4,null,5,null,6), 5},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, int expected) {
        assertEquals(minDepth(root), expected);
    }

    int minFound = 0;
    public int minDepth(TreeNode root) {
        minDepth(root, 1);
        return this.minFound;
    }

    public void minDepth(TreeNode root, int currentDepth) {
        if (root == null) {
            return;
        }
        if (currentDepth == this.minFound) {
            return;
        }
        if (root.left == null && root.right == null) {
            // found leaf node
            this.minFound = currentDepth;
            return;
        }
        if (root.left != null) {
            minDepth(root.left, currentDepth + 1);
        }
        if (root.right != null) {
            minDepth(root.right, currentDepth + 1);
        }
    }
}
