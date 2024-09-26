package ru.otus.banknote;

public class Banknote {

    private final BanknotesDenomination banknoteDenomination;

    public Banknote(BanknotesDenomination banknoteDenomination) {
        this.banknoteDenomination = banknoteDenomination;
    }

    public BanknotesDenomination getBanknoteDenomination() {
        return banknoteDenomination;
    }

    public int getDenominationValue() {
        return banknoteDenomination.getDenomination();
    }
}
