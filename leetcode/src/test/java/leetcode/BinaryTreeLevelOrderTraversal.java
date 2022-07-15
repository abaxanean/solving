/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.AbstractList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static java.util.Arrays.asList;
import static leetcode.shared.TreeNode.createTreeFromPreOrder;
import static org.testng.Assert.assertEquals;

/**
 * Given the root of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).
 */
public class BinaryTreeLevelOrderTraversal {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createTreeFromPreOrder(3, 9, 20, null, null, 15, 7), asList(asList(3), asList(9, 20), asList(15, 7))},
                {createTreeFromPreOrder(1), asList(asList(1))},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, List<List<Integer>> expected) {
        assertEquals(levelOrder(root), expected);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        return new LevelList(root);
    }

    private static class LevelList extends AbstractList<List<Integer>> {

        final TreeNode root;
        final int size;

        public LevelList(final TreeNode root) {
            this.root = root;
            this.size = getSize(root, 0);
        }

        private int getSize(final TreeNode root, int level) {
            if (root == null) {
                return 0;
            }
            return 1 + Math.max(getSize(root.left, level + 1), getSize(root.right, level + 1));
        }

        @Override
        public List<Integer> get(final int index) {
            return null;
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public Iterator<List<Integer>> iterator() {
            return new LevelIterator(this.root);
        }
    }

    private static class LevelIterator implements Iterator<List<Integer>> {

        private static final TreeNode SENTINEL = new TreeNode();
        final Deque<TreeNode> queue;

        public LevelIterator(final TreeNode root) {
            this.queue = new ArrayDeque<>();
            this.queue.add(root);
            this.queue.add(SENTINEL);
        }

        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override
        public List<Integer> next() {
            List<Integer> list = new ArrayList<>();
            TreeNode node;
            while ((node = this.queue.removeFirst()) != SENTINEL) {
                list.add(node.val);
                addToQueue(node.left);
                addToQueue(node.right);
            }
            if (!this.queue.isEmpty()) {
                // use null as separator between levels
                this.queue.addLast(SENTINEL);
            }
            return list;
        }

        private void addToQueue(TreeNode node) {
            if (node == null) {
                return;
            }
            this.queue.addLast(node);
        }
    }
}