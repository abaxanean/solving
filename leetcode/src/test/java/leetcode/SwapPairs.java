package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.ListNode;

import static org.testng.Assert.assertEquals;
import static leetcode.shared.ListNode.createList;

/**
 * Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)
 */
public class SwapPairs {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createList(1, 2, 3, 4), createList(2, 1, 4, 3)},
                {createList(1, 2, 3, 4, 5), createList(2, 1, 4, 3, 5)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode head, ListNode expected) {
        assertEquals(swapPairs(head), expected);
    }

public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }
    ListNode dummy = new ListNode(0, head);
    ListNode first;
    ListNode second;
    ListNode previous = dummy;
    do {
        // take 2 consecutive nodes
        first = previous.next;
        second = first.next;
        // swap first with second
        first.next = second.next;
        second.next = first;
        // link previous pair with this one
        previous.next = second;
        // store the node to be linked with next pair
        previous = first;
    } while (previous.next != null && previous.next.next != null);
    return dummy.next;
}

}
