package ru.otus.banknote;

public class Banknote {

    private final Banknotes banknoteDenomination;

    public Banknote(Banknotes banknoteDenomination) {
        this.banknoteDenomination = banknoteDenomination;
    }

    public Banknotes getBanknoteDenomination() {
        return banknoteDenomination;
    }

    public int getDenominationValue() {
        return banknoteDenomination.getDenomination();
    }
}
