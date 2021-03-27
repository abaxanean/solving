/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

package seekingalpha;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import com.fasterxml.jackson.core.type.TypeReference;

public class Performance {

    private final PriceFetcher priceFetcher;

    public Performance(final PriceFetcher priceFetcher) {
        this.priceFetcher = priceFetcher;
    }

    public static void main(String[] args) throws IOException {
        new Performance(new PriceFetcher()).calculatePeformance(Const.CURRENT);
    }

    private void calculatePeformance(final String author) throws IOException {
        final File file = new File("/Users/abaxanean/projects2/solving/muruz/src/main/resources/" + author + ".json");
        final List<Stock> allStocks = Const.MAPPER.readValue(file, new TypeReference<List<Stock>>() {
        });
        final Map<MonthY, List<Stock>> stocks2020 = allStocks.stream()
//                .filter(s -> s.getDate().getYear() >= 2018)
                .collect(Collectors.groupingBy(s -> new MonthY(s.getDate().getYear(), s.getDate().getMonth())));
        final Map<MonthY, List<Stock>> sortedStocks2020 = new TreeMap<>(stocks2020);
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
            for (int i = 0; i < stocks.size(); i++) {
                Stock stock = stocks.get(i);
                spyChanges[i] = change(stock.getSpyPrice(), spyPrice);
                final double boughtAt = stock.getPrice();
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
            }
            System.out.print(DoubleStream.of(stockChanges).mapToObj(this::doub).collect(Collectors.toList()));
            DoubleStream.of(stockChanges).average().ifPresent(d -> System.out.println(" AVG " + doub(d)));
            System.out.print(DoubleStream.of(spyChanges).mapToObj(this::doub).collect(Collectors.toList()));
            DoubleStream.of(spyChanges).average().ifPresent(d -> System.out.println(" SPY " + doub(d)));
        });
    }

    String doub(double d) {
        return String.format("%6.2f", d);
    }

    double change(final double boughtAt, final double soldAt) {
        return ((soldAt / boughtAt) * 100) - 100;
    }


}
