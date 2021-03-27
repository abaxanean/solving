/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

package seekingalpha.v3;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;

import seekingalpha.PriceFetcher;
import seekingalpha.v3.ArticlesLoader.Article;
import seekingalpha.v3.SentimentLoader.Ticker;

//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import com.gargoylesoftware.htmlunit.util.Cookie;

import seekingalpha.Const;
import seekingalpha.Stock;

public class SeekingAlphaV3 {
//    public static void main(String[] args) {
//        try (final WebClient webClient = new WebClient()) {
//            webClient.getCookieManager().addCookie(new Cookie("https://seekingalpha.com", "session_id", "34d4dabc-9b1f-4cbb-ad02-5a0fb0a8e32d"));
//            webClient.getOptions().setThrowExceptionOnScriptError(false);
//            final HtmlPage page = webClient.getPage("https://seekingalpha.com/article/4395206-cisco-putting-emotions-aside-this-is-cheap-for-now");
//            final String pageAsXml = page.asXml();
//            final String pageAsText = page.asText();
//            System.out.println(pageAsXml.length() + " " + pageAsText.length());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    ArticlesLoader articlesLoader;
    PriceFetcher priceFetcher;
    SentimentLoader sentimentLoader ;

    public SeekingAlphaV3(final ArticlesLoader articlesLoader, final PriceFetcher priceFetcher, final SentimentLoader sentimentLoader) {
        this.articlesLoader = articlesLoader;
        this.priceFetcher = priceFetcher;
        this.sentimentLoader = sentimentLoader;
    }

    public static void main(String[] args) throws IOException {
        new SeekingAlphaV3(new ArticlesLoader(), new PriceFetcher(), new SentimentLoader()).fetchData(Const.CURRENT);
    }

