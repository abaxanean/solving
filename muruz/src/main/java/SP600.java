/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;

import seekingalpha.Const;
import seekingalpha.PriceFetcher;
import seekingalpha.Stock;

public class SP600 {

    public static void main(String[] args) throws IOException {
        new SP600().fetchPrices();
    }

    final PriceFetcher priceFetcher = new PriceFetcher();

    private static List<String> readStocks() throws IOException {
        final byte[] bytes = Files.readAllBytes(Paths.get("/Users/abaxanean/projects2/solving/muruz/src/main/resources/sp-600.json"));
        final List<String> set = Const.MAPPER.readValue(bytes, new TypeReference<List<String>>() {
        });
        return set;
    }

    void fetchPrices() throws IOException {
        final List<String> stocks = readStocks();
        final LocalDate start = LocalDate.of(2020, Month.JANUARY, 1);
        final LocalDate end = LocalDate.of(2021, Month.FEBRUARY, 9);
        final Path DIR = Paths.get("/Users/abaxanean/projects2/solving/muruz/src/main/resources/data");
        for (String stock : stocks) {
            final Map map = this.priceFetcher.getPricesForStock(stock, start, end);
            final Path destination = DIR.resolve(stock + ".json");
            final byte[] bytes = Const.MAPPER.writerWithDefaultPrettyPrinter().writeValueAsBytes(map);
            Files.write(destination, bytes);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
