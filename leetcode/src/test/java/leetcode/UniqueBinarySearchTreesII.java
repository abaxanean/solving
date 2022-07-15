/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static leetcode.shared.TreeNode.createTreeFromPreOrder;
import static org.testng.Assert.assertEquals;

/**
 * Given an integer n, return all the structurally unique BST's (binary search trees), which has exactly n nodes of unique values from 1 to n. Return the answer in any order.
 */
public class UniqueBinarySearchTreesII {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
//                {2, Arrays.asList(createTreeFromPreOrder(new Integer[]{1,null,2}), createTreeFromPreOrder(new Integer[]{2,1,null}))},
//                {3, Arrays.asList(
//                        createTreeFromPreOrder(new Integer[]{1, null, 2, null, 3}),
//                        createTreeFromPreOrder(new Integer[]{1, null, 3, 2}),
//                        createTreeFromPreOrder(new Integer[]{2, 1, 3}),
//                        createTreeFromPreOrder(new Integer[]{3, 1, null, null, 2}),
//                        createTreeFromPreOrder(new Integer[]{3, 2, null, 1})
//                )},
                {4, Arrays.asList(
                        createTreeFromPreOrder(new Integer[]{1, null, 2, null, 3}),
                        createTreeFromPreOrder(new Integer[]{1, null, 3, 2}),
                        createTreeFromPreOrder(new Integer[]{2, 1, 3}),
                        createTreeFromPreOrder(new Integer[]{3, 1, null, null, 2}),
                        createTreeFromPreOrder(new Integer[]{3, 2, null, 1})
                )},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int s, List<TreeNode> expected) {
//        this.result.clear();
        assertEquals(generateTrees(s), expected);
    }


    List[][] dp;
    public List<TreeNode> generateTrees(int n) {
        this.dp = new List[n + 1][n + 1];
        return generateTrees(1, n);
    }

    public List<TreeNode> generateTrees(int start, int end) {
        if (start > end) {
            return Collections.singletonList(null);
        }
        if (this.dp[start][end] != null) {
            return this.dp[start][end];
        }
        List<TreeNode> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            List<TreeNode> lefts = generateTrees(start, i - 1);
            List<TreeNode> rights = generateTrees(i + 1, end);
            for (TreeNode left : lefts) {
                for (TreeNode right : rights) {
                    result.add(new TreeNode(i, left, right));
                }
            }
        }
        return this.dp[start][end] = result;
    }

    List<TreeNode> result = new ArrayList<>();
    TreeNode root;
    int n;

//    public List<TreeNode> generateTrees(int n) {
//        this.n = n;
//        // for (int i = 1; i <= n; i ++) {
//        this.root = new TreeNode();
//        generateTree(this.root, 1, n, 1);
//        // }
//        return result;
//    }
//
//    private void generateTree(TreeNode parent, int start, int end, int count) {
//        for (int i = start; i <= end; i++) {
//            parent.val = i;
//            if (count == this.n) {
//                result.add(copyTree(this.root, new HashSet<>()));
//                return;
//            }
//            int left = 0;
//            if (start < i) {
//                parent.left = new TreeNode();
//                generateTree(parent.left, start, i - 1, count + 1);
//                left = i - start;
//            } else {
//                parent.left = null;
//            }
//
//            if (i < end) {
//                parent.right = new TreeNode();
//                generateTree(parent.right, i + 1, end, count + 1 + left);
//            } else {
//                parent.right = null;
//            }
//        }
//    }
//
//    TreeNode copyTree(TreeNode src, Set<Integer> set) {
//        if (src == null || !set.add(src.val)) {
//            return null;
//        }
//        TreeNode node = new TreeNode(src.val);
//        node.left = copyTree(src.left, set);
//        node.right = copyTree(src.right, set);
//        return node;
//    }
}

// [[1,null,2,null,3,null,4],[1,null,2,null,4,3],[1,null,3,2,4],[1,null,4,2,null,null,3],[1,null,4,3,null,2],[2,1,3,null,null,null,4],[2,1,4,null,null,3],[3,2,4],                  [4,1,null,null,2,null,3],[4,1,null,null,3,2],[4,2,null,1,3],[4,3,null,1,null,null,2],[4,3,null,2,null,1]]
// [[1,null,2,null,3,null,4],[1,null,2,null,4,3],[1,null,3,2,4],[1,null,4,2,null,null,3],[1,null,4,3,null,2],[2,1,3,null,null,null,4],[2,1,4,null,null,3],[3,1,4,null,2],[3,2,4,1], [4,1,null,null,2,null,3],[4,1,null,null,3,2],[4,2,null,1,3],[4,3,null,1,null,null,2],[4,3,null,2,null,1]]
