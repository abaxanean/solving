package shared;

import java.util.Objects;

public class ListNode {

    public int val;
    public ListNode next;

    ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode createList(final int... numbers) {
        final ListNode preHead = new ListNode(0);
        ListNode current = preHead;
        for (int i : numbers) {
            current.next = new ListNode(i);
            current = current.next;
        }
        return preHead.next;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ListNode listNode = (ListNode)o;
        return val == listNode.val && Objects.equals(next, listNode.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, next);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode cursor = this;
        while (cursor != null) {
            sb.append(cursor.val);
            cursor = cursor.next;
        }
        return sb.toString();
    }
}

