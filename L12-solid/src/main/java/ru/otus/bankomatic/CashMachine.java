package ru.otus.bankomatic;

import ru.otus.banknote.Banknote;

import java.util.List;

public class CashMachine implements Bancomatic {

    private final CashStorage cashStorage; // new

    public CashMachine(CashStorage cashStorage) {

        this.cashStorage = cashStorage;
    }

    @Override
    public void depositMoney(List<Banknote> banknotesList) {
        this.cashStorage.depositMoney(banknotesList);
    }

    @Override
    public int getBalance() {
        return cashStorage.getBalance();
    }

    @Override
    public List<Banknote> getMoney(int amount) throws RuntimeException{
        return cashStorage.getMoney(amount);
    }
}
