/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

package seekingalpha.v3;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;

import seekingalpha.Const;

public class SentimentLoader {
    // https://seekingalpha.com/api/v3/articles/4410721?include=author%2Cauthor.authorResearch%2Cco_authors%2CprimaryTickers%2CsecondaryTickers%2CotherTags%2Cpresentations%2Cpresentations.slides%2Csentiments
    private static final String SENTIMENT = "https://seekingalpha.com/api/v3/articles/%d?include=author%%2Cauthor.authorResearch%%2Cco_authors%%2CprimaryTickers%%2CsecondaryTickers%%2CotherTags%%2Cpresentations%%2Cpresentations.slides%%2Csentiments";
//    private static final String SENTIMENTOTHER = "https://seekingalpha.com/api/v3/sentiments?filter[tag_id]=156603&filter[author_id]=105703&include=article&page[size]=5";


    List<Ticker> loadSentiments(final int articleId) {
        try {
            final String sentimentJson = Jsoup.connect(String.format(SENTIMENT, articleId))
                    .header("cookie", "_sasource=; session_id=09708426-82b0-4630-bf0b-1c7b9f69d243; machine_cookie=2994519254799; __pat=-18000000; _fbp=fb.1.1609369198163.1628992116; _pxvid=e2a93113-4af2-11eb-8e87-0242ac12000f; g_state={\"i_l\":0}; user_id=52980618; user_devices=; u_voc=; marketplace_author_slugs=; user_perm=; user_remember_token=e0c602c582d9c3c7e6dbbeb8193c263616f1c208; __tac=; has_paid_subscription=true; ever_pro=1; sapu=12; portfolio_sort_type=a_z; _pxhd=9b81b7053d831d0e418b92698dce0fc88c8297e1e67eb88e98fefc26b9d3b6ac:80650f60-6b3b-11e9-814e-41aaaa844f02; user_nick=bacs; ubvt=50.35.228.1111611976589996096; __tae=1611790131881; user_cookie_key=dp455z; _sapi_session_id=eyJzZXNzaW9uX2lkIjoiNGI0YTZhM2UyZTBhZjdmNWFmMDk5NzgyZGQ2NTE0NzEiLCJmbGFzaCI6eyJkaXNjYXJkIjpbXSwiZmxhc2hlcyI6eyJub3RpY2UiOm51bGx9fX0%3D--2931810775cf0a2c81e05c67f84cb36476232d28; __tbc=%7Bjzx%7DlCp-P5kTOqotpgFeypItnFOQW0ajdYImM_pO-7tRpq1N3iH5hjj1TGpG9l0229xNuOmp_YMx-xgu7-hy1t125uB4vFgjujlSvHobLdPOD1mfFMMsSw6ZT-2kOKGBspLhoA2mdTePIi02Kuzq68YqAQ; session_id=09708426-82b0-4630-bf0b-1c7b9f69d243; gk_user_access=1*archived*1611871039; gk_user_access_sign=eaf45e6d144cccef4ef6d9b0c07cfb8927b155e3; __pvi=%7B%22id%22%3A%22v-2021-01-28-13-57-00-923-F7zY0C7g5caWECNB-b2b0bb31d66b8385f3439796c3d0b24e%22%2C%22domain%22%3A%22.seekingalpha.com%22%2C%22time%22%3A1611871703600%7D; xbc=%7Bjzx%7DfH6OftjRj7gPec54uNF38qIeIfnBcBLVH5xTnNSbiS9LXeA04eUkTLmmAqo_rJ19w5J7L5t_707o8I4C3yp8fXzOFmww2lxigMSZpcWiBn2bfNT0Xp0UoWl2vruKTZ3RmzxFNGKoJLTFNsWaNuKKzvbCxsfNkmWBhW0XjVbTpkbiMuY-oBOROcsdmkGGVOgi_nSkVSSgxvLFXQ-SNY3-_ssWFslk6SK6BFEfPlYy8BcIX-sv-7ARshc1HzvjbOnhAnvXD9jwq941kFo7uhu1oURiRjG1uynaV7qxQdH_SuQW0GpMt9ypl3DSs8v3gKgH4LSJecPXqQDxC5IrRc3o6UWMu0cr7pZI4PEvZDO4Ex37w-lrjjydn9-CwWnOQqKOEXI8PZmOFrZ_n8cSeo99R6Ntvg4v3IKG-RpiBvC4yc9exWiGMGSVqy2fq4TkNX7p8HvHcJgI1TH60suoEuaPcsonGJP_EzlIJPIahZvwB7wyVRh9rJIz12FDTHAQ_TQ6u787a43Hf8r0gaCzgnwebiugKuyttNYmrc4IOlOrFtZRuRVftAsWnW0DeNm8BIlGHmWAEFOFDNPoEoq4KMHmxIccWqQMFbKEV8_xKUGPWT2Cgl3YvY2EMq75p2Z9_b57wY6-eULuvwUuxahZAtqZy42snUgV-r6Ovkm9a7FVw70ntWS_WOclBFzsm0LrzxkNXwvhFKCBqTcp0t5W9F3wz_ocesbNui1jE1hinvLJdqn0tDtIW99_JE2lPwsraO04_OQmxQILn12BGPpWcv9ukL-saR6-EVmUAYo0sd5PoD6PD3WkVNuE9PuvCEYYYO5UxnbJZrNmMeGJO7-dGGHu1VUichQ80QEQEvdT3Q-FqhO6BjHtqnjjbIs5AWIuBkIGtriDSrJrn3pai0Ph0mBZ3Q")
                    .ignoreContentType(true)
                    .execute().body();
            final Map map = Const.MAPPER.readValue(sentimentJson, Map.class);
            return getSentiments(map);
        } catch (IOException e) {
            throw new RuntimeException("Article not found " + articleId);
        }
    }

