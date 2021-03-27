package seekingalpha;/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.core.JsonProcessingException;

public class SeekingAlpha {

    private static final String SITE = "https://seekingalpha.com";
    private static final String ARTICLES = SITE + "/author/%s/ajax_load_regular_articles?lt=%d&page=%d&author=true&sort=recent&rta=true";

    private final PriceFetcher priceFetcher;

    public SeekingAlpha(final PriceFetcher priceFetcher) {
        this.priceFetcher = priceFetcher;
    }

    public static void main(String[] args) throws IOException {
        new SeekingAlpha(new PriceFetcher()).fetchData(Const.CURRENT);
//            final Map<String, Double> pricesAtPublication = getPriceOnDay(bullish, closestTradingDay);
//            final Map<String, Double> currentPrices = getCurrentPrices(bullish);

//            pricesAtPublication.forEach((stock, price) -> {
//                final double currentPrice = currentPrices.get(stock);
//                final double change = ((currentPrice / price) - 1) * 100;
//                System.out.printf("%s %.2f%n", stock, change);
//            });
    }

    void fetchData(final String author) throws IOException {
        final List<String> hrefs = getArticleUrls(author);
        final List<Stock> stocks = new ArrayList<>();
        final Map<String, LocalDate> sell = new HashMap<>();
        for (String href : hrefs) {
            Document document = Jsoup.connect(SITE + href)
                    .header("cookie", "_sasource=; session_id=09708426-82b0-4630-bf0b-1c7b9f69d243; machine_cookie=2994519254799; __pat=-18000000; _fbp=fb.1.1609369198163.1628992116; _pxvid=e2a93113-4af2-11eb-8e87-0242ac12000f; g_state={\"i_l\":0}; user_id=52980618; user_devices=; u_voc=; marketplace_author_slugs=; user_perm=; user_remember_token=e0c602c582d9c3c7e6dbbeb8193c263616f1c208; __tac=; has_paid_subscription=true; ever_pro=1; sapu=12; portfolio_sort_type=a_z; _pxhd=9b81b7053d831d0e418b92698dce0fc88c8297e1e67eb88e98fefc26b9d3b6ac:80650f60-6b3b-11e9-814e-41aaaa844f02; user_nick=bacs; ubvt=50.35.228.1111611976589996096; __tae=1611790131881; user_cookie_key=dp455z; _sapi_session_id=eyJzZXNzaW9uX2lkIjoiNGI0YTZhM2UyZTBhZjdmNWFmMDk5NzgyZGQ2NTE0NzEiLCJmbGFzaCI6eyJkaXNjYXJkIjpbXSwiZmxhc2hlcyI6eyJub3RpY2UiOm51bGx9fX0%3D--2931810775cf0a2c81e05c67f84cb36476232d28; __tbc=%7Bjzx%7DlCp-P5kTOqotpgFeypItnFOQW0ajdYImM_pO-7tRpq1N3iH5hjj1TGpG9l0229xNuOmp_YMx-xgu7-hy1t125uB4vFgjujlSvHobLdPOD1mfFMMsSw6ZT-2kOKGBspLhoA2mdTePIi02Kuzq68YqAQ; session_id=09708426-82b0-4630-bf0b-1c7b9f69d243; gk_user_access=1*archived*1611871039; gk_user_access_sign=eaf45e6d144cccef4ef6d9b0c07cfb8927b155e3; __pvi=%7B%22id%22%3A%22v-2021-01-28-13-57-00-923-F7zY0C7g5caWECNB-b2b0bb31d66b8385f3439796c3d0b24e%22%2C%22domain%22%3A%22.seekingalpha.com%22%2C%22time%22%3A1611871703600%7D; xbc=%7Bjzx%7DfH6OftjRj7gPec54uNF38qIeIfnBcBLVH5xTnNSbiS9LXeA04eUkTLmmAqo_rJ19w5J7L5t_707o8I4C3yp8fXzOFmww2lxigMSZpcWiBn2bfNT0Xp0UoWl2vruKTZ3RmzxFNGKoJLTFNsWaNuKKzvbCxsfNkmWBhW0XjVbTpkbiMuY-oBOROcsdmkGGVOgi_nSkVSSgxvLFXQ-SNY3-_ssWFslk6SK6BFEfPlYy8BcIX-sv-7ARshc1HzvjbOnhAnvXD9jwq941kFo7uhu1oURiRjG1uynaV7qxQdH_SuQW0GpMt9ypl3DSs8v3gKgH4LSJecPXqQDxC5IrRc3o6UWMu0cr7pZI4PEvZDO4Ex37w-lrjjydn9-CwWnOQqKOEXI8PZmOFrZ_n8cSeo99R6Ntvg4v3IKG-RpiBvC4yc9exWiGMGSVqy2fq4TkNX7p8HvHcJgI1TH60suoEuaPcsonGJP_EzlIJPIahZvwB7wyVRh9rJIz12FDTHAQ_TQ6u787a43Hf8r0gaCzgnwebiugKuyttNYmrc4IOlOrFtZRuRVftAsWnW0DeNm8BIlGHmWAEFOFDNPoEoq4KMHmxIccWqQMFbKEV8_xKUGPWT2Cgl3YvY2EMq75p2Z9_b57wY6-eULuvwUuxahZAtqZy42snUgV-r6Ovkm9a7FVw70ntWS_WOclBFzsm0LrzxkNXwvhFKCBqTcp0t5W9F3wz_ocesbNui1jE1hinvLJdqn0tDtIW99_JE2lPwsraO04_OQmxQILn12BGPpWcv9ukL-saR6-EVmUAYo0sd5PoD6PD3WkVNuE9PuvCEYYYO5UxnbJZrNmMeGJO7-dGGHu1VUichQ80QEQEvdT3Q-FqhO6BjHtqnjjbIs5AWIuBkIGtriDSrJrn3pai0Ph0mBZ3Q")
                    .ignoreContentType(true)
                    .execute().parse();
            List<DataNode> scripts = document.getElementsByTag("script").dataNodes();
            final String script = findSentiment(scripts);
            if (script == null) {
                System.out.println("No script " + document.title());
                continue;
            }
            Map pageConfig = (Map)Const.MAPPER.readValue(script, Map.class).get("pageConfig");
            Map data = (Map)pageConfig.get("Data");
            final Map<String, Integer> sentimentMap = sentiment(data);
            if (sentimentMap.isEmpty()) {
                System.out.println("No symbols sentiment " + document.title());
                continue;
            }
            final Map articleData = (Map)data.get("article");
//            final String publicationDays = (String)articleData.get("article_datetime");
            final String closestTradingDays = (String)articleData.get("closest_trading_day");
            final LocalDate closestTradingDay = LocalDate.parse(closestTradingDays);
            final List<String> bullish = sentimentMap.keySet().stream()
                    .filter(e -> sentimentMap.get(e) < 2)
                    .collect(Collectors.toList());
            sentimentMap.keySet().stream()
                    .filter(e -> sentimentMap.get(e) >= 2)
                    .forEach(stock -> sell.put(stock.toUpperCase(), closestTradingDay));
            if (bullish.isEmpty()) {
//                System.out.println("No bullish sentiment " + sentimentMap);
                continue;
            }

            final List<Stock> priceOnDay = this.priceFetcher.getPriceOnDay(bullish, closestTradingDay);
            priceOnDay.forEach(s -> {
                if (sell.containsKey(s.getStock())) {
                    final LocalDate sellDate = sell.get(s.getStock());
                    s.setSellDate(sellDate);
                    final List<Stock> sellQuote = this.priceFetcher.getPriceOnDay(Collections.singletonList(s.getStock()), sellDate);
                    if (!sellQuote.isEmpty()) {
                        s.setSellPrice(sellQuote.get(0).getPrice());
                    }
                }
            });
            stocks.addAll(priceOnDay);
        }


        Const.MAPPER.writerWithDefaultPrettyPrinter().writeValue(new File("/Users/abaxanean/projects2/solving/muruz/src/test/resources/" + author + ".json"), stocks);
    }

