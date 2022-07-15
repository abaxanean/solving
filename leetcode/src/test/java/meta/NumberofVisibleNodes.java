/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NumberofVisibleNodes {

    @DataProvider
    public static Object[][] testCases() {
        Node root_1 = new Node(8);
        root_1.left = new Node(3);
        root_1.right = new Node(10);
        root_1.left.left = new Node(1);
        root_1.left.right = new Node(6);
        root_1.right.right = new Node(14);
        root_1.left.right.left = new Node(4);
        root_1.left.right.right = new Node(7);
        root_1.right.right.left = new Node(13);

        Node root_2 = new Node(10);
        root_2.left = new Node(8);
        root_2.right = new Node(15);
        root_2.left.left = new Node(4);
        root_2.left.left.right = new Node(5);
        root_2.left.left.right.right = new Node(6);
        root_2.right.left = new Node(14);
        root_2.right.right = new Node(16);
        return new Object[][]{
                {root_1, 4},
                {root_2, 5},
        };
    }


    @Test(dataProvider = "testCases")
    public void test(Node root, int expected) {
        assertEquals(visibleNodes(root), expected);
    }

    int visibleNodes(Node root) {
        return visibleNodes(root, 0);
    }

    int visibleNodes(Node node, int level) {
        if (node == null) {
            return level;
        }
        return Math.max(visibleNodes(node.left, level + 1)
                , visibleNodes(node.right, level + 1));
    }

    static class Node {
        int data;
        Node left;
        Node right;

        Node() {
            this.data = 0;
            this.left = null;
            this.right = null;
        }

        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

}
