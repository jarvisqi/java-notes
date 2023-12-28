package com.softmax.features;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFeature {
    private static void oldStyle() {
        Stream<String> stringStream = Stream.of("a", "b", "c");
        List<String> stringList = stringStream.collect(Collectors.toList());
        for (String s : stringList) {
            System.out.println(s);
        }
    }

    private static void streamToList() {
        Stream<String> stringStream = Stream.of("a", "b", "c");
        List<String> stringList = stringStream.toList();
        for (String s : stringList) {
            System.out.println(s);
        }
    }

    public static void main(String[] arg) {

        oldStyle();

        streamToList();

    }

}
