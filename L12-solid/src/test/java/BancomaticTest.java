import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.banknote.*;
import ru.otus.bankomatic.CashMachine;
import ru.otus.bankomatic.CashStorageImpl;

import java.util.ArrayList;
import java.lang.RuntimeException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BancomaticTest {

    @Test
    @DisplayName("Проверка помещения банкнот")
    void depositMoneyTest() {

        int expectedSum = 13200;
        var myBancomatic = new CashMachine(new CashStorageImpl());

        var firstReceipt = new ArrayList<Banknote>();

        for (int i = 0; i < 2; i++) {
            firstReceipt.add(new Banknote(Banknotes.RUBLE_100));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_500));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_1000));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_5000));
        }

        myBancomatic.depositMoney(firstReceipt);

        assertThat(myBancomatic.getBalance()).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Проверка выдачи при наличии суммы")
    void getMoneyTest() {

        var myBancomatic = new CashMachine(new CashStorageImpl());

        var firstReceipt = new ArrayList<Banknote>();

        for (int i = 0; i < 2; i++) {
            firstReceipt.add(new Banknote(Banknotes.RUBLE_100));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_500));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_1000));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_5000));
        }

        myBancomatic.depositMoney(firstReceipt);

        List<Integer> result = new ArrayList<>();
        int expectedSum = 7600;
        int sum = 0;
        var cash = myBancomatic.getMoney(expectedSum);
        for (var c : cash) {
            result.add(c.getDenominationValue());
            sum += c.getDenominationValue();
        }

        assertThat(sum).isEqualTo(expectedSum);
        result.sort(Integer::compare);
        assertThat(result).isEqualTo(List.of(100, 500, 1000, 1000, 5000));

    }

    @Test
    @DisplayName("Проверка выдачи при отсутствии суммы")
    void getMoneyExceptionTest() {

        var myBancomatic = new CashMachine(new CashStorageImpl());

        var firstReceipt = new ArrayList<Banknote>();

        for (int i = 0; i < 2; i++) {
            firstReceipt.add(new Banknote(Banknotes.RUBLE_100));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_500));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_1000));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_5000));
        }

        myBancomatic.depositMoney(firstReceipt);

        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            var cash = myBancomatic.getMoney(13500);
        });
        assertNotNull(thrown.getMessage());

        Throwable thrown2 = assertThrows(RuntimeException.class, () -> {
            var cash = myBancomatic.getMoney(7900);
        });
        assertNotNull(thrown2.getMessage());

    }

    @Test
    @DisplayName("Проверка баланса")
    void customerAsKeyTest() {

        int expectedSum = 26400;
        var myBancomatic = new CashMachine(new CashStorageImpl());

        var firstReceipt = new ArrayList<Banknote>();

        for (int i = 0; i < 3; i++) {
            firstReceipt.add(new Banknote(Banknotes.RUBLE_100));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_500));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_1000));
            firstReceipt.add(new Banknote(Banknotes.RUBLE_5000));
        }

        var secondReceipt = new ArrayList<Banknote>();

        secondReceipt.add(new Banknote(Banknotes.RUBLE_100));
        secondReceipt.add(new Banknote(Banknotes.RUBLE_500));
        secondReceipt.add(new Banknote(Banknotes.RUBLE_1000));
        secondReceipt.add(new Banknote(Banknotes.RUBLE_5000));

        myBancomatic.depositMoney(firstReceipt);
        myBancomatic.depositMoney(secondReceipt);

        int sum = myBancomatic.getBalance();
        assertThat(sum).isEqualTo(expectedSum);

    }



}
