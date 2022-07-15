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
 * Given the head of a sorted linked list, delete all duplicates such that each element appears only once. Return the linked list sorted as well.
 */
public class RemoveDuplicatesfromSortedList {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createList(1,1,2), createList(1,2)},
                {createList(1,1,2,3,3), createList(1,2,3)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode head, ListNode expected) {
        assertEquals(deleteDuplicates(head), expected);
    }


    public ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        while(current != null && current.next != null) {
            if (current.val == current.next.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        return head;
    }
}
