package ru.otus.bankomatic;

import ru.otus.banknote.Banknote;

import java.util.List;

public interface Cell<T extends Banknote>{

    int getMaxBanknotesCount();
    int getBanknotesCount();
    void addBanknotes(List<T> list);
    List<T> getBanknotes(int count);
}
