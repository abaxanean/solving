/*
 * Copyright 2021 Ivanti, Inc.
 * All rights reserved.
 */

package seekingalpha.v3;

import java.io.File;
import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import com.fasterxml.jackson.core.type.TypeReference;

import seekingalpha.Const;
import seekingalpha.MonthY;
import seekingalpha.PriceFetcher;
import seekingalpha.Statistic;
import seekingalpha.Stock;

public class Performance {

    private final PriceFetcher priceFetcher;
    private final Statistic statistic = new Statistic();

    public Performance(final PriceFetcher priceFetcher) {
        this.priceFetcher = priceFetcher;
    }

    public static void main(String[] args) throws IOException {
        new Performance(new PriceFetcher()).compute(Const.CURRENT);
    }

    private void compute(final String author) throws IOException {
        final File file = new File("/Users/abaxanean/projects2/solving/muruz/src/main/resources/" + author + ".json");
        final List<Stock> allStocks = Const.MAPPER.readValue(file, new TypeReference<List<Stock>>() {
        });
        final Map<MonthY, List<Stock>> groupedByMonth = allStocks.stream()
                .collect(Collectors.groupingBy(s -> new MonthY(s.getDate().getYear(), s.getDate().getMonth())));
        final Map<MonthY, List<Stock>> sortedStocks2020 = new TreeMap<>(groupedByMonth);
        final double spyPrice = this.priceFetcher.getCurrentPrices(Collections.singletonList("SPY")).get("SPY");
        sortedStocks2020.forEach((month, stocks) -> {
            System.out.println(month + " =========");
            final Set<String> symbolsSet = stocks.stream()
                    .filter(stock -> stock.getSellDate() == null)
                    .map(Stock::getStock)
                    .collect(Collectors.toSet());
            final Map<String, Double> currentPrices = symbolsSet.isEmpty()
                    ? Collections.emptyMap()
                    :this.priceFetcher.getCurrentPrices(new ArrayList<>(symbolsSet));
            final double[] spyChanges = new double[stocks.size()];
            final double[] stockChanges = new double[stocks.size()];
            final String[] tickers = new String[stocks.size()];
            for (int i = 0; i < stocks.size(); i++) {
                Stock stock = stocks.get(i);
                spyChanges[i] = change(stock.getSpyPrice(), spyPrice);
                final double boughtAt = stock.getPrice();
                tickers[i] = stocks.get(i).getStock();
                if (stock.getSellDate() == null) {
                    if (!currentPrices.containsKey(stock.getStock())) {
                        System.out.println("[WARN] Price not found " + stock.getStock());
                        continue;
                    }
                    stockChanges[i] = change(boughtAt, currentPrices.get(stock.getStock()));
                } else {
                    System.out.printf("Sell %s Gain %.2f%% Bought at %s Sold at %s %s - %s (%s)%n", stock.getStock(), change(stock.getPrice(), stock.getSellPrice()),
                            stock.getPrice(), stock.getSellPrice(),
                            stock.getDate(), stock.getSellDate(),
                            Period.between(stock.getDate(), stock.getSellDate()));
                    stockChanges[i] = change(boughtAt, stock.getSellPrice());
                }
                this.statistic.reportStock(stockChanges[i], spyChanges[i]);
            }
            System.out.print(DoubleStream.of(stockChanges).mapToObj(this::doub).collect(Collectors.toList()));
            DoubleStream.of(stockChanges).average().ifPresent(d -> System.out.print(" AVG " + doub(d)));
//            System.out.print(" vs SPY ");
            DoubleStream.of(spyChanges).average().ifPresent(d -> System.out.println("   SPY " + doub(d)));
            System.out.print(Arrays.stream(tickers).map(this::ticker).collect(Collectors.toList()));
            System.out.println();
//            System.out.print(DoubleStream.of(spyChanges).mapToObj(this::doub).collect(Collectors.toList()));
        });
        System.out.println(statistic.generateReport());
    }

    String ticker(final String stock) {
        return String.format("%6s", stock);
    }

    String doub(final double d) {
        return String.format("%6.2f", d);
    }

    double change(final double boughtAt, final double soldAt) {
        return ((soldAt / boughtAt) * 100) - 100;
    }

    static class Pair<L, R> {
        final L left;
        final R right;

        public Pair(final L left, final R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return this.left;
        }

        public R getRight() {
            return this.right;
        }
    }

}
