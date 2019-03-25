/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

import java.awt.Dimension;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MuruzNeo {

    private static Set<String> PLAYERS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("Bacs", "Minato", "Funtik", "Astro")));

    public static void main(String[] args) throws IOException, InterruptedException {
//        printTop();
        monitor();

        //        List<Player> players =  getPlayers();
//        group(players, p -> p.location);
    }

    private static void clearScreen() throws IOException {

//        System.out.print(String.format("\033[%dA", 4)); // Move up
//        System.out.print("\033[2K");
//        Runtime.getRuntime().exec("clear");
//        System.out.println("- - - - - - - - - - - -");
        System.out.print("\033[H\033[2J");
    }

    private static void printTop() throws IOException {
        List<Player> playerList = getPlayers();

        playerList.sort(Comparator.naturalOrder());
        int i = 1;
        int index = 0;
        for (Player player : playerList) {
            System.out.println(String.format("%4d %s", i, player));
            if (index == 0 && player.name.equals("Bacs")) {
                index = i;
            }
            i++;
        }

        System.out.println((index * 1.f) / playerList.size());
    }

    private static void monitor() throws InterruptedException, IOException {
        while (true) {
            if (LocalDateTime.now().getMinute() == 24 && new HashSet<>(Arrays.asList(2, 5, 8, 11, 14, 17, 20, 23)).contains(LocalDateTime.now().getHour())) {
                createAndShowGUI("BC");
                return;
            }
            clearScreen();
            List<Map.Entry<String, Integer>> entries = new ArrayList<>();
            Set<String> players = new HashSet<>(PLAYERS);
            List<Player> playerList = getPlayers();
            for (Player player : playerList) {
                if (players.remove(player.name)) {
                    if (player.lvl == 400) {
                        createAndShowGUI(player.name + ' ' + player.lvl);
                        return;
                    } else {
                        entries.add(new AbstractMap.SimpleEntry<>(player.name, player.lvl));
//                        System.out.println(String.format("%10s %s", player.name, player.lvl));
                    }
                }
            }
            if (!players.isEmpty()) {
                createAndShowGUI(players.toString());
                return;
            }
            entries.sort(Comparator.comparing(Map.Entry::getValue));
            entries.stream()
                    .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                    .forEach(e -> System.out.println(String.format("%10s %s", e.getKey(), e.getValue())));
            Thread.sleep(TimeUnit.MINUTES.toMillis(1));
        }
    }

    static <T> void group(List<Player> playerList, Function<Player, T> f) {
        Map<T, Long> map = playerList.stream().collect(Collectors.groupingBy(f, Collectors.counting()));
        List<T> keys = new ArrayList<>(map.keySet());
        keys.sort((key1, key2) -> (int)(map.get(key2) - map.get(key1)));
        for (T key : keys) {
            System.out.println(key + " " + map.get(key));
        }
    }

    private static void createAndShowGUI(final String s) {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(900, 900));

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel(s);
        frame.getContentPane().add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    static class Player implements Comparable<Player> {
        final int res;
        final int lvl;
        final String name;
        final String location;

        public Player(final int res, final int lvl, final String name, final String location) {
            this.res = res;
            this.lvl = lvl;
            this.name = name;
            this.location = location;
        }

        @Override
        public int compareTo(final Player o) {
            if (o.res == this.res) {
                return o.lvl - this.lvl;
            }
            return o.res - this.res;
        }

        @Override
        public String toString() {
            return this.name + ' ' + this.lvl + '[' + this.res + ']';
        }
    }

    private static List<Player> getPlayers() throws IOException {
        Connection.Response response = Jsoup.connect("http://muruz.ru/onlineneo.html").execute();
        Document document = response.parse();
        Element element = document.body().select(".statisticstb").get(0);
        Elements elements = element.select("tr:not(.row1)");
        List<Player> playerList = new ArrayList<>(1000);
        for (Element tr : elements) {
            Elements tds = tr.select("td");
            String res = tds.get(3).text();
            if (res.length() < 3) {
                continue;
            }
            String[] parts = res.split("\\D");
            int resInt = Integer.parseInt(parts[2]);
            if (resInt > 0) {
                playerList.add(new Player(resInt, Integer.parseInt(parts[0]), tds.get(1).text(), tds.get(5).text()));
            }
        }
        return playerList;
    }
}