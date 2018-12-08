/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.codefights;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FormulaOneBet {

    public static void main(String[] args) throws IOException {
        String[][][] bets = new String[][][]{
                {
                        {"massa","2","200"}, {"webber","7","100"}, {"alonso","1","100"}
                },
                {
                        {"massa","1","200"}
                }
        };
        int result = new FormulaOneBet().formulaOneBet(2008, 5, bets);
        System.out.println(result);
    }

    int formulaOneBet(int year, int round, String[][][] usersBets) throws IOException {
        List<String> drivers = loadStandings(year, round);
        int length = usersBets.length;
        // payout per user
        int[] payouts = new int[length];
        // wagered per user
        int[] cost = new int[length];
        int allMoney = 0;
        for (int i = 0; i < length; i++) {
            for (String[] bet : usersBets[i]) {
                int amount = Integer.parseInt(bet[2]);
                allMoney += amount;
                cost[i] += amount;
                if (drivers.get(Integer.parseInt(bet[1]) - 1).equals(bet[0])) {
                    // found winning bet
                    payouts[i] += amount;
                }
            }
        }
        int max = Integer.MIN_VALUE;
        int totalWon = Arrays.stream(payouts).sum();
        if (totalWon == 0) {
            // nobody guessed anything, so the user who spent the least 'earned' the largest amount
            return -Arrays.stream(cost).min().getAsInt();
        }
        for (int j = 0; j < length; j++) {
            int payout = (payouts[j] * allMoney) / totalWon;
            int earned = payout - cost[j];
            if (earned > max) {
                max = earned;
            }
        }
        return max;
    }

    private List<String> loadStandings(final int year, final int round) throws IOException {
        Pattern pattern = Pattern.compile("driverId=\"(\\w+)\"");
        java.net.URL url = new java.net.URL("http://ergast.com/api/f1/" + year + '/' + round + "/driverStandings");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return in.lines().map(pattern::matcher)
                    .filter(Matcher::find)
                    .map(matcher -> matcher.group(1))
                    .collect(Collectors.toList());
        }
    }

}
