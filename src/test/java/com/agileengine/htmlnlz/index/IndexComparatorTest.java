package com.agileengine.htmlnlz.index;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IndexComparatorTest {

    private IndexComparator comparator;
    private Indexer indexer;

    @BeforeEach
    public void before() {
        comparator = new IndexComparator();
        indexer = new Indexer();
    }

    @Test
    public void matchOk() {
        Element orig = Jsoup.parse("<a href=\"#notOk\" class=\"ok text\">text</a>").selectFirst("a");
        Element other = Jsoup.parse("<a href=\"#ok\" class=\"not done btn\">text</a>").selectFirst("a");
        Index indexOrig = indexer.index(orig);
        Index indexOther = indexer.index(other);
        int res = comparator.distance(indexOrig, indexOther);
        System.out.println(res);
//        Assertions.assertTrue(res >= 0);
    }

}