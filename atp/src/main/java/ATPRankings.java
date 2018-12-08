/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ATPRankings {

    private static final String FEDERER = "Roger Federer";
    private static final String NADAL = "Rafael Nadal";
    private static final String DJOKOVIC = "Novak Djokovic";
    private static final String MURRAY = "Andy Murray";

    final String template = "http://www.atpworldtour.com/en/rankings/singles?rankRange=0-100&ajax=true&rankDate=";
    LocalDate startDate = LocalDate.of(2001, Month.JANUARY, 1);
    // start of Monday only rankings
    LocalDate firstMonday = LocalDate.of(1985, Month.JANUARY, 14);
    LocalDate end = LocalDate.now().plusDays(1);
    LocalDate confirmed = null;

    final Set<String> BIG_4 = new HashSet<>(Arrays.asList(FEDERER, NADAL, DJOKOVIC, MURRAY));
    LocalDate start = null;
    static final Path PATH = Paths.get("/Users/abaxanean/projects2/solving/atp/src/main/resources/rankings");

    public static void main(String[] args) throws IOException, InterruptedException {
        new ATPRankings().countWeeks();
//        new ATPRankings().downloadRankings();
    }

    private void playerRanking(String player) throws InterruptedException, IOException {
        int previousRanking = -1;
        while ((startDate = startDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY))).isBefore(end)) {
            final String url = template + startDate.getYear() + '-' + startDate.getMonthValue() + '-' + startDate.getDayOfMonth();
            Response response = getResponse(url);
            final Document document = response.parse();
            final Element table = document.select("table").get(0);
            final Elements rows = table.select("tr");
            if (rows.size() == 1) {
                continue;
            }
            for (int i = 1; i <= 4; i++) {
                final Elements columns = rows.get(i).select("td");
                int ranking = Integer.parseInt(columns.get(0).text());
                assert i == ranking;
                String name = columns.get(3).text();
                if (name.equals(player) && previousRanking != ranking) {
                    System.out.println(startDate + " " + ranking);
                    previousRanking = ranking;
                    break;
                }
            }
        }
    }

    private Response getResponse(final String url) {
        Response response = null;
        while (response == null) {
            try {
                response = Jsoup.connect(url).execute();
            } catch (java.net.SocketTimeoutException e) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e1) {
                    throw new RuntimeException("aaa", e1);
                }
                System.out.println("sleep");
            } catch (IOException e) {
                throw new RuntimeException("aaa", e);
            }
        }
        if (response.statusCode() != 200) {
            System.out.println(url);
            throw new RuntimeException(String.format("url: %s code: %s message: %s%n", url, response.statusCode(), response.statusMessage()));
        }
        return response;
    }

    private void computeSpans() throws IOException {
        Month month = null;
        int weeks = 0;
        while ((startDate = startDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY))).isBefore(end)) {
            if (startDate.getMonth() != month) {
                System.out.println("--" + startDate);
                month = startDate.getMonth();
            }
            final String url = template + startDate.getYear() + '-' + startDate.getMonthValue() + '-' + startDate.getDayOfMonth();
            Response response = Jsoup.connect(url).execute();
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
            for (int i = 1; i <= 1; i++) {
                final Elements columns = rows.get(i).select("td");
                int ranking = Integer.parseInt(columns.get(0).text());
                assert i == ranking;
                String name = columns.get(3).text();
                TOP.add(name);
            }
            if (BIG_4.containsAll(TOP)) {
                confirmed = startDate;
                if (start == null) {
                    start = startDate;
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
        System.out.println("last week " + startDate);
    }

    final Map<String, Map<Integer, Integer>> map = new HashMap<>();
    final Map<String, Integer> rankings = new HashMap<>();

    private void computeWeeks() throws IOException, InterruptedException {
        map.put(FEDERER, createMap());
        map.put(NADAL, createMap());
        map.put(DJOKOVIC, createMap());
        map.put(MURRAY, createMap());
        Month month = null;
        while ((startDate = startDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY))).isBefore(end)) {
            if (startDate.getMonth() != month) {
                System.out.println("--" + startDate);
                month = startDate.getMonth();
            }
            final String url = template + startDate.getYear() + '-' + startDate.getMonthValue() + '-' + startDate.getDayOfMonth();
            Response response = getResponse(url);
            final Document document = response.parse();
            final Element table = document.select("table").get(0);
            final Elements rows = table.select("tr");
            if (rows.size() == 1) {
                rankings.forEach((player, ranking) -> {
                    Map<Integer, Integer> counts = map.get(player);
                    counts.put(ranking, counts.get(ranking) + 1);
                });
                continue;
            }
            rankings.clear();
            for (int i = 1; i <= 4; i++) {
                final Elements columns = rows.get(i).select("td");
                int ranking = Integer.parseInt(columns.get(0).text());
                assert i == ranking;
                String name = columns.get(3).text();
                Map<Integer, Integer> counts = map.get(name);
                if (counts == null) {
                    continue;
                }
                counts.put(ranking, counts.get(ranking) + 1);
                rankings.put(name, ranking);
            }
        }
        System.out.println(startDate);
        System.out.println(map);
    }

    private Map<Integer, Integer> createMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 0);
        map.put(2, 0);
        map.put(3, 0);
        map.put(4, 0);
        return map;
    }


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter monthDayFormatter = DateTimeFormatter.ofPattern("MM-dd");

    private void downloadRankings() throws IOException {
        LocalDate previousMonday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        Document rankings = getResponse(template + formatter.format(previousMonday)).parse();
        Elements elements = rankings.select("#filterHolder").select(".dropdown[data-value=rankDate]").select("li[data-value]");
        elements.parallelStream()
                .map(e -> e.attr("data-value"))
                .map(str -> LocalDate.parse(str, formatter))
                .filter(date -> !Files.exists(PATH.resolve(Integer.toString(date.getYear())).resolve(monthDayFormatter.format(date))))
                .map(this::getDocument)
                .map(this::getPlayers)
                .forEach(this::writeToFile);
    }


    void countWeeks() throws IOException {
        LocalDate startDate = LocalDate.of(1973, 8, 23);
        TopCounter counter = new TopCounter();
        final LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        while (startDate.isBefore(nextMonday)) {
            Path path = path(startDate);
            final boolean contentExists = Files.isRegularFile(path) && isNotEmpty(path);
            final boolean isSunday = startDate.getDayOfWeek() == DayOfWeek.SUNDAY;
            if (contentExists) {
                counter.setLines(readLines(path, 4));
            }
            if (isSunday) {
                counter.processWeek(startDate);
            }
            startDate = startDate.plusDays(1);
        }
        final List<Map.Entry<String, Integer>> top4Total = counter.getTop4Total();
        final List<Map.Entry<String, Integer>>[] top4 = counter.getTop4();
        final List<Map.Entry<String, Integer>> combinations = counter.getCombinations();
        System.out.println(top4);
    }

    private boolean isNotEmpty(final Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.readLine() != null;
        }
    }

    private Path path(final LocalDate date) {
        return PATH.resolve(Integer.toString(date.getYear())).resolve(monthDayFormatter.format(date));
    }

    public static List<String> readLines(Path path, int lines) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            List<String> result = new ArrayList<>();
            for (int i = 0; i < lines; i++) {
                String line = reader.readLine();
                if (line == null)
                    break;
                result.add(line);
            }
            return result;
        }
    }

    private void writeToFile(final Map.Entry<LocalDate, List<String>> entry) {
        LocalDate localDate = entry.getKey();
        try {
            Path yearDir = Files.createDirectories(PATH.resolve(Integer.toString(localDate.getYear())));
            Files.write(yearDir.resolve(monthDayFormatter.format(localDate)), entry.getValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map.Entry<LocalDate, List<String>> getPlayers(final Map.Entry<LocalDate, Document> entry) {
        final LocalDate localDate = entry.getKey();
        final List<String> result = entry.getValue().select("tbody").select("tr").stream().map(tr ->
                String.format("%d,%s,%d,%d,%d",
                        getRank(tr),
                        tr.select(".player-cell").text()
                        , parseInt(tr, ".age-cell")
                        , Integer.parseInt(tr.select(".points-cell").text().replace(",", ""))
                        , parseInt(tr, ".tourn-cell")))
                .collect(Collectors.toList());
        return new AbstractMap.SimpleEntry<>(localDate, result);
    }

    private int getRank(final Element tr) {
        return Integer.parseInt(tr.select(".rank-cell").text().trim().replace("T", ""));
    }

    private int parseInt(final Element tr, final String clazz) {
        final String s = tr.select(clazz).text();
        if (s.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    private Map.Entry<LocalDate, Document> getDocument(final LocalDate date) {
        try {
            return new AbstractMap.SimpleEntry<>(date, getResponse(template + formatter.format(date)).parse());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
