/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler.util;

import java.util.function.Function;

/**
 * TODO: Document this class.
 */
public class Util {

    public static <T> T[][] parseArray(String s, T[][] array, Function<String, T> f) {
        String[] lines = s.split("\n");
        for (int i = 0; i < array.length; i++) {
            String[] numbers = lines[i].split(" ");
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = f.apply(numbers[j]);
            }
        }
        return array;
    }

}
