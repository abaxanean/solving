/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

/**
 * Given the root of a binary tree, flatten the tree into a "linked list":
 *
 * The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list and the left child pointer is always null.
 * The "linked list" should be in the same order as a pre-order traversal of the binary tree.
 */
public class FlattenBinaryTree_toLinkedList {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
//                {TreeNode.createTreeFromPreOrder(1,2,5,3,4,null,6), TreeNode.createTreeFromPreOrder(1,null,2,null,3,null,4,null,5,null,6)},
                {TreeNode.createTreeFromPreOrder(3,1,4,null,2), TreeNode.createTreeFromPreOrder(3,null,1,null,2,null,4)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, TreeNode expected) {
        flatten(root);
        assertEquals(root, expected);
    }

    public void flatten2(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        TreeNode current = root;
        while (current != null) {
            if (current.left == null) {
                if (current.right == null && !queue.isEmpty()) {
                    current.right = queue.pollFirst();
                }
                current = current.right;
                continue;
            }
            if (current.right != null) {
                queue.addFirst(current.right);
            }
            current.right = current.left;
            current.left = null;
            current = current.right;
        }
    }

    TreeNode last = null;
    public void flatten(TreeNode root) {
        if(root == null){
            return;
        }

        flatten(root.right);
        flatten(root.left);

        root.right = last;
        root.left = null;

        last = root;
    }
}
