/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

public class Reverse {

    public static class LinkedListNode {

        public int value;
        public LinkedListNode next;

        public LinkedListNode(int value) {
            this.value = value;
        }
    }

    public static LinkedListNode reverse(LinkedListNode headOfList) {
        if (headOfList == null) {
            return null;
        }
        LinkedListNode previous = null;
        LinkedListNode current = headOfList;
        LinkedListNode next;

        while (current != null) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;

        }
        return previous;
    }

    public static void main(String[] args) {
        LinkedListNode one = new LinkedListNode(1);
        LinkedListNode two = new LinkedListNode(2);
        LinkedListNode three = new LinkedListNode(3);
        LinkedListNode four = new LinkedListNode(4);
        LinkedListNode five = new LinkedListNode(5);

        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;

        reverse(one);
        System.out.println(one.value);
    }


}
