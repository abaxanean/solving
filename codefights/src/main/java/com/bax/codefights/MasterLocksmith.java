/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.codefights;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MasterLocksmith {

    long masterLocksmith2(long k, long[] initialState) {
        final Set<Long> cache = new HashSet<>(Math.toIntExact(k));
        long min = Long.MAX_VALUE;
        long result = 0L;
        for (long target = 0; target < k; target++) {
            long current = 0L;
            if (cache.add(target)) {
                for (long disc : initialState) {
                    if (target > disc) {
                        current += Math.min(target - disc, k + (disc - target));
                    } else if (target < disc) {
                        current += Math.min(disc - target, k + (target - disc));
                    }

                    if (current >= min) {
                        break;
                    }
                }
                if (current < min) {
                    min = current;
                    result = target;
                }
            }
        }
        return result;
    }

    long masterLocksmith(long k, long[] initialState) {
        Arrays.sort(initialState);
        long minCost = Long.MAX_VALUE;
        long result = 0L;
        long previous = -1;
        for (long target : initialState) {
            long cost = 0L;
            if (previous != target) {
                for (long disc : initialState) {
                    cost += distance(k, disc, target);
                    if (cost >= minCost) {
                        break;
                    }
                }
                if (cost < minCost) {
                    minCost = cost;
                    result = target;
                }
                previous = target;
            }
        }

        long target = 0;
        long cost = 0;
        for (long disc : initialState) {
            cost += distance(k, disc, target);
            if (cost > minCost) {
                break;
            }
        }
        if (cost <= minCost) {
            result = target;
        }

        return result;
    }

    private long distance(final long k, final long disc, final long target) {
        if (target > disc) {
            return Math.min(target - disc, k - target + disc);
        }
        if (target < disc) {
            return Math.min(disc - target, k - disc + target);
        }
        return 0;
    }

    public static void main(String[] args) {
        long result = new MasterLocksmith().masterLocksmith(10, new long[]{2, 7, 1});
        System.out.println(result);

        final Set<Long> cache = new HashSet<>();
        long[] array = new long[]{123456789L, 987654321L, 123456789L};
        for (long l : array) {
            if (!cache.contains(l)) {
                cache.add(l);
                System.out.println(l);
            }
        }
    }

}
