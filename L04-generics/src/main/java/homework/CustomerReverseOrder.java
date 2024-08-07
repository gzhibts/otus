package homework;

import java.util.Deque;
import java.util.ArrayDeque;

public class CustomerReverseOrder {

    private final Deque<Customer> customers = new ArrayDeque<>();

    public void add(Customer customer) {
        this.customers.push(customer);
    }

    public Customer take() {
        return this.customers.pop();
    }

}
