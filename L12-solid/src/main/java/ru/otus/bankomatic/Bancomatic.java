package ru.otus.bankomatic;

import ru.otus.banknote.Banknote;
import java.util.List;

public interface Bancomatic<T extends Banknote> {

    void depositMoney(List<T> banknoteList);
    int getBalance();
    List<T> getMoney(int amount);

}
