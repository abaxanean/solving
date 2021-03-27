/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

package seekingalpha.v3;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import seekingalpha.Const;

public class ArticlesLoader {

//    private static final String ARTICLES = "https://seekingalpha.com/author/gen-alpha/ajax_load_regular_articles?lt=0&page=0&author=true&userId=49839830&sort=recent&rta=true";
    private static final String ARTICLES = "https://seekingalpha.com/author/%s/ajax_load_regular_articles?lt=%d&page=%d";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    public List<Article> getArticleUrls(final String author) throws IOException {
        final List<Article> articles = new ArrayList<>();
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
                final LocalDate date = getDate(element);
                skipCurrentMonth = skipCurrentMonth && date.getYear() == LocalDate.now().getYear() && date.getMonth() == LocalDate.now().getMonth();
                if (skipCurrentMonth) {
                    continue;
                }
                final Element anchor = element.select("a").first();
                articles.add(new Article(anchor, date));
            }
        }
        return articles;
    }

    private LocalDate getDate(final Element element) {
        final String timestamp = element.parent().attr("data-timestamp");
        return ZonedDateTime.parse(timestamp, DATE_FORMATTER).toLocalDate();
    }

    static boolean shouldSkip(String time) {
        final ZonedDateTime zonedDateTime = ZonedDateTime.parse(time, DATE_FORMATTER);
        return zonedDateTime.getYear() == LocalDate.now().getYear() && zonedDateTime.getMonth() == LocalDate.now().getMonth();
    }

    static class Article {

        private static final int PREFIX_LENGTH = "/article/".length();

        private final int id;
        private final String title;
        private final String href;
        private final LocalDate date;

        public Article(final Element anchor, final LocalDate date) {
            this.title = anchor.text();
            this.href = anchor.attr("href");
            this.id = parseId(this.href);
            this.date = date;
        }

        static int parseId(final String href) {
            final int endIndex = href.indexOf("-", PREFIX_LENGTH);
            return Integer.parseInt(href.substring(PREFIX_LENGTH, endIndex));
        }

        public int getId() {
            return this.id;
        }

        public String getTitle() {
            return this.title;
        }

        public String getHref() {
            return this.href;
        }

        public LocalDate getDate() {
            return this.date;
        }
    }

    public static void main(String[] args) {
        System.out.println(Article.parseId("/article/4401520-5_4-yield-shaw-communications-is-your-canadian-telecom-play?source=all_articles_title"));
    }

}
