package com.agileengine.htmlnlz.index;

public class IndexComparator {

    public int distance(Index o1, Index o2) {
        int sum = 0;
        for (String keyword: o2.getKeywords()) {
            if (o1.getKeywords().contains(keyword)) {
                sum += o2.getWeight(keyword);
            }
        }
        return Math.abs(o1.getTotalWeight() - sum);
    }
}
