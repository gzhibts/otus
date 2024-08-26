package ru.otus.test;

import ru.otus.myAnnotation.After;
import ru.otus.myAnnotation.Before;
import ru.otus.myAnnotation.Test;

public class MyTest {

    private int x;

    @Test
    private void printTest1() {
        System.out.println("test1");
        throw new RuntimeException("ex");
    }

    @Test
    private void printTest2() {
        System.out.println("test2");
    }

    @Before
    private void printBefore() {
        x = 37;
        System.out.println("before");
    }

    @After
    private void printAfter() {
        System.out.println("After with x = " + x);
    }

}
