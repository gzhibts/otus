package ru.otus.test;

import ru.otus.Multiplicator;
import ru.otus.myAnnotation.After;
import ru.otus.myAnnotation.Before;
import ru.otus.myAnnotation.Test;

public class MyTest {

    private int expected;

    @Test
    private void testCalculate() {
        var mult = new Multiplicator(5, 2);
        int result = mult.calculate();
        if (result != expected) {
            System.out.println("expected: 10, " + "fact: " + result);
            throw new RuntimeException("ex");
        }
        System.out.println("Calculator test succeed");
    }
    @Test
    private void testConstructor() {
        var mult = new Multiplicator(5, 2);
        if (mult.getA() != 5 || mult.getB() != 2) {
            System.out.println("Constructor test failed");
            throw new RuntimeException("ex");
        }
        System.out.println("Constructor test succeed");
    }

    @Test
    private void printTestException() {
        System.out.println("Exception test");
        throw new RuntimeException("ex");
    }

    @Before
    private void printBefore() {
        System.out.println("-- Before");
        this.expected = 10;
    }

    @After
    private void printAfter() {
        System.out.println("-- After");
    }

}
