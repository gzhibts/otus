package ru.otus;

import com.google.common.collect.Lists;
import com.google.common.collect.Iterables;
import com.google.common.base.Predicates;
import java.util.List;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String... args) {

        System.out.println("Hello, Otus! Here are some names for a cat:");

        List<String> names = Lists.newArrayList("Charlie", "Kitty", "Lucy", "Simba", "Cleo", "Nala");
        System.out.println(names);
        Iterable<String> result
                = Iterables.filter(names, Predicates.containsPattern("a"));

        System.out.println("This names contains \"c\":");
        System.out.println(result);
    }

}
