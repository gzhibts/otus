package ru.otus.bankomatic;

import ru.otus.banknote.Banknote;

import java.util.List;

public interface CashStorage<N extends Banknote> {

    void depositMoney(List<N> banknoteList);
    int getBalance();
    List<N> getMoney(int amount);

}
