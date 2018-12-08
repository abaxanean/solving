/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

class Player {

    final int ranking;
    final String name;
    final int age;
    final int points;
    final int tourn;

    Player(final int ranking, final String name, final int age, final int points, final int tourn) {
        this.ranking = ranking;
        this.name = name;
        this.age = age;
        this.points = points;
        this.tourn = tourn;
    }

    static Player ofLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            throw new RuntimeException("Invalid line " + line);
        }
        return new Player(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
    }
}
