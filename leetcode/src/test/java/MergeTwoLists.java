import java.util.Objects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import shared.ListNode;

import static org.testng.Assert.assertEquals;
import static shared.ListNode.createList;

/**
 * Merge two sorted linked lists and return it as a sorted list. The list should be made by splicing together the nodes of the first two lists.
 */
public class MergeTwoLists {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {createList(1, 2, 4), createList(1, 3, 4), createList(1, 1, 2, 3, 4, 4)},
                {createList(), createList(), createList()},
                {createList(), createList(0), createList(0)}
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode l1, ListNode l2, ListNode expected) {
        assertEquals(mergeTwoLists(l1, l2), expected);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        final ListNode result = new ListNode(0);
        ListNode resultCursor = result;
        ListNode l1Cursor = l1;
        ListNode l2Cursor = l2;
        while (l1Cursor != null && l2Cursor != null) {
            if (l1Cursor.val < l2Cursor.val) {
                resultCursor.next = new ListNode(l1Cursor.val);
                l1Cursor = l1Cursor.next;
            } else {
                resultCursor.next = new ListNode(l2Cursor.val);
                l2Cursor = l2Cursor.next;
            }
            resultCursor = resultCursor.next;
        }
        if (l1Cursor == null && l2Cursor == null) {
            return result.next;
        }
        // re-use portion of existing list after on of them exhausted
        resultCursor.next = l1Cursor != null ? l1Cursor : l2Cursor;
        return result.next;
    }



}
