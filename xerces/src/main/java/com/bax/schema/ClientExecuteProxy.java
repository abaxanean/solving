/*
 * Copyright 2017 Mobile Iron, Inc.
 * All rights reserved.
 */

package com.bax.schema;

import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ClientExecuteProxy {

    public static void main(String[] args) throws Exception {

        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("storeedgefd.dsx.mp.microsoft.com")
                .setPath("/pages/searchResults")
                .setParameter("appVersion", "2016.29.13.0")
                .setParameter("market", "US")
                .setParameter("locale", "en-US")
                .setParameter("deviceType", "w")
                .setParameter("screenSize", "L")
                .setParameter("query", "money")
                .build();

        URI mifs = new URIBuilder()
                .setScheme("https")
                .setHost("app770.auto.mobileiron.com")
                .setPath("/mifs/login.jsp")
                .setParameter("query", "money")
                .build();


        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            HttpHost proxy = new HttpHost("proxy-noauth.qa.mobileiron.com", 3128, "http");

            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .setAuthenticationEnabled(false)
                    .build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("accept", "application/json");
            request.setHeader("user-agent", "WindowsStore/2016.29.13.0");
//            request.setHeader("ms-contract-version", "4");
            request.setConfig(config);

            System.out.println("Executing request " + request.getRequestLine() + " via " + proxy);

            try (CloseableHttpResponse response = httpclient.execute(request)) {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        }
    }
}

/**
 * Ford         5
 * Chevrolet    3
 * Ram          1
 * Nissan       3
 * Honda        3
 * Toyota       4
 * Jeep         1
 */

