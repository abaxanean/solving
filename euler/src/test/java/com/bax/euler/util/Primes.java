/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler.util;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;

public class Primes {

    protected final Deque<Integer> primes = new ArrayDeque<>(Collections.singletonList(2));

    // a function which does have side effects (adds to the primes collection)
    protected boolean isPrime(final int i) {
        // we really need to check for divisors only up to sqrt
        int sqrt = (int)Math.sqrt(i);
        // and we really need to find only prime divisors since any number can be written as a product of prime numbers
        for (int prime : primes) {
            if (i % prime == 0) {
                return false;
            }
            if (prime > sqrt) {
                break;
            }
        }
        primes.addLast(i);
        return true;
    }

    //    private boolean isPrimeSlow(final int number) {
//        int sqrt = (int)Math.sqrt(number);
//        for (int i = 2; i < sqrt; i ++) {
//            if (number % i == 0) {
//                return false;
//            }
//        }
//        return true;
//    }

}
