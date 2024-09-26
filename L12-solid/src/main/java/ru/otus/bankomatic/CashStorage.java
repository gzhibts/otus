package ru.otus.bankomatic;

import ru.otus.banknote.Banknote;

import java.util.List;

public interface CashStorage {

    void depositMoney(List<Banknote> banknoteList);
    int getBalance();
    List<Banknote> getMoney(int amount);
}
