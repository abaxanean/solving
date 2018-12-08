/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.codefights;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PossibleChampions {

Object[] possibleChampions(int b, int c, int e, int f) {
    if (c == e || b == f) {
        return new String[0];
    }

    String sf1Winner = c > e ? "Croatia" : "England";
    int sf1WinnerGoalsLeft = Math.abs(c - e) - 1;
    String sf2Winner = b > f ? "Belgium" : "France";
    int sf2WinnerGoalsLeft = Math.abs(b - f) - 1;
    List<String> result = new ArrayList<>();
    if (sf1WinnerGoalsLeft > 0) {
        result.add(sf1Winner);
    }
    if (sf2WinnerGoalsLeft > 0) {
        result.add(sf2Winner);
    }
    result.sort(Comparator.naturalOrder());
    return result.toArray();
}

    public static void main(String[] args) {
        Object[] result = new PossibleChampions().possibleChampions(9, 8, 6, 5);
        System.out.println(Arrays.toString(result));
    }

}