    private List<Ticker> getSentiments(final Map map) throws IOException {
//        final List tickers = getFromMap(map, "data", "primaryTickers", "data");
//        if (tickers.size() > 1) {
//            System.out.println("More than 1 tickers " + getFromMap(map,"data", "attributes", "title"));
//            return null;
//        }
        final List<?> included = getFromMap(map, "included");
        final List<Sentiment> sentiments = included.stream().map(i -> (Map) i)
                .filter(m -> m.get("type").equals("sentiment"))
                .map(m -> (String) getFromMap(m, "attributes", "type"))
                .map(Sentiment::valueOf)
                .collect(Collectors.toList());

        if (sentiments.isEmpty()) {
            System.out.println("[DEBUG] No sentiment " + getFromMap(map,"data", "attributes", "title"));
            return null;
        }
        if (sentiments.size() > 1 && new HashSet<>(sentiments).size() > 1) {
            System.out.println("[WARN] Different tickers sentiment " + getFromMap(map,"data", "attributes", "title"));
            return null;
        }
//        final String sentiment = getFromMap(sentiments.get(0), "attributes", "type");
//        final String sentiment = sentimentO.get();
        final List<Map> symbolMap = included.stream().map(i -> (Map) i)
                .map(m -> (Map)m.get("attributes"))
                .filter(Objects::nonNull)
                .filter(m -> m.containsKey("tagKind") && m.get("tagKind").equals("Tags::Ticker"))
                .collect(Collectors.toList());
        if (sentiments.size() != symbolMap.size()) {
            System.out.println("[INFO] Check the article is " + sentiments.get(0) + " on " +
                    symbolMap.subList(0, new HashSet<>(sentiments).size()).stream()
                            .map(s -> (String) s.get("name")).collect(Collectors.joining(","))
            + " " + getFromMap(map,"data", "attributes", "title"));
        }
        final List<Ticker> result = new ArrayList<>(sentiments.size());
        final String closestTradingDate = getFromMap(map, "data", "attributes", "closestTradingDate");
        final LocalDate closestTradingDay = LocalDate.parse(closestTradingDate);
        for (int i = 0; i < Math.min(symbolMap.size(), sentiments.size()); i++) {
            final String symbol = (String) symbolMap.get(i).get("name");
            result.add(new Ticker(symbol.toUpperCase(), sentiments.get(i), closestTradingDay));
        }
        return result;
    }

    <T> T getFromMap(final Map map, String... path) {
        Map current = map;
        for (int i = 0; i < path.length - 1; i++) {
            current = (Map) current.get(path[i]);
        }
        return (T) current.get(path[path.length - 1]);
    }

    static class Ticker {
        private final String stock;
        private final Sentiment sentiment;
        private final LocalDate closestTradingDay;

        public Ticker(final String stock, final Sentiment sentiment, final LocalDate closestTradingDay) {
            this.stock = stock;
            this.sentiment = sentiment;
            this.closestTradingDay = closestTradingDay;
        }

        public String getStock() {
            return this.stock;
        }

        public Sentiment getSentiment() {
            return this.sentiment;
        }

        public LocalDate getClosestTradingDay() {
            return this.closestTradingDay;
        }
    }

    enum Sentiment {
        very_bullish(true),
        bullish(true),
        neutral,
        bearish,
        very_bearish;

        final boolean isBullish;

        Sentiment() {
            this(false);
        }

        Sentiment(final boolean isBullish) {
            this.isBullish = isBullish;
        }

        boolean isBullish() {
            return this.isBullish;
        }
    }
    
}
