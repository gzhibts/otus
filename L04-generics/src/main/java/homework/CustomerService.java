package homework;

import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    private final TreeMap<Customer, String> sortedMap = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        var newMap = new TreeMap<Customer, String>();

        Customer resultCustomer = sortedMap.firstEntry().getKey();
        Customer newCustomer = new Customer(resultCustomer.getId(), resultCustomer.getName(), resultCustomer.getScores());
        String resultData = sortedMap.firstEntry().getValue();

        newMap.put(newCustomer, resultData);

        return newMap.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        var newMap = new TreeMap<Customer, String>();
        var entry = sortedMap.higherEntry(customer);
        if (entry == null) {
            return null;
        }

        Customer resultCustomer = entry.getKey();
        Customer newCustomer = new Customer(resultCustomer.getId(), resultCustomer.getName(), resultCustomer.getScores());
        String resultData = entry.getValue();

        newMap.put(newCustomer, resultData);

        return newMap.firstEntry();
    }

    public void add(Customer customer, String data) {
        sortedMap.put(customer, data);
    }
}
