/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.interview;

import java.util.stream.IntStream;

/**
 * On a surface of 1 meter, water drop keeps pouring.
 * 1 drop makes 1 cm of surface wet.
 * Let us know when is is fully wet
 */
public class WaterDrops {

    private static final long ONES = 0xFFFFFFFFFFFFFFFFL;
    private static final int BYTES_PER_INDEX = 64;
    private static final long[] SPACE = new long[((100 * 100) / BYTES_PER_INDEX) + 1];

    public static void main(String[] args) {
        IntStream.range(0, 99).forEach(i -> drops((float) i));
        drops(98.99f);
        System.out.println(drops(0.0f));
//        Arrays.stream(SPACE).forEach(l -> System.out.print(String.format("%64s", Long.toBinaryString(l)).replace(' ', '0')));
    }

    private static boolean drops(float f) {
        int drop = (int)(f * 100);
        mark(drop, 100);
        for (int i = 0; i < (100 * 100) / BYTES_PER_INDEX; i++) {
            if (SPACE[i] != ONES) {
                return false;
            }
        }
        long mask = ONES << (BYTES_PER_INDEX - ((100 * 100) % BYTES_PER_INDEX));
        return (SPACE[((100 * 100) / BYTES_PER_INDEX)] & mask) == mask;
    }

    private static void mark(final int drop, int size) {
        int remainder = drop % BYTES_PER_INDEX;
        int index = drop / BYTES_PER_INDEX;

        markStart(index++, BYTES_PER_INDEX - remainder);
        size -= BYTES_PER_INDEX - remainder;

        while (size >= BYTES_PER_INDEX) {
            markFull(index);
            size -= BYTES_PER_INDEX;
            index++;
        }
        markEnd(index, size);
    }

    private static void markFull(final int index) {
        SPACE[index] = ONES;
    }

    private static void markStart(final int index, final int ones) {
        SPACE[index] |= ONES >>> BYTES_PER_INDEX - ones;
    }

    private static void markEnd(final int index, final int ones) {
        SPACE[index] |= ONES << BYTES_PER_INDEX - ones;
    }
}
