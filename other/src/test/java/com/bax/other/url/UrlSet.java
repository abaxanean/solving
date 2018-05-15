package com.bax.other.url;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UrlSet {

    public static void main(String[] args) throws MalformedURLException {
        Set<URL> urls = new HashSet<>();
//        urls.add(new URL("http://javapuzzlers.com/"));
//        urls.add(new URL("http://javapuzzlers.com/"));
//        urls.add(new URL("http://google.com/"));
//        System.out.println(urls.size());
//        System.out.println(new URL("http://10.101.0.38/").equals(new URL("http://mobileiron.com/")));
//        System.out.println(LocalDateTime.now().minusWeeks(52).minusDays(2));
//        System.out.println(UUID.fromString("21409634-747E-4BCD-9688-085569B9179D"));
        System.out.println(UUID.randomUUID());
    }

}
