package ru.otus.bankomatic;

import ru.otus.banknote.Ruble;

import java.util.*;

public class CashStorageRub<R extends Ruble> implements CashStorage<R> {

    private final Map<Integer, Cell<R>> cellsMap;
    private final List<Integer> allBanknotesDenomination;

    public CashStorageRub() {

        this.allBanknotesDenomination = List.of(5000, 1000, 500, 100, 50);
        this.cellsMap = new HashMap<>();
        for (int denomination : allBanknotesDenomination) {
            this.cellsMap.put(denomination, new CellImpl<R>(500));
        }
    }

    @Override
    public void depositMoney(List<R> banknotesList) {

        for (int cellDenomination : allBanknotesDenomination) {
            var cell = cellsMap.get(cellDenomination);
            var curTypeBanknotesList = banknotesList.stream().filter( b -> b.getDenomination() == cellDenomination).toList();
            cell.addBanknotes(curTypeBanknotesList);
        }
    }

    @Override
    public int getBalance() {

        int sum = 0;
        for (int cellDenomination : allBanknotesDenomination) {
            var cell = cellsMap.get(cellDenomination);
            sum += cell.getBanknotesCount() * cellDenomination;
        }

        return sum;
    }

    @Override
    public List<R> getMoney(int amount) throws RuntimeException{

        int balance = this.getBalance();
        if (balance < amount) {
            throw new RuntimeException("Cash storage has " + balance + " available rubles.");
        }

        List<R> result = new ArrayList<>();
        Map<Cell<R>, Integer> banknotesCountToGet = new HashMap<>();

        int left = amount;

        for (int denomination : allBanknotesDenomination) {

            if (denomination > left) {
                continue;
            }

            int count = left / denomination;

            var curCell = cellsMap.get(denomination);

            if (curCell.getBanknotesCount() < count) {
                count = curCell.getBanknotesCount();
            }

            banknotesCountToGet.put(curCell, count);

            left = left - count * denomination;

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
