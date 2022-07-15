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
 * Given the head of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list. Return the linked list sorted as well.
 */
public class RemoveDuplicatesFromSortedListII {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createList(1,2,3,3,4,4,5), createList(1,2,5)},
                {createList(1,1,1,2,3), createList(2,3)},
                {createList(), null},
                {createList(1,1), null},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode head, ListNode expected) {
        assertEquals(deleteDuplicates(head), expected);
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode preHead = new ListNode(-101, head);
        ListNode previous = preHead;
        ListNode current = head;
        while (current != null) {
            if (current.next == null || current.val != current.next.val) {
                previous.next = current;
                previous = current;
                current = current.next;
                continue;
            }
            int val = current.val;
            while (current != null && current.val == val) {
                current = current.next;
            }
        }
        previous.next = null;
        return preHead.next;
    }
}
