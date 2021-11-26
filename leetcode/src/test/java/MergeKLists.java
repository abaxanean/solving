import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import shared.ListNode;

import static shared.ListNode.createList;

import static org.testng.Assert.assertEquals;

/**
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 * <p>
 * Merge all the linked-lists into one sorted linked-list and return it.
 */
public class MergeKLists {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new ListNode[]{createList(1,4,5), createList(1,3,4), createList(2, 6)}, createList(1,1,2,3,4,4,5,6)},
                {new ListNode[]{createList()}, createList()},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(ListNode[] lists, ListNode expected) {
        assertEquals(mergeKLists(lists), expected);
    }

    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt(n -> n.val));
        Arrays.stream(lists).filter(Objects::nonNull).forEach(heap::add);
        ListNode result = new ListNode(0);
        ListNode resultCursor = result;
        while (heap.size() > 1) {
            ListNode min = heap.poll();
            resultCursor.next = min;
            if (min.next != null) {
                heap.add(min.next);
            }
            resultCursor = resultCursor.next;
        }
        resultCursor.next = heap.poll();
        return result.next;
    }


}
