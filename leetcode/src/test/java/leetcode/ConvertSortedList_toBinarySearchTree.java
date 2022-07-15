/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.ListNode;
import leetcode.shared.TreeNode;

import static leetcode.shared.TreeNode.createTreeFromPreOrder;
import static org.testng.Assert.assertEquals;

/**
 * Given the head of a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
 * <p>
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 */
public class ConvertSortedList_toBinarySearchTree {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {ListNode.createList(-10, -3, 0, 5, 9), createTreeFromPreOrder(0, -3, 9, -10, null, 5)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode head, TreeNode expected) {
        assertEquals(sortedListToBST(head), expected);
    }

//    public TreeNode sortedListToBST(ListNode head) {
//        int size = 0;ListNode current = head;
//        while (current != null) {
//            size++;
//            current = current.next;
//        }
//        int[] array = new int[size];
//        current = head;
//        for (int i = 0; i < size; i++) {
//            array[i] = current.val;
//            current = current.next;
//        }
//        return sortedArrayToBST(array, 0, size - 1);
//    }

    private TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        int rootIndex = (start + end) / 2;
        TreeNode node = new TreeNode(nums[rootIndex]);
        node.left = sortedArrayToBST(nums, start, rootIndex - 1);
        node.right = sortedArrayToBST(nums, rootIndex + 1, end);
        return node;
    }

    public TreeNode sortedListToBST(ListNode head) {
        int size = getListSize(head);
        ListNode[] headArr = new ListNode[]{head};
        return convertListToBST(headArr, 0, size - 1);
    }

    private int getListSize(ListNode head) {
        int size = 0;
        ListNode curr = head;
        while (curr != null) {
            curr = curr.next;
            size++;
        }
        return size;
    }


    private TreeNode convertListToBST(ListNode[] headArr, int begin, int end) {
        if (begin > end) return null;
        int mid = begin + (end - begin) / 2;
        TreeNode left = convertListToBST(headArr, begin, mid - 1);
        TreeNode node = new TreeNode(headArr[0].val);
        node.left = left;
        headArr[0] = headArr[0].next;
        node.right = convertListToBST(headArr, mid + 1, end);
        return node;
    }
}