    void fetchData(final String author) throws IOException {
        final List<Article> hrefs = this.articlesLoader.getArticleUrls(author);
        final List<Stock> stocks = new ArrayList<>();
        final Map<String, LocalDate> sell = new HashMap<>();
        for (Article href : hrefs) {
            final List<Ticker> tickers = this.sentimentLoader.loadSentiments(href.getId());
            if (tickers == null) {
                continue;
            }
//            Document document = Jsoup.connect(SITE + href)
//                    .header("cookie", "_sasource=; session_id=09708426-82b0-4630-bf0b-1c7b9f69d243; machine_cookie=2994519254799; __pat=-18000000; _fbp=fb.1.1609369198163.1628992116; _pxvid=e2a93113-4af2-11eb-8e87-0242ac12000f; g_state={\"i_l\":0}; user_id=52980618; user_devices=; u_voc=; marketplace_author_slugs=; user_perm=; user_remember_token=e0c602c582d9c3c7e6dbbeb8193c263616f1c208; __tac=; has_paid_subscription=true; ever_pro=1; sapu=12; portfolio_sort_type=a_z; _pxhd=9b81b7053d831d0e418b92698dce0fc88c8297e1e67eb88e98fefc26b9d3b6ac:80650f60-6b3b-11e9-814e-41aaaa844f02; user_nick=bacs; ubvt=50.35.228.1111611976589996096; __tae=1611790131881; user_cookie_key=dp455z; _sapi_session_id=eyJzZXNzaW9uX2lkIjoiNGI0YTZhM2UyZTBhZjdmNWFmMDk5NzgyZGQ2NTE0NzEiLCJmbGFzaCI6eyJkaXNjYXJkIjpbXSwiZmxhc2hlcyI6eyJub3RpY2UiOm51bGx9fX0%3D--2931810775cf0a2c81e05c67f84cb36476232d28; __tbc=%7Bjzx%7DlCp-P5kTOqotpgFeypItnFOQW0ajdYImM_pO-7tRpq1N3iH5hjj1TGpG9l0229xNuOmp_YMx-xgu7-hy1t125uB4vFgjujlSvHobLdPOD1mfFMMsSw6ZT-2kOKGBspLhoA2mdTePIi02Kuzq68YqAQ; session_id=09708426-82b0-4630-bf0b-1c7b9f69d243; gk_user_access=1*archived*1611871039; gk_user_access_sign=eaf45e6d144cccef4ef6d9b0c07cfb8927b155e3; __pvi=%7B%22id%22%3A%22v-2021-01-28-13-57-00-923-F7zY0C7g5caWECNB-b2b0bb31d66b8385f3439796c3d0b24e%22%2C%22domain%22%3A%22.seekingalpha.com%22%2C%22time%22%3A1611871703600%7D; xbc=%7Bjzx%7DfH6OftjRj7gPec54uNF38qIeIfnBcBLVH5xTnNSbiS9LXeA04eUkTLmmAqo_rJ19w5J7L5t_707o8I4C3yp8fXzOFmww2lxigMSZpcWiBn2bfNT0Xp0UoWl2vruKTZ3RmzxFNGKoJLTFNsWaNuKKzvbCxsfNkmWBhW0XjVbTpkbiMuY-oBOROcsdmkGGVOgi_nSkVSSgxvLFXQ-SNY3-_ssWFslk6SK6BFEfPlYy8BcIX-sv-7ARshc1HzvjbOnhAnvXD9jwq941kFo7uhu1oURiRjG1uynaV7qxQdH_SuQW0GpMt9ypl3DSs8v3gKgH4LSJecPXqQDxC5IrRc3o6UWMu0cr7pZI4PEvZDO4Ex37w-lrjjydn9-CwWnOQqKOEXI8PZmOFrZ_n8cSeo99R6Ntvg4v3IKG-RpiBvC4yc9exWiGMGSVqy2fq4TkNX7p8HvHcJgI1TH60suoEuaPcsonGJP_EzlIJPIahZvwB7wyVRh9rJIz12FDTHAQ_TQ6u787a43Hf8r0gaCzgnwebiugKuyttNYmrc4IOlOrFtZRuRVftAsWnW0DeNm8BIlGHmWAEFOFDNPoEoq4KMHmxIccWqQMFbKEV8_xKUGPWT2Cgl3YvY2EMq75p2Z9_b57wY6-eULuvwUuxahZAtqZy42snUgV-r6Ovkm9a7FVw70ntWS_WOclBFzsm0LrzxkNXwvhFKCBqTcp0t5W9F3wz_ocesbNui1jE1hinvLJdqn0tDtIW99_JE2lPwsraO04_OQmxQILn12BGPpWcv9ukL-saR6-EVmUAYo0sd5PoD6PD3WkVNuE9PuvCEYYYO5UxnbJZrNmMeGJO7-dGGHu1VUichQ80QEQEvdT3Q-FqhO6BjHtqnjjbIs5AWIuBkIGtriDSrJrn3pai0Ph0mBZ3Q")
//                    .ignoreContentType(true)
//                    .execute().parse();
//            List<DataNode> scripts = document.getElementsByTag("script").dataNodes();
//            final String script = findSentiment(scripts);
//            if (script == null) {
//                System.out.println("No script " + document.title());
//                continue;
//            }
//            Map pageConfig = (Map)Const.MAPPER.readValue(script, Map.class).get("pageConfig");
//            Map data = (Map)pageConfig.get("Data");
//            final Map<String, Integer> sentimentMap = sentiment(data);
//            if (sentimentMap.isEmpty()) {
//                System.out.println("No symbols sentiment " + document.title());
//                continue;
//            }
//            final Map articleData = (Map)data.get("article");
//            final String closestTradingDays = (String)articleData.get("closest_trading_day");
//            final LocalDate closestTradingDay = LocalDate.parse(closestTradingDays);
//            if (!ticker.getSentiment().isBullish()) {
//                continue;
//            }
            final List<String> bullish = new ArrayList<>();
            for (Ticker ticker : tickers) {
                final String stock = ticker.getStock().toUpperCase();
                if (ticker.getSentiment().isBullish()) {
                    bullish.add(stock);
                } else if (!ticker.getSentiment().isBullish()) {
                    sell.put(stock, ticker.getClosestTradingDay());
                }
            }

            if (bullish.isEmpty()) {
////                System.out.println("No bullish sentiment " + sentimentMap);
//                continue;
            }

            final List<Stock> priceOnDay = this.priceFetcher.getPriceOnDay(bullish, tickers.get(0).getClosestTradingDay());
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


        Const.MAPPER.writerWithDefaultPrettyPrinter().writeValue(new File("/Users/abaxanean/projects2/solving/muruz/src/main/resources/" + author + ".json"), stocks);
    }
}
