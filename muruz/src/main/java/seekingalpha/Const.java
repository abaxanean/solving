/*
 * Copyright 2021 MobileIron, Inc.
 * All rights reserved.
 */

package seekingalpha;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Const {

    // buy $10,000
    public static final String ALTITRADE_PARTNERS = "altitrade-partners";
    public static final String JUSTIN_DOPIERALA = "justin-dopierala";
    public static final String MIGUEL_MARECOS_DUARTE = "miguel-marecos-duarte";
    public static final String SIMPLE_INVESTMENT_IDEAS = "simple-investment-ideas";
    public static final String SOLAR_INVESTING = "solar-investing";
    public static final String TJ_ROBERTS = "tj-roberts";
    public static final String NEUTRAL_INVESTING = "neutral-investing";
    // some heavy losses but winning too
    public static final String CATALYST_CAPITAL = "catalyst-capital";
    // buy $5,000
    public static final String LEFT_BRAIN_INVESTMENT_RESEARCH = "left-brain-investment-research";
    public static final String STEVE_ZACHRITZ = "steve-zachritz";
    // buy $2,000
    public static final String MAURO_SOLIS = "mauro-solis";
    public static final String JONATHAN_WHEELER = "jonathan-wheeler";
    public static final String JOHN_D_EDWARDS_CFA = "john-d-edwards-cfa";
    public static final String TREMBLING_WITH_GREED = "trembling-with-greed";
    // buy $1,000
    public static final String SHAREHOLDERS_UNITE = "shareholders-unite";
    public static final String WY_CAPITAL = "wy-capital";
    public static final String VINCE_MARTIN = "vince-martin";
    public static final String PETER_F_WAY_CFA = "peter-f-way-cfa";
    public static final String SERGIO_HEIBER = "sergio-heiber";
    public static final String HOWARD_JAY_KLEIN = "howard-jay-klein";


    // looks good, take another look
    public static final String VALUEZEN = "valuezen";
    public static final String BERT_HOCHFELD = "bert-hochfeld";
    public static final String THE_INSIDERS_FORUM = "the-insiders-forum";
    // can't pull metadata
    // check https://www.tipranks.com/bloggers/top
    // trembling-with-greed
    public static final String MICHAEL_WIGGINS_DE_OLIVEIRA = "michael-wiggins-de-oliveira";
    public static final String WHITE_DIAMOND_RESEARCH = "white-diamond-research";
    public static final String ISHAN_PURI = "ishan-puri";



    public static final String CURRENT = ISHAN_PURI;



    // no
    public static final String CHRIS_DEMUTH_JR = "chris-demuth-jr";
    public static final String DANE_BOWLER = "dane-bowler";
    // short time looks good but long not really, check again
    public static final String INDIVIDUAL_TRADER = "individual-trader";
    // looks good short term but not long term
    public static final String BOOX_RESEARCH = "boox-research";

    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.findAndRegisterModules();
    }

    private static final ZoneId EASTERN = ZoneId.of("US/Eastern");
    private static final String[] SENTIMENT = new String[]{"Very Bullish", "Bullish", "Neutral", "Bearish", "Very Bearish"};


    public static <T> T getFromMap(final Map map, String... path) {
        Map current = map;
        for (int i = 0; i < path.length - 1; i++) {
            current = (Map) current.get(path[i]);
        }
        return (T) current.get(path[path.length - 1]);
    }

    public static <T> Collection<T> diff(final Collection<T> minuend, final Collection<T> subtrahend) {
        if (minuend.isEmpty()) {
            return Collections.emptySet();
        }
        if (subtrahend.isEmpty()) {
            return minuend;
        }
        final Set<T> difference = new HashSet<>(minuend);
        difference.removeAll(subtrahend);
        return difference;
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
