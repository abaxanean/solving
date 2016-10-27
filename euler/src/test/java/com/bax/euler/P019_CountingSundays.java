/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.testng.annotations.Test;

import static java.time.temporal.TemporalAdjusters.next;
import static org.testng.Assert.assertEquals;

/**
 * https://projecteuler.net/problem=19
 * <pre>
 * You are given the following information, but you may prefer to do some research for yourself.
 *
 * 1 Jan 1900 was a Monday.
 * Thirty days has September,
 * April, June and November.
 * All the rest have thirty-one,
 * Saving February alone,
 * Which has twenty-eight, rain or shine.
 * And on leap years, twenty-nine.
 * A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.
 *
 * How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?
 * </pre>
 */
public class P019_CountingSundays {

    @Test
    public void solve() {
        int count = countSundaysOnFirst(DayOfWeek.SUNDAY, 1);
        assertEquals(count, 171);
    }

    private int countSundaysOnFirst(DayOfWeek dayOfWeek, int dayOfMonth) {
        int count = 0;
        LocalDate localDate = LocalDate.of(1901, 1, 1);
        while (localDate.isBefore(LocalDate.of(2000, 12, 31))) {
            localDate = localDate.with(next(dayOfWeek));
            if (localDate.getDayOfMonth() == dayOfMonth) {
                count++;
            }
        }
        return count;
    }

}
