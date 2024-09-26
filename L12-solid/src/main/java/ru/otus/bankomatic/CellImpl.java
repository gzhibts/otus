package ru.otus.bankomatic;

import ru.otus.banknote.Banknote;

import java.util.ArrayList;
import java.util.List;

public class CellImpl implements Cell{

    private final List<Banknote> banknotes;
    private final int maxBanknotesCount;

    public CellImpl(int maxBanknotesCount) {
        this.banknotes = new ArrayList<>();
        this.maxBanknotesCount = maxBanknotesCount;
    }

    @Override
    public int getMaxBanknotesCount() {
        return this.maxBanknotesCount;
    }

    @Override
    public int getBanknotesCount() {
        return this.banknotes.size();
    }

    @Override
    public void addBanknotes(List<Banknote> list) {
        this.banknotes.addAll(list);
    }

    @Override
    public List<Banknote> getBanknotes(int count) {
        var result = new ArrayList<Banknote>();
        for (int n = 0; n < count; n++) {
            result.add(banknotes.getLast());
            banknotes.removeLast();
        }
        return result;
    }

}
