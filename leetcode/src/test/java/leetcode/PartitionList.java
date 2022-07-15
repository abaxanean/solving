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
 * Given the head of a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
 *
 * You should preserve the original relative order of the nodes in each of the two partitions.
 */
public class PartitionList {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createList(1,4,3,2,5,2), 3, createList(1,2,2,4,3,5)},
                {createList(4,1,3,2,5,2), 3, createList(1,2,2,4,3,5)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode head, int x, ListNode expected) {
        assertEquals(partition(head, x), expected);
    }


    public ListNode partition(ListNode head, int x) {
        ListNode original = head;

        // before and after are the two pointers used to create the two list
        // before_head and after_head are used to save the heads of the two lists.
        // All of these are initialized with the dummy nodes created.
        ListNode before_head = new ListNode(0);
        ListNode before = before_head;
        ListNode after_head = new ListNode(0);
        ListNode after = after_head;

        while (head != null) {

            // If the original list node is lesser than the given x,
            // assign it to the before list.
            if (head.val < x) {
                before.next = head;
                before = before.next;
            } else {
                // If the original list node is greater or equal to the given x,
                // assign it to the after list.
                after.next = head;
                after = after.next;
            }

            // move ahead in the original list
            head = head.next;
        }

        // Last node of "after" list would also be ending node of the reformed list
        after.next = null;

        // Once all the nodes are correctly assigned to the two lists,
        // combine them to form a single list which would be returned.
        before.next = after_head.next;

        return before_head.next;
    }

    public ListNode partition2(ListNode head, int x) {
        ListNode dummy = new ListNode(0, head);
        ListNode previous = dummy;
        ListNode smaller = dummy;
        ListNode current = head;
        while (current != null) {
            if (current.val >= x) {
                previous = current;
                current = current.next;
                continue;
            }

            if (smaller.next == current) {
                previous = current;
                current = current.next;
                smaller = current;
                continue;
            }

            // remove the node
            previous.next = current.next;
            //insert the node
            ListNode nextTmp = smaller.next;
            smaller.next = current;
            current.next = nextTmp;
            smaller = current;
            current = previous.next;

        }
        return dummy.next;
    }
}
