package epam.lunch.dto;

import epam.lunch.model.Customer;

import java.util.List;

public class OrderWithPrice {

    private final Customer customer;
    private final List<String> dishes;
    private final int totalPrice;

    public OrderWithPrice(Customer customer, List<String> dishes, int totalPrice) {
        this.customer = customer;
        this.dishes = dishes;
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<String> getDishes() {
        return dishes;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
