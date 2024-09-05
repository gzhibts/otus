package ru.otus;

public class Multiplicator {

    private final int a;
    private final int b;

    public Multiplicator(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int calculate() {
        return a * b;
   }

}