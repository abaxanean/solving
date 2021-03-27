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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
// /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/bin/java -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/lib/tools.jar:/Users/abaxanean/projects2/solving/muruz/target/classes:/Users/abaxanean/.m2/repository/commons-io/commons-io/2.5/commons-io-2.5.jar:/Users/abaxanean/.m2/repository/org/jsoup/jsoup/1.11.3/jsoup-1.11.3.jar MuruzNeo
public class MuruzNeo {

    private static Map<String, Boolean> PLAYERS = new HashMap<>();

    static {
//        PLAYERS.put("MoHeTka", true);
        PLAYERS.put("Balbes", true);
//        PLAYERS.put("Trus", true);
//        PLAYERS.put("Byvalyj", true);

//        PLAYERS.put("walcott", true);
//        PLAYERS.put("EsTeT", true);
//        PLAYERS.put("HeBonpoC", true);
    }
//    private static final JFrame frame = createGUI();

    public static void main(String[] args) throws IOException, InterruptedException {
//        printTop();
        monitor();

//                List<Player> players =  getPlayers();
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
//            if (LocalDateTime.now().getMinute() == 24 && new HashSet<>(Arrays.asList(2, 5, 8, 11, 14, 17, 20, 23)).contains(LocalDateTime.now().getHour())) {
//                createAndShowGUI("BC");
//                return;
//            }
            clearScreen();
            List<Map.Entry<String, Integer>> entries = new ArrayList<>();
            Map<String, Boolean> players = new HashMap<>(PLAYERS);
            List<Player> playerList = getPlayers();
            Map<Integer, List<String>> abyss = new HashMap<>();
            abyss.put(1, new ArrayList<>());
            abyss.put(2, new ArrayList<>());
            abyss.put(3, new ArrayList<>());
            for (Player player : playerList) {
                if (player.location.equals("Abyss")) {
                    abyss.get(player.server).add(player.name);
                }
                if (!players.containsKey(player.name)) {
                    continue;
                }
                boolean mma = players.remove(player.name);
                if (player.lvl == 400 && !mma) {
                    createAndShowGUI(player.name + ' ' + player.lvl);
                    return;
                } else {
                    entries.add(new AbstractMap.SimpleEntry<>(player.name, player.lvl));
                }
            }
            abyss.forEach((k,v) -> System.out.printf("%d %s %n", k, v));
            if (!players.isEmpty()) {
                createAndShowGUI(players.keySet().toString());
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
        frame.requestFocus();
    }

//    private static void showNotification(final String message) {
//        frame.setVisible(true);
//        JDialog dialogue =  new JDialog(frame, true);
//        dialogue.setPreferredSize(new Dimension(400, 400));
//        JLabel label = new JLabel(message);
//        frame.getContentPane().add(label);
//        dialogue.getContentPane().add(label);
//        dialogue.pack();
//        dialogue.setVisible(true);
//        frame.requestFocus();
//        dialogue.requestFocus();
//    }

    static class Player implements Comparable<Player> {
        final int res;
        final int lvl;
        final String name;
        final String location;
        final int server;

        public Player(final int res, final int lvl, final String name, final String location, final int server) {
            this.res = res;
            this.lvl = lvl;
            this.name = name;
            this.location = location;
            this.server = server;
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
        Connection.Response response = Jsoup.connect("http://muruz.ru/onlineevo.html").execute();
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
                playerList.add(new Player(resInt, Integer.parseInt(parts[0]), tds.get(1).text(), tds.get(5).text(), Integer.parseInt(tds.get(6).text())));
            }
        }
        return playerList;
    }
}