/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode.shared;

import java.util.Objects;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode() {}
    public TreeNode(int val) { this.val = val; }
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    public static TreeNode createTreeFromPreOrder(Integer... preorder) {
        if (preorder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        addChildren(root, 0, preorder);
        return root;
    }

    private static void addChildren(TreeNode node, int index, Integer[] preorder) {
        int leftChildIndex = (index * 2) + 1;
        if (leftChildIndex >= preorder.length) {
            return;
        }
        if (preorder[leftChildIndex] != null) {
            node.left = new TreeNode(preorder[leftChildIndex]);
            addChildren(node.left, leftChildIndex, preorder);
        }
        int rightChildIndex = (index * 2) + 2;
        if (rightChildIndex >= preorder.length || preorder[rightChildIndex] == null) {
            return;
        }
        node.right = new TreeNode(preorder[rightChildIndex]);
        addChildren(node.right, rightChildIndex, preorder);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TreeNode treeNode = (TreeNode)o;
        return val == treeNode.val && Objects.equals(left, treeNode.left) && Objects.equals(right, treeNode.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, left, right);
    }
}