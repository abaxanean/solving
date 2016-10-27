/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Divisors {

    protected Collection<Integer> divisors(int number) {
        Set<Integer> result = new HashSet<>();
        result.add(1);
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                result.add(i);
                result.add(number / i);
            }
        }
        return result;
    }


}
