package com.agileengine.htmlnlz.index;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.util.*;
import java.util.stream.Collectors;

public class Indexer {

    private static Map<AttributeValueType, List<String>> stopWords = new HashMap<>();

    static {
        stopWords.put(AttributeValueType.JS,
                Arrays.asList("javascript", "return", "false", "true", "window"));
        stopWords.put(AttributeValueType.JS,
                Arrays.asList("by", "on", "in", "for", "at", "of", "to", "with", "about", "till", "from", "during"));
    }

    public Index index(Element elem) {
        final Index index = new Index();
        index.setTag(elem.tagName());
        for (Attribute attr : elem.attributes()) { //go through all attributes
            AttributeValueType type = detectAttributeValueType(attr);
            for (String word : tokenizeAlphanum(attr.getValue())) { //split attr value to tokens/words by non-alphanum chars
                tokenizeCamelCase(word).stream() //split alphanum words by CamelCase
                        .filter(w -> isNotAStopWord(type, w))
                        .forEach(index::addKeyword); //add all keywords to index
            }
        }

        return index;
    }

    /**
     * Split attributes value into words. Delimiters are non alphanum characters
     *
     * @param value attributes value
     * @return collection of tokens
     */
    private Collection<String> tokenizeAlphanum(String value) {
        return Arrays.stream(value.split("[^a-zA-Z0-9]")) //on non alphanum
                .filter(s -> !s.trim().isEmpty()) //filters out empty tokens
                .collect(Collectors.toList());
    }

    /**
     * Split CamelCased string values into separate tokens
     *
     * @param value alphanum value
     * @return collection of tokens
     */
    private Collection<String> tokenizeCamelCase(String value) {
        return Arrays.stream(value.split("(?<=[a-z])(?=[A-Z])")) //split string when capital letter follows lowercase letter
                .filter(s -> !s.trim().isEmpty()) //filters out empty strings
                .collect(Collectors.toList());
    }

    private boolean isNotAStopWord(AttributeValueType type, String word) {
        return !stopWords.getOrDefault(type, Collections.emptyList()).contains(word);
    }

    private AttributeValueType detectAttributeValueType(Attribute attr) {
        if (attr.getValue().contains("javascript:")) {
            return AttributeValueType.JS;
        }
        return AttributeValueType.TEXT;
    }

    private enum AttributeValueType {
        JS, TEXT
    }

}
