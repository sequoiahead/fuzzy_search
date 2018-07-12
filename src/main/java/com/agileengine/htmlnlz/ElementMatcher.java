package com.agileengine.htmlnlz;

import com.agileengine.htmlnlz.index.Index;
import com.agileengine.htmlnlz.index.IndexComparator;
import com.agileengine.htmlnlz.index.Indexer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Goes through all elements by tag name
 */
public class ElementMatcher {

    private Indexer indexer = new Indexer();
    private IndexComparator comparator = new IndexComparator();

    public Optional<String> findElement(Index index, Document doc) {
        String tag = index.getTag();
        int maxDistance = Integer.MAX_VALUE;
        Element matchedElem = null;
        for (Element elem: doc.getElementsByTag(tag)) {
            Index elemIndex = indexer.index(elem);
            int distance = comparator.distance(index, elemIndex);
            if (distance < maxDistance) {
                matchedElem = elem;
                maxDistance = distance;
            }
        }
        return renderPath(matchedElem);
    }

    private Optional<String> renderPath(Element elem) {
        if (elem == null) {
            return Optional.empty();
        }
        Deque<String> stack = new ArrayDeque<>();
        stack.push(formatAttributes(elem)); //add found elem to the path
        elem.parents().stream()
                .map(this::formatAttributes)
                .forEach(stack::push);
        return Optional.of(stack.stream().collect(Collectors.joining(" > ")));
    }

    private String formatAttributes(Element elem) {
        return String.format("%s[%s]",
                elem.tagName(),
                elem.attributes().asList().stream().map(s -> s.toString()).collect(Collectors.joining(" ")));
    }
}
