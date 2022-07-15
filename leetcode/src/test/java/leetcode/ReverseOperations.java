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
 * TODO: Document this class.
 */
public class ReverseOperations {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createList(1, 2, 8, 9, 12, 16), createList(1, 8, 2, 9, 16, 12)},
                {createList(2, 18, 24, 3, 5, 7, 9, 6, 12), createList(24, 18, 2, 3, 5, 7, 9, 12, 6)},
                {createList(1, 2, 3, 4, 5), createList(1, 2, 3, 4 ,5)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode head, ListNode expected) {
        assertEquals(reverse(head), expected);
    }

    class Node {
        int data;
        Node next;

        Node(int x) {
            data = x;
            next = null;
        }
    }

ListNode reverse(ListNode head) {
    ListNode preStart = new ListNode(-1, head);
    ListNode previous = preStart;
    for (ListNode current = head; current != null; current = current.next) {
        if (current.val % 2 == 1) {
            previous = current;
        } else {
            previous.next = reverseEven(current);
        }
    }
    return preStart.next;
}

ListNode reverseEven(final ListNode start) {
    ListNode previous = null;
    ListNode current = start;
    ListNode next;
    while (current != null && current.val % 2 == 0) {
        next = current.next;
        current.next = previous;
        previous = current;
        current = next;
    }
    start.next = current;
    return previous;
}


Node reverse(Node head) {
    Node preStart = new Node(1);
    preStart.next = head;
    Node previous = preStart;
    for (Node start = head; start != null; start = start.next) {
        if (start.data % 2 == 1) {
            previous = start;
            continue;
        }
        Node end = start.next;
        while (end != null && end.data % 2 == 0) {
            end = end.next;
        }
        previous.next = reverse(start, end);
    }
    return preStart.next;
}

// reverse all nodes from start (inclusive) up to end (exclusive)
// returns the last reversed node
Node reverse(final Node start, final Node end) {
    Node previous = end;
    Node current = start;
    Node next;
    while (current != end) {
        next = current.next;
        current.next = previous;
        previous = current;
        current = next;
    }
    return previous;
}
}
