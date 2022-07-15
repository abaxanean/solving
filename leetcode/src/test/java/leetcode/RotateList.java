/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.ListNode;

import static leetcode.shared.ListNode.createList;
import static org.testng.Assert.assertEquals;

/**
 * Given the head of a linked list, rotate the list to the right by k places.
 */
public class RotateList {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createList(1, 2, 3, 4, 5), 2, createList(4, 5, 1, 2, 3)},
                {createList(0, 1, 2), 4, createList(2, 0, 1)},
                {createList(), 0, null},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode head, int k, ListNode expected) {
        assertEquals(rotateRight(head, k), expected);
    }

public ListNode rotateRight(ListNode head, int k) {
    if (head == null) {
        return null;
    }
    int size = getSize(head);
    // reduce k if it is bigger than list size
    k = k % size;
    if (k == 0) {
        return head;
    }
    // place two pointers at distance k
    ListNode current = head;
    ListNode behind = head;
    for(int i = 0 ; i < k; i++) {
        current = current.next;
    }
    // move the pointers forward until the forward one reaches the end of the list
    while(current.next != null) {
        current = current.next;
        behind = behind.next;

    }
    // two changes have to be done to rotate the list
    // 1. last node now points to the first
    current.next = head;
    // the node that is k positions behind now is the last
    // the nodes that used to be after it is the new head
    ListNode result = behind.next;
    behind.next = null;
    return result;
}

int getSize(ListNode head) {
    int size = 0;
    ListNode node = head;
    while (node != null) {
        node = node.next;
        size++;
    }
    return size;
}

}
