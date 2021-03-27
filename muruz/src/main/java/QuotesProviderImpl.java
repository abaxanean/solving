/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seekingalpha.Const;
import seekingalpha.Stock;

import static seekingalpha.Const.assertTrue;

public class QuotesProviderImpl implements QuotesProvider {

    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(DateTimeFormatter.ISO_LOCAL_TIME).toFormatter();

    final Map<LocalDate, Map<String, Double>> DATA = new HashMap<>();

    public QuotesProviderImpl(final String dir) {
        final Path path = Paths.get(dir);
        final Set<String> allStocks = new HashSet<>();
        try {
            Files.list(path).forEach(jsonFile -> {
                try {
                    final byte[] bytes = Files.readAllBytes(jsonFile);
                    final Map map = Const.MAPPER.readValue(bytes, Map.class);
                    final Map<String, Map> attributes = (Map) map.get("attributes");
                    final String stock = (String) map.get("id");
                    allStocks.add(stock);
                    assertTrue(jsonFile.getFileName().toString().equals(stock + ".json"), "json and file do not match " + stock + ' ' + jsonFile.getFileName());
                    attributes.forEach((date, numbers) -> {
                        final LocalDate localDate = LocalDate.parse(date, LOCAL_DATE_TIME_FORMATTER);
                        final double closingPrice = ((Number) numbers.get("close")).doubleValue();
                        if (closingPrice < 0.000001d) {
                            System.out.println("Zero price found " + stock + " " + date);
                            return;
                        }
                        DATA.putIfAbsent(localDate, new HashMap<>());
                        DATA.get(localDate).put(stock, closingPrice);
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DATA.forEach((date, closingPrices) -> {
            if (!closingPrices.keySet().equals(allStocks)) {
                System.out.println("Not present " + Const.diff(allStocks, closingPrices.keySet()));
            }
        });
    }


    @Override
    public Set<LocalDate> getAllDates() {
        return DATA.keySet();
    }

    @Override
    public Map<String, Double> getPrices(final LocalDate date) {
        return this.DATA.get(date);
    }
}
