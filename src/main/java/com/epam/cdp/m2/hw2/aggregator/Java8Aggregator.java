package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java8Aggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        return numbers.stream().reduce(0, (c, e) -> (c + e));
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        List<Pair<String, Long>> pairList = new ArrayList<>();
        Map<String, Long> pairMap = words.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        pairMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).limit(limit).forEachOrdered(element -> pairList.add(new Pair<>(element.getKey(), element.getValue())));

        return pairList;
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {

        List<String> duplicates;
        Set<String> items = new HashSet<>();

        duplicates = words.stream().filter(item -> (!items.add(item.toUpperCase()))).map(String::toUpperCase).limit(limit).collect(Collectors.toList());
        duplicates.sort((s1, s2) -> {
            if (s1.length() != s2.length()) {
                return s1.length() - s2.length();
            }
            return s1.compareTo(s2);
        });

        return duplicates;
    }
}