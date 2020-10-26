package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.*;

public class Java7Aggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        int total = 0;
        for (int value : numbers) {
            total += value;
        }
        return total;
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {

        List<Pair<String, Long>> pairList = new ArrayList<>();
        List<String> list = new ArrayList<>(words);

        for (int i = 0; i < list.size(); i++) {
            Pair<String, Long> pair = new Pair<>(list.get(0), 0L);
            for (String word : list) {
                if (pair.getKey().equals(word)) {
                    pair = new Pair<>(word, pair.getValue() + 1L);
                }
            }
            list.remove(0);
            pairList.add(new Pair<>(pair.getKey(), pair.getValue()));
        }

        pairList.sort(Comparator.<Pair<String, Long>>comparingLong(Pair::getValue).reversed());
        if (limit < pairList.size()) {
            pairList = pairList.subList(0, (int) limit);
        }

        return pairList;
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {

        List<String> duplicates = new ArrayList<>();
        Set<String> unique = new HashSet<>();

        for (String word : words) {
            if (!unique.add(word.toUpperCase())) {
                duplicates.add(word.toUpperCase());
            }
        }
        if (duplicates.size() > limit) {
            duplicates = duplicates.subList(0, (int) limit);
        }

        Collections.sort(duplicates, new java.util.Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s1.length() != s2.length()) {
                    return s1.length() - s2.length();
                }
                return s1.compareTo(s2);
            }
        });

        return duplicates;
    }
}
