/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.ListNode;

import static leetcode.shared.ListNode.createList;
import static org.testng.Assert.assertEquals;

/**
 * Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect. If the two linked lists have no intersection at all, return null.
 *
 * For example, the following two linked lists begin to intersect at node c1:
 */
public class IntersectionOfTwoLinkedLists {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createList(4,1,8,4,5), createList(5,6,1), 2},
                {createList(1,9,1,2,4), createList(3), 3},
                {createList(2,6,4), createList(1,5), 3},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode headA, ListNode headB, int skipA) {
        ListNode intersection = headA;
        for (int i = 0; i < skipA; i++) {
            intersection = intersection.next;
        }
        ListNode b = headB;
        while (b.next != null) {
            b = b.next;
        }
        b.next = intersection;
        assertEquals(getIntersectionNode(headA, headB), intersection);
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA;
        ListNode b = headB;
        boolean switched = false;
        while (a != b) {
            a = a.next;
            b = b.next;
            if (a == null) {
                if (switched) {
                    return null;
                }
                a = headB;
                switched = true;
            }
            if (b == null) {
                b = headA;
            }
        }
        return a;
    }
}
