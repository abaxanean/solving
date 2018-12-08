/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TopCounter {

    private List<String> lastLines = Arrays.asList("", "", "", "");

    private final Map<String, Integer>[] top4Map = new Map[]{new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()};
    private final Map<String, Integer> top4Total = new TreeMap<>();

    private final Map<String, Integer> combinations = new HashMap<>();
    private String lastCombination = null;
    private LocalDate lastStartDate = null;
    private int weeks = 0;

    void processWeek(LocalDate localDate) {
        count();
        combinations(localDate);
    }

    private void combinations(LocalDate localDate) {
        final Player player1 = Player.ofLine(this.lastLines.get(0));
        final Player player2 = Player.ofLine(this.lastLines.get(1));
        final String pair = Stream.of(player1.name.split(" ")[1], player2.name.split(" ")[1]).sorted().collect(Collectors.joining("-"));
        if (this.combinations.containsKey(pair)) {
            this.combinations.put(pair, this.combinations.get(pair) + 1);
        } else {
            this.combinations.put(pair, 1);
        }

        this.combinations.merge(pair, 1, (existing, one) -> existing++);

        final LocalDate previousSundayMonday = localDate.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        if (!pair.equals(this.lastCombination)) {
            final LocalDate previousMonday = localDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));

            if (this.lastStartDate != null) {
                System.out.println(String.format("| %s || {{dts|%d|%d|%d}} || {{dts|%d|%d|%d}} || %d || %d\n|-", lastCombination,
                        lastStartDate.getYear(), lastStartDate.getMonthValue(), lastStartDate.getDayOfMonth(),
                        previousSundayMonday.getYear(), previousSundayMonday.getMonthValue(), previousSundayMonday.getDayOfMonth(),
                        weeks, this.combinations.get(lastCombination))
                );
            }
            this.lastCombination = pair;
            this.lastStartDate = previousMonday;
            weeks = 0;
        }
        weeks++;
        if (localDate.equals(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)))) {
            System.out.println(String.format("| %s || {{dts|%d|%d|%d}} || {{dts|%d|%d|%d}} || %d || %d", lastCombination,
                    lastStartDate.getYear(), lastStartDate.getMonthValue(), lastStartDate.getDayOfMonth(),
                    previousSundayMonday.getYear(), previousSundayMonday.getMonthValue(), previousSundayMonday.getDayOfMonth(),
                    weeks, this.combinations.get(lastCombination)));
        }
    }

    private void count() {
        for (int i = 0; i < 4; i++) {
            final Player player = Player.ofLine(this.lastLines.get(i));
            final Map<String, Integer> map = top4Map[i];
            map.merge(player.name, 1, (a, b) -> a + b);
            top4Total.merge(player.name, 1, (a, b) -> a + b);
        }
    }

    void setLines(List<String> lines) {
        for (String line : lines) {
            if (line.indexOf(',') > 1) {
                continue;
            }
            int ranking = Character.getNumericValue(line.charAt(0));
            if (ranking < 5) {
                lastLines.set(ranking - 1, line);
            }
        }
    }

    List<Entry<String, Integer>>[] getTop4() {
        List<Entry<String, Integer>>[] result = new List[4];
        for (int i = 0; i < 4; i++) {
            result[i] = sort(top4Map[i]);
        }
        return result;
    }

    List<Entry<String, Integer>> getTop4Total() {
        return sort(top4Total);
    }

    List<Entry<String, Integer>> getCombinations() {
        return sort(combinations);
    }

    private List<Entry<String, Integer>> sort(final Map<String, Integer> top4) {
        return top4.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .collect(Collectors.toList());
    }
}
