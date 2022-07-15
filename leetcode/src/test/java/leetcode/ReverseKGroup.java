package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.ListNode;

import static leetcode.shared.ListNode.createList;
import static org.testng.Assert.assertEquals;

/**
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 * <p>
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.
 * <p>
 * You may not alter the values in the list's nodes, only nodes themselves may be changed.
 */
public class ReverseKGroup {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createList(1, 2, 3, 4, 5), 2, createList(2, 1, 4, 3, 5)},
                {createList(1, 2, 3, 4, 5), 3, createList(3, 2, 1, 4, 5)},
                {createList(1, 2, 3, 4, 5), 1, createList(1, 2, 3, 4, 5)},
                {createList(1), 1, createList(1)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode head, int k, ListNode expected) {
        assertEquals(reverseKGroup(head, k), expected);
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (k < 2 || head.next == null) {
            return head;
        }
        int count = 1;
        ListNode start = head;
        ListNode end = head;
        ListNode nextStart;
        ListNode dummy = new ListNode(0, head);
        ListNode previousEnd = dummy;
        while (end != null) {
            if (count == k) {
                // link the end of the previous sub-list to the start of the new sub-list
                previousEnd.next = end;
                // next batch will start after the end of this one
                nextStart = end.next;
                // reverse the sublist
                reverse(start, end);
                previousEnd = start;
                start = nextStart;
                end = nextStart;
                count = 0;
            } else {
                end = end.next;
            }
            count++;
        }
        return dummy.next;
    }

    ListNode reverse(ListNode start, ListNode end) {
        ListNode previous = null;
        ListNode current = start;
        ListNode next;
        while (previous != end) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        // current points now at the element after end in the original list
        start.next = current;
        return end;
    }
}
