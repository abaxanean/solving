package com.bax.codefights;

import java.util.stream.IntStream;

/**
 * <pre>
 * You have a gun that has 3 bullets of different attack powers in its barrel.
 * The gun can only be reloaded once all three bullets have been fired, and it can only be reloaded with bullets of the same type with the same attack powers.
 *
 * There are several targets that you want to shoot down, and each target has a specific sturdiness.
 * Each shot at a target reduces its sturdiness by the value of the bullet's attack power.
 * Once the sturdiness of a target reaches 0, the target falls.
 *
 * Your task is to shoot down all the targets in the minimum number of rounds.
 * In each round, you can shoot at each target with a single bullet. The gun is reloaded after each round.
 *
 * Given the information about bullets and targets, return the minimum number of rounds required to shoot all the targets down.
 * </pre>
 */
public class TheGunner {

    public static void main(String[] args) {
        System.out.println(new TheGunner().theGunner(new int[]{2,2,3}, new int[]{9,9,2}));
    }

    int theGunner(int[] bullets, int[] targets) {
        int sumT = IntStream.of(targets).sum();
        int sumB = IntStream.of(bullets).sorted().skip(3 - targets.length).sum();
        int maxT = IntStream.of(targets).max().orElse(0);
        int maxB = IntStream.of(bullets).max().orElse(1);
        // either by sum or by the rounds needed to shoot the sturdiest target, whichever is bigger
        return Math.max(ceilDiv(sumT, sumB), ceilDiv(maxT, maxB));
    }

    int ceilDiv(int a, int b) {
        return (int)Math.ceil((double)a / b);
    }

}
