package com.agileengine.htmlnlz.index;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IndexerTest {

    private Indexer indexer;

    @BeforeEach
    public void before() {
        indexer = new Indexer();
    }

    @Test
    public void aplhanumTokenizer() {
        Document doc =  Jsoup.parse("<a class=\"btn btn-success\" href=\"#ok\" " +
                "title=\"Make-Button\" rel=\"next\" onclick=\"javascript:window.okDone(); return false;\">\n" +
                "Make everything OK</a>");
        Element element = doc.selectFirst("a");
        Index index = indexer.index(element);
        Assertions.assertEquals("a", index.getTag());
        Assertions.assertEquals(Integer.valueOf(1), index.getWeight("titlemake"), "make");
        Assertions.assertEquals(Integer.valueOf(1), index.getWeight("titlebutton"), "button");
        Assertions.assertEquals(Integer.valueOf(1), index.getWeight("hrefok"), "ok");
        Assertions.assertEquals(Integer.valueOf(1), index.getWeight("classbtn"), "btn");
        Assertions.assertEquals(Integer.valueOf(1), index.getWeight("classsuccess"), "success");
        Assertions.assertEquals(Integer.valueOf(1), index.getWeight("onclickdone"), "done");
        Assertions.assertEquals(Integer.valueOf(1), index.getWeight("onclickok"), "done");
    }

}
