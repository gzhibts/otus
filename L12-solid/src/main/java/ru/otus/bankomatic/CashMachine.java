package ru.otus.bankomatic;

import ru.otus.banknote.Banknote;

import java.util.List;

public class CashMachine<T extends Banknote> implements Bancomatic<T> {

    private final CashStorage<T> cashStorage; // new

    public CashMachine(CashStorage<T> cashStorage) {

        this.cashStorage = cashStorage;
    }

    @Override
    public void depositMoney(List<T> banknotesList) {
        this.cashStorage.depositMoney(banknotesList);
    }

    @Override
    public int getBalance() {
        return cashStorage.getBalance();
    }

    @Override
    public List<T> getMoney(int amount) throws RuntimeException{
        return cashStorage.getMoney(amount);
    }
}
