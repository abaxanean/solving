/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.interview;

public class TrainStops {

    public static void main(String[] args) {
        System.out.println(minimumNumberOfStops(10, 27));
    }

    public static int minimumNumberOfStops(int start, final int end) {
        assert start < end;
        int stops = 1;
        while (start < end) {
            start += findClosestPowerOfTwoLessOrEqualThan(end - start);
            stops++;
        }
        return stops;
    }

    private static int findClosestPowerOfTwoLessOrEqualThan(final int i) {
        assert i > 0;
        return i == 1 ? 1 : 2 << (30 - Integer.numberOfLeadingZeros(i));
    }
}
