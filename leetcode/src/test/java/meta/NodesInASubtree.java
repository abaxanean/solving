/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NodesInASubtree {

    @DataProvider
    public static Object[][] testCases() {
        String s_1 = "aba";
        Node root_1 = new Node(1);
        root_1.children.add(new Node(2));
        root_1.children.add(new Node(3));
        ArrayList<Query> queries_1 = new ArrayList<>();
        queries_1.add(new Query(1, 'a'));

        String s_2 = "abaacab";
        Node root_2 = new Node(1);
        root_2.children.add(new Node(2));
        root_2.children.add(new Node(3));
        root_2.children.add(new Node(7));
        root_2.children.get(0).children.add(new Node(4));
        root_2.children.get(0).children.add(new Node(5));
        root_2.children.get(1).children.add(new Node(6));
        ArrayList<Query> queries_2 = new ArrayList<>();
        queries_2.add(new Query(1, 'a'));
        queries_2.add(new Query(2, 'b'));
        queries_2.add(new Query(3, 'a'));
        return new Object[][]{
                {root_1, queries_1, s_1, new int[]{2}},
                {root_2, queries_2, s_2, new int[]{4,1,2}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(Node root, ArrayList<Query> queries, String s, int[] expected) {
        assertEquals(countOfNodes(root, queries, s), expected);
    }

    int[] countOfNodes(Node root, ArrayList<Query> queries, String s) {
        int[] result = new int[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            result[i] = countNodes(root, queries.get(i), s);
        }
        return result;
    }

    private int countNodes(final Node node, final Query query, final String s) {
        int sum;
        if (query.u == node.val) {
            return countNodes(node, query.c, s);
        }
        for (Node child : node.children) {
            sum = countNodes(child, query, s);
            if (sum > 0) {
                return sum;
            }
        }
        return 0;
    }

    private int countNodes(final Node node, final char c, final String s) {
        int sum = 0;
        if (c == s.charAt(node.val - 1)) {
            sum = 1;
        }
        for (Node child : node.children) {
            sum += countNodes(child, c, s);
        }
        return sum;
    }

    // Tree Node
    static class Node {
        public int val;
        public List<Node> children;

        public Node() {
            val = 0;
            children = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    static class Query {
        int u;
        char c;
        Query(int u, char c) {
            this.u = u;
            this.c = c;
        }
    }


}
