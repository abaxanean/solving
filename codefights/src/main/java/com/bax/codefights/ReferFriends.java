/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.codefights;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReferFriends {

String[] referFriends(String userInfo) throws IOException {
    Map<String, String>[] array = new ObjectMapper().readValue(userInfo, new TypeReference<Map<String, String>[]>() {
    });
    Map<String, String> users = new HashMap<>();
    Map<String, Integer> win = new HashMap<>();
    for (Map<String, String> map : array) {
        String id = map.get("_id");
        users.put(id, map.get("username"));
        win.put(id, 0);
    }
    for (Map<String, String> map : array) {
        String referrer = map.get("referrerId");
        if (referrer != null) {
            win.put(referrer, win.get(referrer) + 500);
        }
    }
    return win.entrySet().stream()
            .map(e -> users.get(e.getKey()) + " $" + e.getValue())
            .sorted()
            .toArray(String[]::new);
}

    public static void main(String[] args) throws IOException {
        String[] result = new ReferFriends().referFriends(
                "["
                        + "{\"_id\": \"glwer6kn7767spok\",\"username\": \"JohnSmith\",\"country\": \"US\",\"referrerId\": \"wsa2fsf43hfsfdsddd\"},"
                        + "{\"_id\": \"wsa2fsf43hfsfdsddd\",\"username\": \"Michael\",\"country\": \"US\"},"
                        + "{\"_id\": \"tt222hbakq23asddd\",\"username\": \"Frank01\",\"country\": \"UK\",\"referrerId\": \"wsa2fsf43hfsfdsddd\"}"
                        + "]"
        );
        System.out.println(Arrays.toString(result));
    }
}
