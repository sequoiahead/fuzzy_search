package com.agileengine.htmlnlz.index;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Index {

    private String tag;
    private Map<String, Integer> keywords = new HashMap<>();

    public Index() {
    }

    void setTag(String tag) {
        this.tag = tag;
    }

    void addKeyword(String keyword) {
        keyword = normalizeKeyword(keyword);
        int weight = keywords.getOrDefault(keyword, 1);
        keywords.put(keyword, weight);
    }

    public String getTag() {
        return tag;
    }

    public Set<String> getKeywords() {
        return keywords.keySet();
    }

    public Integer getWeight(String keyword) {
        return keywords.getOrDefault(normalizeKeyword(keyword), 0);
    }

    public int getTotalWeight() {
        return keywords.values().stream().mapToInt(Integer::intValue).sum();
    }

    private String normalizeKeyword(String keyword) {
        return keyword.toLowerCase();
    }
}
