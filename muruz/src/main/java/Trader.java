/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Trader {

    public static void main(String[] args) {
        new Trader().trade();
    }

    public Trader() {
    }

    final QuotesProvider quotesProvider = new QuotesProviderImpl("/Users/abaxanean/projects2/solving/muruz/src/main/resources/data");
//    final QuotesProvider quotesProvider = null;

    final int LAST_DAYS_COUNT = 5;
    final int HOLD_DAYS_COUNT = 5;
    final int BUY_COUNT = 12;
    final Map<LocalDate, String[]> PURCHASES = new HashMap<>();
    final Map<LocalDate, Double> MONEY = new TreeMap<>(Comparator.<LocalDate>naturalOrder().reversed());

    void trade() {
        final List<LocalDate> allDates = new ArrayList<>(this.quotesProvider.getAllDates());
        allDates.sort(Comparator.naturalOrder());
        for(int todayIndex = 0; todayIndex < allDates.size(); todayIndex++) {
            final int yesterdayIndex = todayIndex - 1;
            final int yesterdayMinusDays = yesterdayIndex - LAST_DAYS_COUNT;
            if (yesterdayMinusDays < 0) {
                continue;
            }
            final LocalDate today = allDates.get(todayIndex);
            final LocalDate yesterday = allDates.get(yesterdayIndex);
            // buy
            final Map<String, Double> startPrices = this.quotesProvider.getPrices(allDates.get(yesterdayMinusDays));
            final Map<String, Double> endPrices = this.quotesProvider.getPrices(yesterday);
            PURCHASES.put(today, findBestPerforming(startPrices, endPrices));
            // sell
            final int indexOfPurchaseDate = todayIndex - HOLD_DAYS_COUNT;
            if (indexOfPurchaseDate < 0) {
                continue;
            }
            final LocalDate purchaseDate = allDates.get(indexOfPurchaseDate);
            if(PURCHASES.containsKey(purchaseDate)) {
                final String[] purchased = PURCHASES.get(purchaseDate);
                final double profit = computeProfit(purchased, this.quotesProvider.getPrices(purchaseDate), this.quotesProvider.getPrices(today));
                final double percent = profit * 100.0 - 100.0;
                System.out.printf("Bought on %s Sold on %s Profit %.2f %n", purchaseDate, today, percent);
                if (!MONEY.containsKey(purchaseDate)) {
                    MONEY.put(purchaseDate, 10_000.00);
                }
                final double amountUsedToPurchase = MONEY.get(purchaseDate);
                final double newAmount = amountUsedToPurchase * profit;
                MONEY.put(today, newAmount);
            }
        }
        System.out.println(MONEY);
    }

    private double computeProfit(final String[] stocks, final Map<String, Double> purchasePrice, final Map<String, Double> sellPrice) {
        return Arrays.stream(stocks)
                .mapToDouble(stock -> sellPrice.get(stock) / purchasePrice.get(stock))
                .average().getAsDouble();
    }

    String[] findBestPerforming(final Map<String, Double> startPrices, final Map<String, Double> endPrices) {
        return endPrices.entrySet().stream()
                .sorted(Comparator.comparingDouble(e -> startPrices.get(e.getKey()) / e.getValue()))
                .limit(BUY_COUNT)
                .map(Map.Entry::getKey)
                .toArray(String[]::new);
//        final Pair[] result = new Pair[BUY_COUNT];
//        int[] index = new int[]{0};
//        endPrices.forEach((stock, price) -> {
//            final Double startPrice = startPrices.get(stock);
//            if (startPrice == null) {
//                System.out.println("Stock not found " + stock);
//                return;
//            }
//            final double delta = price / startPrice;
//            if (index[0] == BUY_COUNT && result[BUY_COUNT-1].) {
//
//            }
//            if (result[index[0]] == null) {
//                result[index[0]] = new Pair(stock, price);
//                index[0]++;
//            }
//        });
    }



    static class Pair {
        final String stock;
        final double price;
        final double delta;

        public Pair(final String stock, final double price, final double delta) {
            this.stock = stock;
            this.price = price;
            this.delta = delta;
        }
    }

}
