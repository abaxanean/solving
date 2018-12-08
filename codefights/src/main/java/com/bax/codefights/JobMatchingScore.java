/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.codefights;

public class JobMatchingScore {

    public static void main(String[] args) {
        int score = new JobMatchingScore().jobMatchingScore(new String[]{"San Francisco, California, United States",
                "San Mateo, California, United States",
                "Columbia, South Carolina, United States",
                "Columbia, California, United States"}, "United States");
        System.out.println(score);
    }

int jobMatchingScore(String[] locations, String criteria) {
    String[] c = criteria.split(", ");
    String country = c[c.length - 1];
    String state = c.length > 1 ? c[c.length - 2] : null;
    String city = c.length > 2 ? c[c.length - 3] : null;

    int result = 0;
    for (String location : locations) {
        String[] l = location.split(", ");
        if ((city == null || city.equals(l[0]))
                && (state == null || state.equals(l[1]))
                && country.equals(l[2])) {
            result++;
        }
    }
    return result;

}

    boolean eq(String s1, String s2) {
        return s1 == null || s1.equals(s2);
    }

}
