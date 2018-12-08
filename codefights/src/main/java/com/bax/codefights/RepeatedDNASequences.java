/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.codefights;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RepeatedDNASequences {

    public static void main(String[] args) {
        Object[] result = new RepeatedDNASequences().repeatedDNASequences("AAAAAAAAAAA");
        System.out.println(Arrays.toString(result));
    }

    Object[] repeatedDNASequences(String s) {
        final Set<String> result = new HashSet<>();
        if (s.length() < 11) {
            return new Object[]{};
        }
        final Node root = new Node();
        for (int i = 0; i <= s.length() - 10; i++) {
            Node n = root;
            int j = 0;
            boolean allPresent = true;
            while (j < 10) {
                Character c = s.charAt(i + j);
                if (!n.hasNode(c)) {
                    allPresent = false;
                }
                n = n.getNode(c);
                j++;
            }
            if (allPresent) {
                result.add(s.substring(i, i + 10));
            }
        }

        return result.stream()
                .sorted().toArray();

    }

    static class Node {
        final Map<Character, Node> map = new HashMap<>();

        boolean hasNode(Character c) {
            if (map.containsKey(c)) {
                return true;
            }
            map.put(c, new Node());
            return false;
        }

        Node getNode(Character c) {
            return map.get(c);
        }
    }

}
