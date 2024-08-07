package homework;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.AbstractMap;
import java.util.Comparator;

public class CustomerService {

    private final NavigableMap<Customer, String> sortedMap = new TreeMap<>(Comparator.comparing(Customer::getScores));


    public Map.Entry<Customer, String> getSmallest() {

        return entryCopy(sortedMap.firstEntry());

    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        return entryCopy(sortedMap.higherEntry(customer));

    }

    public void add(Customer customer, String data) {

        sortedMap.put(customer, data);

    }

    private Map.Entry<Customer, String> entryCopy(Map.Entry<Customer, String> entry) {

        if (entry == null) {
            return null;
        }

        var customer = entry.getKey();
        var customerCopy = new Customer(customer.getId(), customer.getName(), customer.getScores());

        return new AbstractMap.SimpleEntry<>(customerCopy, entry.getValue());
        
    }

}
