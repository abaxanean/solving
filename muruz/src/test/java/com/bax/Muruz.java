package com.bax;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

public class Muruz {

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final String character = System.getProperty("nick");
    private final URL url = new URL("http://muruz.ru/onlinesx2.html");

    private int firstLevel = -1;
    private int level;
    private long time = System.currentTimeMillis();

    public Muruz() throws MalformedURLException {
    }

    @Test
    public void test() {
        int exp = 60;
        int points = 1000;
        int totalPoints = 0;
        int res=0;
        while(exp >=1 && points > 0) {
            res++;
            totalPoints+=points;
            exp--;
            points -=10;
        }
        System.out.printf("res %d  total points %d", res, totalPoints);
    }

    @Test
    public void track() throws IOException, InterruptedException {
        System.out.println(character);
        while (true) {
            run();
            Thread.sleep(computeDelay());
        }
    }

    private void run() {
        String html = getHtml();
        int index = html.indexOf("<td>" + character + "</td>");
        if (index == -1) {
            System.out.println("Character not online !!!");
            return;
        }
        int start = index + 40 + character.length();
        String l = html.substring(start, start + 3);
        int newLevel = Integer.parseInt(l);
        if (firstLevel == -1 || level > newLevel) {
            firstLevel = newLevel;
            time = System.currentTimeMillis();
            System.out.println("Counting from " + firstLevel);
        }
        if (newLevel != level) {
            double rate = (System.currentTimeMillis() - time) / Math.max(newLevel - firstLevel, 1);
            level = newLevel;
            System.out.println(String.format("%s %d %.1f", df.format(LocalDateTime.now()), level, rate / 1000));
        } else {
            System.out.println("Level did not change");
        }

    }

    private String getHtml() {
        try {
            return IOUtils.toString(url.openStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new UncheckedIOException(e);
        }
    }

    private long computeDelay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.withSecond(30).plusMinutes(5 - now.getMinute() % 5);
        while (next.isBefore(now)) {
            next = next.plusMinutes(1);
        }
        return ChronoUnit.MILLIS.between(now, next);
    }

    @Test
    public void lvlPoints() {
        int points = (5 * 219) + (180 * 6);
        int pointsPerLevel = 6;
        int perStat = 0;
        int lvl = 400;
        int exp = 50;
        while(perStat < 32_000) {
            lvl++;
            pointsPerLevel++;
            if (exp > 1) {
                exp--;
            }
            points+= pointsPerLevel;
            perStat = points / 4;
            System.out.printf("%d | %d %d   %d %n", exp, points, perStat, lvl);
        }

    }
}
