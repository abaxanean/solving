/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static org.testng.Assert.assertEquals;
import static java.util.Arrays.asList;

/**
 * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of the node values in the path equals targetSum. Each path should be returned as a list of the node values, not node references.
 *
 * A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.
 */
public class PathSum_II {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {TreeNode.createTreeFromPreOrder(5,4,8,11,null,13,4,7,2,null,null,null, null, 5,1), 22, asList(asList(5,4,11,2), asList(5,8,4,5))},
                {TreeNode.createTreeFromPreOrder(1,2,3), 5, asList()},
                {TreeNode.createTreeFromPreOrder(1,2), 0, asList()},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, int targetSum, List<List<Integer>> expected) {
        assertEquals(pathSum(root, targetSum), expected);
    }

    List<List<Integer>> result;

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        result = new ArrayList<>();
        Deque<Integer> queue = new ArrayDeque<>();
        pathSum(root, targetSum, queue);
        return this.result;
    }

    void pathSum(TreeNode node, int targetSum, Deque<Integer> queue) {
        if (node == null) {
            return;
        }
        queue.addLast(node.val);
        if (node.left == null && node.right == null) {
            if (node.val == targetSum) {
                result.add(new ArrayList<>(queue));
            }
        } else {
            pathSum(node.left, targetSum - node.val, queue);
            pathSum(node.right, targetSum - node.val, queue);
        }
        queue.removeLast();
    }
}
