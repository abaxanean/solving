/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ATPRankings {

    final String template = "http://www.atpworldtour.com/en/rankings/singles?rankRange=0-4&ajax=true&rankDate=";
    LocalDate date = LocalDate.of(2008, Month.AUGUST, 28);
    LocalDate now = LocalDate.now();
    LocalDate confirmed = null;

    final Set<String> BIG_4 = new HashSet<>(Arrays.asList("Roger Federer", "Rafael Nadal", "Novak Djokovic", "Andy Murray"));
    LocalDate start = null;

    public static void main(String[] args) throws IOException {
        new ATPRankings().work();
    }

    private void work() throws IOException {
        Month month = null;
        int weeks = 0;
        while (date.isBefore(now)) {
            date = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            if (date.getMonth() != month) {
                System.out.println("--" + date);
                month = date.getMonth();
            }
            final String url = template + date.getYear() + '-' + date.getMonthValue() + '-' + date.getDayOfMonth();
            Connection.Response response = Jsoup.connect(url).execute();
            if (response.statusCode() != 200) {
                System.out.println(url);
                break;
            }
            final Document document = response.parse();
            final Element table = document.select("table").get(0);
            final Elements rows = table.select("tr");
            if (rows.size() == 1) {
                if (start != null) {
                    weeks++;
                }
                continue;
            }
            final Set<String> TOP = new HashSet<>();
            for (int i = 1; i <= 4; i++) {
                final Elements columns = rows.get(i).select("td");
                int ranking = Integer.parseInt(columns.get(0).text());
                assert i == ranking;
                String name = columns.get(3).text();
                TOP.add(name);
            }
            if (BIG_4.containsAll(TOP)) {
                confirmed = date;
                if (start == null) {
                    start = date;
                    System.out.printf("Start %s\n", start);
                }
            }
            if (!BIG_4.containsAll(TOP) && start != null) {
                System.out.printf("TOP %s %s - %s\n", TOP.size(), start, confirmed);
                start = null;
                System.out.println("" + weeks + " weeks");
                weeks = 0;
            }
            if (start != null) {
                weeks++;
            }
        }
    }

}
