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
 * Given the head of a singly linked list and two integers left and right where left <= right, reverse the nodes of the list from position left to position right, and return the reversed list.
 */
public class ReverseLinkedListII {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createList(1,2,3,4,5), 2, 4, createList(1,4,3,2,5)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode head, int left, int right, ListNode expected) {
        assertEquals(reverseBetween(head, left, right), expected);
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) {
            return head;
        }
        ListNode preHead = new ListNode(0, head);
        ListNode previous = preHead;
        ListNode current = head;
        int index = 1;
        ListNode start = null;
        while (current != null) {
            if (index == left) {
                start = previous;
            }
            ListNode tmp = current.next;
            if (index > left && index <= right) {
                // reverse
                current.next = previous;
            }
            if (index++ == right) {
                start.next.next = tmp;
                start.next = current;
                return preHead.next;
            }
            previous = current;
            current = tmp;
        }
        throw new IllegalArgumentException("Should not be reached");
    }
}
