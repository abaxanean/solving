/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

public class ScorePicker {

    static String[] SCORES = new String[]{
            "Draw 1-1",
            "Belgium 2-1",
            "England 2-1",
            "Draw 2-2",
            "Belgium 1-0",
            "Belgium 2-0",
            "England 1-0",
            "Belgium 3-1",
            "Belgium 3-2",
            "England 2-0",
            "Belgium 3-0",
            "England 3-1",
    };

    public static void main(String[] args) {
        ZoneId zoneId = ZoneId.of("America/Los_Angeles");
        LocalDateTime matchTime = LocalDateTime.of(2018, 7, 14, 7, 0);
        Random random = new Random(ZonedDateTime.of(matchTime, zoneId).toEpochSecond());
        int max = (SCORES.length * (SCORES.length + 1)) / 2;
        System.out.println("Anastasia: " + SCORES[pickIndex(random.nextInt(max))]);
        System.out.println("Alex: " + SCORES[pickIndex(random.nextInt(max))]);

        LocalDateTime AnastasiaBirthday = LocalDateTime.of(1988, 3, 29, 1, 0);
        Random coroana = new Random(ZonedDateTime.of(AnastasiaBirthday, zoneId).toEpochSecond());
        System.out.println("Anastasia: " + (coroana.nextInt(2) + 1));
//        System.out.println("Alex: " + (coroana.nextInt(2) + 1));

        LocalDateTime AlexBirthday = LocalDateTime.of(1989, 4, 17, 10, 0);
        coroana = new Random(ZonedDateTime.of(AnastasiaBirthday, zoneId).toEpochSecond());
        System.out.println("Alex: " + (coroana.nextInt(2) + 1));
    }


    private static int pickIndex(int number) {
        int length = SCORES.length;
        int sum = length;
        int index = 0;
        while (index < length - 1) {
            if (number < sum) {
                return index;
            }
            index++;
            sum += (length - index);
        }
        return index;
    }
}