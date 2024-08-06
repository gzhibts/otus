package homework;

import java.util.LinkedList;

public class CustomerReverseOrder {

    // todo: 2. надо реализовать методы этого класса
    private final LinkedList<Customer> customers = new LinkedList<>();

    public void add(Customer customer) {
        this.customers.add(customer);
    }

    public Customer take() {
        return this.customers.pollLast();
    }
}
