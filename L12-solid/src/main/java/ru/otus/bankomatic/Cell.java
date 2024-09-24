package ru.otus.bankomatic;

import ru.otus.banknote.Banknote;
import ru.otus.banknote.Banknotes;

import java.util.List;

public interface Cell {

    int getMaxBanknotesCount();
    int getBanknotesCount();
    void addBanknotes(List<Banknote> list);
    List<Banknote> getBanknotes(int count);

}
