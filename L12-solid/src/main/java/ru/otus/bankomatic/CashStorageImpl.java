package ru.otus.bankomatic;

import ru.otus.banknote.Banknote;
import ru.otus.banknote.Banknotes;

import java.util.*;

public class CashStorageImpl implements CashStorage {

    private final Map<Banknotes, Cell> cellsMap;
    private final Banknotes[] allBanknotesDenomination;

    public CashStorageImpl() {

        this.allBanknotesDenomination = Banknotes.values();
        this.cellsMap = new HashMap<>();
        for (Banknotes denomination : allBanknotesDenomination) {
            this.cellsMap.put(denomination, new CellImpl(500));
        }
    }

    @Override
    public void depositMoney(List<Banknote> banknotesList) {

        for (Banknotes cellDenomination : allBanknotesDenomination) {
            var cell = cellsMap.get(cellDenomination);
            var curTypeBanknotesList = banknotesList.stream().filter( b -> b.getBanknoteDenomination() == cellDenomination).toList();
            cell.addBanknotes(curTypeBanknotesList);
        }
    }

    @Override
    public int getBalance() {

        int sum = 0;
        for (Banknotes cellDenomination : allBanknotesDenomination) {
            var cell = cellsMap.get(cellDenomination);
            sum += cell.getBanknotesCount() * cellDenomination.getDenomination();
        }

        return sum;

    }

    @Override
    public List<Banknote> getMoney(int amount) throws RuntimeException{

        int balance = this.getBalance();
        if (balance < amount) {
            throw new RuntimeException("Cash storage has " + balance + " available rubles.");
        }

        List<Banknote> result = new ArrayList<>();
        Map<Cell, Integer> banknotesCountToGet = new HashMap<>();

        int left = amount;

        for (Banknotes denomination : allBanknotesDenomination) {

            if (denomination.getDenomination() <= left) {

                int count = left / denomination.getDenomination();

                var curCell = cellsMap.get(denomination);

                if (curCell.getBanknotesCount() < count) {
                    count = curCell.getBanknotesCount();
                }

                banknotesCountToGet.put(curCell, count);

                left = left - count * denomination.getDenomination();
            }

        }

        if (left != 0) {
            throw new RuntimeException("The ATM cannot dispense this amount.");
        }

        for (var key : banknotesCountToGet.keySet()) {
            result.addAll(key.getBanknotes(banknotesCountToGet.get(key)));
        }

        return result;

    }
}
