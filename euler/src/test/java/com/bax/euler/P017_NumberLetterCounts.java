/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.stream.IntStream;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=17
 * <pre>
 *  If the numbers 1 to 5 are written out in words: one, two, three, four, five, then there are
 *  3 + 3 + 5 + 4 + 4 = 19 letters used in total.
 *
 *  If all the numbers from 1 to 1000 (one thousand) inclusive were written out in words, how many letters would be used?
 *
 *  NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and forty-two) contains 23 letters and 115
 *  (one hundred and fifteen)contains 20 letters. The use of "and" when writing out numbers is in compliance with British usage.
 * </pre>
 */
public class P017_NumberLetterCounts {

    @Test
    public void solve() {
        assertEquals(lettersCount(1000), 21124);
    }

    private int lettersCount(final int to) {
        return IntStream.rangeClosed(1, to)
                .map(this::characters)
                .sum();
    }

    private int characters(int originalNumber) {
        int number = originalNumber;
        int sum = 0;
        int thousands = number / 1000;
        if (thousands > 0) {
            Spelling spelling = Spelling.of(thousands);
            sum += spelling.units + 8; // number + thousand
            number %= 1000;
        }
        int hundreds = number / 100;
        if (hundreds > 0) {
            Spelling spelling = Spelling.of(hundreds);
            sum += spelling.units + 7; // number + hundred
            number %= 100;
        }
        int dozens = number / 10;
        if (dozens == 1) {
            sum += tenToNineTeen(number);
            number = 0;
        } else if (dozens > 0) {
            sum += Spelling.of(dozens).dozens;
            number %= 10;
        }
        if (hundreds > 0 && (dozens > 0 || number > 0)) {
            sum += 3; // and
        }
        return sum + Spelling.of(number).units;
    }

    private int tenToNineTeen(final int number) {
        switch (number) {
            case 10:
                return 3;
            case 11:
            case 12:
                return 6;
            case 13:
            case 18:
                return 8;
            case 15:
                return 7;
            case 16:
            case 14:
            case 17:
            case 19:
                return Spelling.of(number % 10).units + 4; // number + teen
            default:
                throw new IllegalArgumentException();
        }
    }


    enum Spelling {
        ZERO(0, 0),
        ONE(3, 3),
        TWO(3, 6),
        THREE(5, 6),
        FOUR(4, 5),
        FIVE(4, 5),
        SIX(3, 5),
        SEVEN(5, 7),
        EIGHT(5, 6),
        NINE(4, 6);

        static Spelling[] values = Spelling.values();

        final int units;
        final int dozens;

        Spelling(final int units, final int dozens) {
            this.units = units;
            this.dozens = dozens;
        }

        public static Spelling of(int i) {
            return values[i];
        }
    }
}
