/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

package seekingalpha;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

public class PriceFetcher {

    private static final String HISTORY_QUOTE = "https://finance.api.seekingalpha.com/v2/historical-prices?start=%s&end=%<s&adj=All&symbols%%5B%%5D=%s";
    private static final String HISTORY_QUOTES = "https://finance.api.seekingalpha.com/v2/historical-prices?start=%s&end=%s&adj=All&symbols%%5B%%5D=%s";
    private static final String CURRENT_QUOTE = "https://finance.api.seekingalpha.com/v2/real-time-prices?symbols%%5B%%5D=%s";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<Stock> getPriceOnDay(final List<String> bullish, final LocalDate closestTradingDay) {
        final String priceAtPublicationUrl = String.format(HISTORY_QUOTE, FORMATTER.format(closestTradingDay), String.join(",", bullish) + ",spy");
        final Map quotes;
        try {
            final String priceAtPublicationJson = Jsoup.connect(priceAtPublicationUrl)
                    .ignoreContentType(true)
                    .execute().body();
            quotes = Const.MAPPER.readValue(priceAtPublicationJson, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        final List<Stock> result = new ArrayList<>(bullish.size());
        double[] spyPrice = new double[1];
        for (Object historyQuote : (List)quotes.get("data")) {
            Map quote = (Map)historyQuote;
            final String ticker = (String)quote.get("id");
            final Map attributes = (Map)quote.get("attributes");
            if (attributes == null || attributes.isEmpty()) {
                System.out.println("[WARN] Price not found " + bullish + " " + closestTradingDay);
                continue;
            }
            final Map stockData = (Map)attributes.values().iterator().next();
            // the price the stock closed that day
            final double publicationPrice = number(stockData.get("close"));
//                    closestTradingDay.equals(publicationDay)
//                    ? number(stockData.get("close"))
//                    : number(stockData.get("open"));
            if (publicationPrice < 0.000001d) {
                System.out.println("Zero price found " + bullish + " " + closestTradingDay);
                continue;
            }
            if (ticker.equalsIgnoreCase("SPY")) {
                spyPrice[0] = publicationPrice;
                continue;
            }
            final Stock stock = new Stock();
            stock.setStock(ticker);
            stock.setPrice(publicationPrice);
            stock.setDate(closestTradingDay);
            result.add(stock);
        }
        if (spyPrice[0] < 1.0) {
            throw new RuntimeException("No spy price found " + closestTradingDay);
        }
        result.forEach(stock -> stock.setSpyPrice(spyPrice[0]));
        return result;
    }

    public Map getPricesForStock(final String stock, final LocalDate startDate, final LocalDate endDate) {
        final String priceAtPublicationUrl = String.format(HISTORY_QUOTES, FORMATTER.format(startDate), FORMATTER.format(endDate), stock);
        final Map quotes;
        try {
            final String priceAtPublicationJson = Jsoup.connect(priceAtPublicationUrl)
                    .ignoreContentType(true)
                    .execute().body();
            quotes = Const.MAPPER.readValue(priceAtPublicationJson, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        final List list = Const.getFromMap(quotes, "data");
        return (Map) list.get(0);
    }

    public Map<String, Double> getCurrentPrices(final List<String> bullish) {
//        final List<List<String>> stockLists = Lists.partition(new ArrayList<>(stocksSet), PAGE_SIZE);
        final String currentPrice = String.format(CURRENT_QUOTE, String.join(",", bullish));
        final List currentQuotes;
        try {
            final String currentPriceJson = Jsoup.connect(currentPrice)
                    .ignoreContentType(true)
                    .execute().body();
            currentQuotes = (List)Const.MAPPER.readValue(currentPriceJson, Map.class).get("data");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        final Map<String, Double> result = new HashMap<>(bullish.size());
        for (Object quote : currentQuotes) {
            final Map map = (Map)quote;
            final String stock = (String)map.get("id");
            final Map attributes = (Map)map.get("attributes");
            final Double lastPrice = getLast(attributes);
            if (lastPrice != null) {
                result.put(stock.toUpperCase(), lastPrice);
            } else {

            }
        }
        return result;
    }

    private static Double getLast(final Map attributes) {
        if (attributes.get("last") != null) {
            return number(attributes.get("last"));
        }
        if (attributes.get("previousClose") != null) {
            return number(attributes.get("previousClose"));
        }
        return null;
//        throw new RuntimeException("last price not found");
    }

    private static double number(Object number) {
        return ((Number)number).doubleValue();
    }

}
