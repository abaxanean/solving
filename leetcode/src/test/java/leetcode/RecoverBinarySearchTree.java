/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static leetcode.shared.TreeNode.createTreeFromPreOrder;
import static org.testng.Assert.assertEquals;

/**
 * You are given the root of a binary search tree (BST), where the values of exactly two nodes of the tree were swapped by mistake. Recover the tree without changing its structure.
 */
public class RecoverBinarySearchTree {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createTreeFromPreOrder(1, 3, null, null, 2), createTreeFromPreOrder(3, 1, null, null, 2)},
                {createTreeFromPreOrder(3,1,4,null,null,2), createTreeFromPreOrder(2,1,4,null,null,3)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, TreeNode expected) {
        recoverTree(root);
        assertEquals(root, expected);
    }

    TreeNode first;
    TreeNode second;
    TreeNode previous;

    public void recoverTree(TreeNode root) {
        inOrder(root);
        int tmp = first.val;
        first.val = second.val;
        second.val = tmp;
    }

    // 4,2,3,1
    private void inOrder(final TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        if (previous != null && previous.val > root.val) {
            if (first == null) {
                this.first = this.previous;
            }
            this.second = root;
        }
        this.previous = root;
        inOrder(root.right);
    }

    private static int[] findTwoSwapped(List<Integer> nums) {
        int n = nums.size();
        int x = -1, y = -1;
        boolean swapped_first_occurrence = false;
        for (int i = 0; i < n - 1; ++i) {
            if (nums.get(i + 1) < nums.get(i)) {
                y = nums.get(i + 1);
                if (!swapped_first_occurrence) {
                    // first swap occurrence
                    x = nums.get(i);
                    swapped_first_occurrence = true;
                } else {
                    // second swap occurrence
                    break;
                }
            }
        }
        return new int[]{x, y};
    }

    private static void iterateInOrderUsingStack(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.add(root);
                root = root.left;
            }
            root = stack.removeLast();
            System.out.println(root.val);
            root = root.right;
        }
    }

    public static void main(String[] args) {
        iterateInOrderUsingStack(createTreeFromPreOrder(3,1,null,null,2));
        System.out.println();
        iterateInOrderUsingStack(createTreeFromPreOrder(2,1,4,null,null,3));
    }
}
