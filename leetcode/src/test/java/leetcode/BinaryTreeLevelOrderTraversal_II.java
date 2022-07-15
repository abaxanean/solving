/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static java.util.Arrays.asList;
import static leetcode.shared.TreeNode.createTreeFromPreOrder;
import static org.testng.Assert.assertEquals;

/**
 * Given the root of a binary tree, return the bottom-up level order traversal of its nodes' values. (i.e., from left to right, level by level from leaf to root).
 */
public class BinaryTreeLevelOrderTraversal_II {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
//                {createTreeFromPreOrder(3, 9, 20, null, null, 15, 7), asList(asList(15, 7), asList(9, 20), asList(3))},
                {createTreeFromPreOrder(1), asList(asList(1))},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, List<List<Integer>> expected) {
        assertEquals(levelOrderBottom(root), expected);
    }



    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, 0, result);
        Collections.reverse(result);
        return result;
    }

    private void dfs(final TreeNode node, final int i,  List<List<Integer>> result) {
        if (node == null) {
            return;
        }
        List<Integer> list;
        if (result.size() > i) {
            list = result.get(i);
        } else {
            list = new ArrayList<>();
            result.add(list);
        }
        list.add(node.val);
        dfs(node.left, i + 1, result);
        dfs(node.right, i + 1, result);
    }
}
