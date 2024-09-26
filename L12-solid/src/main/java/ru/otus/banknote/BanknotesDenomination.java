package ru.otus.banknote;

public enum BanknotesDenomination {
    RUBLE_500 (5000),
    RUBLE_1000 (1000),
    RUBLE_5000 (500),
    RUBLE_100 (100);

    private final int denomination;

    BanknotesDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

}
