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
 * TODO: Document this class.
 */
public class BinaryTreeZigzagLevelOrderTraversal {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createTreeFromPreOrder(3, 9, 20, null, null, 15, 7), asList(asList(3), asList(20, 9), asList(15, 7))},
                {createTreeFromPreOrder(1), asList(asList(1))},
                {createTreeFromPreOrder(), asList()},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(TreeNode root, List<List<Integer>> expected) {
        assertEquals(zigzagLevelOrder(root), expected);
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        return new ZigzagList(root);
    }

    // === solution 1
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        // what order do we process the current line in
        boolean leftToRight = true;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        List<List<Integer>> result = new ArrayList<>();
        while(!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> line = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = leftToRight ? queue.removeFirst() : queue.removeLast();
                line.add(node.val);
                if (leftToRight) {
                    addLast(queue, node.left);
                    addLast(queue, node.right);
                } else {
                    addFirst(queue, node.right);
                    addFirst(queue, node.left);
                }
            }
            result.add(line);
            leftToRight = !leftToRight;
        }
        return result;
    }

    static void addLast(Deque<TreeNode> queue, TreeNode node) {
        if (node == null) {
            return;
        }
        queue.addLast(node);
    }

    static void addFirst(Deque<TreeNode> queue, TreeNode node) {
        if (node == null) {
            return;
        }
        queue.addFirst(node);
    }

    // === solution 2

    static class ZigzagList extends AbstractList<List<Integer>> {

        final TreeNode root;
        final int size;

        public ZigzagList(final TreeNode root) {
            this.root = root;
            this.size = getSize(root);
        }

        private int getSize(final TreeNode root) {
            if (root == null) {
                return 0;
            }
            return 1 + Math.max(getSize(root.left), getSize(root.right));
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
            return new ZigzagIterator(this.root);
        }
    }

    private static class ZigzagIterator implements Iterator<List<Integer>> {

        final Deque<TreeNode> queue = new ArrayDeque<>();
        boolean leftToRight = true;

        public ZigzagIterator(final TreeNode root) {
            this.queue.add(root);
        }

        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override
        public List<Integer> next() {
            int size = queue.size();
            List<Integer> line = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = this.leftToRight ? queue.removeFirst() : queue.removeLast();
                line.add(node.val);
                if (leftToRight) {
                    addLast(queue, node.left);
                    addLast(queue, node.right);
                } else {
                    addFirst(queue, node.right);
                    addFirst(queue, node.left);
                }
            }
            leftToRight = !leftToRight;
            return line;
        }

        static void addLast(Deque<TreeNode> queue, TreeNode node) {
            if (node == null) {
                return;
            }
            queue.addLast(node);
        }

        static void addFirst(Deque<TreeNode> queue, TreeNode node) {
            if (node == null) {
                return;
            }
            queue.addFirst(node);
        }
    }
}
