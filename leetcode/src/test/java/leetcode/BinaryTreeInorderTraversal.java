/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayList;
import java.util.List;

import leetcode.shared.TreeNode;

/**
 * Given the root of a binary tree, return the inorder traversal of its nodes' values.
 */
public class BinaryTreeInorderTraversal {

    List<Integer> result = new ArrayList<>();

    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return result;
        }
        inorderTraversal(root.left);
        result.add(root.val);
        inorderTraversal(root.right);
        return result;

    }

    public static long solution(long n) {
        int stairs = (int) n;
        long[] dp = new long[stairs + 1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3; i <= stairs; i++) {
            dp[i] = dp[i-3] + dp[i-2] + dp[i-1];
        }
        return dp[stairs];
    }
}
