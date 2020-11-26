package com.epam.cdp.m2.hw2.aggregator;

import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java8ParallelAggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        return numbers.parallelStream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        Map<String, Long> pairMap = words.parallelStream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return pairMap.entrySet().parallelStream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed().thenComparing(Map.Entry.comparingByKey()))
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .limit(limit).collect(Collectors.toList());
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {

        Set<String> items = new HashSet<>();
        return words.parallelStream().filter(item -> (!items.add(item.toUpperCase())))
                .map(String::toUpperCase)
                .limit(limit)
                .sorted(stringComparator())
                .collect(Collectors.toList());
    }

    private Comparator<String> stringComparator() {
        return (s1, s2) -> {
            if (s1.length() != s2.length()) {
                return s1.length() - s2.length();
            }
            return s1.compareTo(s2);
        };
    }
}