    private static List<String> getArticleUrls(final String author) throws IOException {
        final List<String> hrefs = new ArrayList<>();
        int totalPages = 1;
        boolean skipCurrentMonth = true;
        for (int page = 0; page < totalPages; page++) {
            Connection.Response response = Jsoup.connect(String.format(ARTICLES, author, page, page))
                    .header("cookie", "machine_cookie=2994519254799; __pat=-18000000; _fbp=fb.1.1609369198163.1628992116; _pxvid=e2a93113-4af2-11eb-8e87-0242ac12000f; g_state={\"i_l\":0}; user_id=52980618; user_nick=; user_devices=; u_voc=; marketplace_author_slugs=; user_perm=; user_remember_token=e0c602c582d9c3c7e6dbbeb8193c263616f1c208; __tac=; has_paid_subscription=true; ever_pro=1; sapu=12; portfolio_sort_type=a_z; _sapi_session_id=eyJzZXNzaW9uX2lkIjoiNGI0YTZhM2UyZTBhZjdmNWFmMDk5NzgyZGQ2NTE0NzEifQ%3D%3D--904e72e2925f1b4a282f80f807f445780b109483; __tbc=%7Bjzx%7DlCp-P5kTOqotpgFeypItnFOQW0ajdYImM_pO-7tRpq1N3iH5hjj1TGpG9l0229xNuOmp_YMx-xgu7-hy1t125uB4vFgjujlSvHobLdPOD1mfFMMsSw6ZT-2kOKGBspLhoA2mdTePIi02Kuzq68YqAQ; user_cookie_key=vnuvra; _pxhd=9b81b7053d831d0e418b92698dce0fc88c8297e1e67eb88e98fefc26b9d3b6ac:80650f60-6b3b-11e9-814e-41aaaa844f02; __tae=1611341548903; session_id=d14cb983-bdf6-4260-ae4c-0c0a7b362c20; gk_user_access=1*archived*1611386230; gk_user_access_sign=7ce3360ef3b3f73d193b00117ecdb63f82202f8f; __pvi=%7B%22id%22%3A%22v-2021-01-22-23-11-19-071-ylP7T3CLxYXrkzHc-19d31cf47b6d9ccb0d09523bb05eba99%22%2C%22domain%22%3A%22.seekingalpha.com%22%2C%22time%22%3A1611386231170%7D; xbc=%7Bjzx%7DfH6OftjRj7gPec54uNF38vKZJkQEY0I_x67nKK6OTLSH0JB4xz92Gfm74KBA8PbBkTGRL3sIBe3kUnADzgZrbRMzb2Mo6Q1AxwETwW8PihhUajbRszf4mKxgAGS3rEU9rcmUjv7jHQoC0M8q7OfHHmm9iZrIVm8OBJya92QI4u7he07rfWxYjVAhQEdDz_meW-OXGSA8RIL6uDSsAt7AoMCu6JWctYVddxAsbYs9yiTVoO-8jTSfTDB_nRSJSjkPwdrjMBBUnUyUzK7fKx0-m7G-QoQlpNOoVnltNMl_ZGprMve3dAQ4A54mDQZL9TKL__SrjbvKk9dRgkdZVSnHMigGukaoZuD3Lsjz_byxBMv-KJX1E9s9qDiyl3T_Mfj94Gh4HTLQU23CdD6MMkaAtA1mKzJUaUcHYZm6CHugmpH2YCbUPtx1_9P3P_x-qw5RS5cTfYmUf_4rrTL5ewdk3YX2MBrB9I6gux8mCz7AmSx1YeazAhGkczrjucMqkVamL8MW8L2jmbn0oMTHbZfzlfpiM_Wyjsg2EeW8LZI7S7zh5z2Tu7fam57LWUaU9u8D09WnzXag36bQjCPDUXes7wAjczotX2YGLKNEIcm3k_AO9-0UPlxFcj9XM9-AACu-_3DNrz5dj4X0-3o9TbrkKrzgQgOwJoSrYOvKAk9a4QpyFdNc7rilkcBB1O55LTCNNDcl_ZM4wJ66fmpU4LVv5EmHeI3dKJgCU0N2xO2XUMos7vnEucHeeARggBlI9v2Z0ip8bxGFtQFOLiepPFBzU70WniC0vcYd5WWn5cH5wxf7f5C187wnu_XfhrcXHF_evNo46KWVX7C1yYBASLP6Y1UichQ80QEQEvdT3Q-FqhOdNaRPJKi41PtfkhcG3LXujVUtKRhBLG1FQRvvj3YSAw")
                    .ignoreContentType(true)
                    .execute();
            final Map map = Const.MAPPER.readValue(response.bodyAsBytes(), Map.class);
            totalPages = (int)map.get("page_count");
            final String html = (String)map.get("html_content");
            for (Element element : Jsoup.parse(html).select(".author-article-title")) {
                skipCurrentMonth = skipCurrentMonth && shouldSkip(element.parent().attr("data-timestamp"));
                if (skipCurrentMonth) {
                    continue;
                }
                hrefs.add(element.select("a").attr("href"));
            }
        }
        return hrefs;
    }

    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    static boolean shouldSkip(String time) {
        final ZonedDateTime zonedDateTime = ZonedDateTime.parse(time, DATE_FORMATTER);
        return zonedDateTime.getYear() == LocalDate.now().getYear() && zonedDateTime.getMonth() == LocalDate.now().getMonth();
    }


    private static Map<String, Integer> sentiment(final Map data) {
        List<?> authorSentiment = (List)data.get("authorSentiment");
        if (authorSentiment.isEmpty()) {
            return Collections.emptyMap();
        }
        return authorSentiment.stream()
                .map(s -> (Map)s)
                .collect(Collectors.toMap(s1 -> (String)s1.get("primary_ticker"), s1 -> (int)s1.get("type_id"), (i,j ) -> {
                    if (!i.equals(j)) {
                        System.out.println("Different sentiments " + data);
                        return i;
                    }
                    System.out.println("Symbol duplicate " + data);
                    return i;
                }));
    }


    private static String findSentiment(List<DataNode> scripts) {
        for (DataNode node : scripts) {
            final String data = node.getWholeData();
            if (data.startsWith("window.SA = {")) {
                return data.substring(12, data.length() - 1);
            }
        }
        return null;
    }

}
